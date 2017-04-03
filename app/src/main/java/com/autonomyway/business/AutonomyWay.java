package com.autonomyway.business;

import android.content.Context;

import com.autonomyway.AutonomyWayFacade;
import com.autonomyway.R;
import com.autonomyway.model.DaoMaster;
import com.autonomyway.model.DaoSession;
import com.autonomyway.model.Income;
import com.autonomyway.model.IncomeDao;

import org.greenrobot.greendao.database.Database;

import java.util.ArrayList;
import java.util.List;


public class AutonomyWay implements AutonomyWayFacade {
    private static AutonomyWayFacade facade = null;
    DaoSession session;
    private Context ctx;

    private AutonomyWay(Context ctx) {
        this.ctx = ctx;
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(ctx, "fake.db");
        Database db = helper.getWritableDb();
        this.session = new DaoMaster(db).newSession();
    }

    public static AutonomyWayFacade getInstance(Context ctx) {
        if (facade == null) {
            facade = new AutonomyWay(ctx);
        }
        return facade;
    }

    @Override
    public Income createIncome(String name, long recurrentTime, long recurrentCash, Income.Type type) {
        Income income = new Income(name, recurrentTime, recurrentCash, type);
        session.getIncomeDao().insert(income);
        return income;
    }

    @Override
    public List<Income> getIncomeList() {
        return session.getIncomeDao().queryBuilder().orderAsc(IncomeDao.Properties.Name).list();
    }

    @Override
    public void createInitialData() {
        if (getIncomeList().size() == 0) {
            List<Income> incomes = new ArrayList<>();
            incomes.add(new Income(ctx.getString(R.string.income_init_salary), 0, 0, Income.Type.WORK));
            incomes.add(new Income(ctx.getString(R.string.income_init_stocks), 0, 0, Income.Type.BUSINESS));
            session.getIncomeDao().insertInTx(incomes);
        }
    }
}
