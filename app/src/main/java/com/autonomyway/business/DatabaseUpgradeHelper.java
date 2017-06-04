package com.autonomyway.business;

import android.content.Context;
import android.support.annotation.NonNull;

import com.autonomyway.model.DaoMaster;
import com.autonomyway.model.DaoSession;
import com.autonomyway.model.Transfer;
import com.autonomyway.model.Wealth;
import com.autonomyway.model.WealthDao;

import org.greenrobot.greendao.database.Database;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

// main idea from

public class DatabaseUpgradeHelper extends DaoMaster.OpenHelper {

    public DatabaseUpgradeHelper(Context context, String name) {
        super(context, name);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        List<Migration> migrations = getMigrations();

        // Only run migrations past the old version
        for (Migration migration : migrations) {
            if (oldVersion < migration.getVersion()) {
                migration.run(db);
            }
        }
    }

    private List<Migration> getMigrations() {
        List<Migration> migrations = new ArrayList<>();
        migrations.add(new FixWealthBalance());
        Set<Migration> unique_version_migrations=new HashSet<>(migrations);
        if (migrations.size()!=unique_version_migrations.size()){
            throw new RuntimeException("There are Migrations with same version");
        }

        // Sorting just to be safe, in case other people add migrations in the wrong order.
        Collections.sort(migrations);

        return migrations;
    }

     static class FixWealthBalance extends ComparableMigration {

        @Override
        public Integer getVersion() {
            return 3;
        }

        @Override
        public void run(Database db) {
            final DaoSession session = new DaoMaster(db).newSession();
            session.runInTx(new Runnable() {
                @Override
                public void run() {
                    Map<Long,Wealth> wealthMap=new HashMap<>();
                    WealthDao wealthDao = session.getWealthDao();
                    for (Wealth w: wealthDao.queryBuilder().list()){
                        w.setBalance(w.getInitialBalance());
                        wealthMap.put(w.getId(),w);
                    }

                    for (Transfer t:session.getTransferDao().queryBuilder().list()){
                        if (t.getOriginClassHolder().getNodeClass()==Wealth.class){
                            Wealth origin= wealthMap.get(t.getOriginId());
                            origin.handleTransferCreationAsOrigin(t);
                        }

                        if (t.getDestinationClassHolder().getNodeClass()==Wealth.class){
                            Wealth destination= wealthMap.get(t.getDestinationId());
                            destination.handleTransferCreationAsDestination(t);
                        }
                    }
                    wealthDao.updateInTx(wealthMap.values());


                }
            });
            session.clear();
        }
    }

//    private static class MigrationV3 implements Migration {
//
//        @Override
//        public Integer getVersion() {
//            return 3;
//        }
//
//        @Override
//        public void runMigration(Database db) {
//            // Add new column to user table
//            db.execSQL("ALTER TABLE " + UserDao.TABLENAME + " ADD COLUMN " + UserDao.Properties.Age.columnName + " INTEGER");
//        }
//    }

    private interface Migration extends Comparable<Migration>{
        Integer getVersion();

        void run(Database db);
    }

    static private abstract class ComparableMigration implements Migration{
        @Override
        public int compareTo(@NonNull Migration o) {
            return this.getVersion().compareTo(o.getVersion());
        }

        @Override
        public int hashCode() {
            return getVersion().hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Migration)){
                return false;
            }
            Migration migration= (Migration) obj;
            return getVersion().equals(migration.getVersion());
        }
    }


}