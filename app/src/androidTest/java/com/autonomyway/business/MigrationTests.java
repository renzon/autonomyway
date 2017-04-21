package com.autonomyway.business;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.autonomyway.AutonomyWayFacade;
import com.autonomyway.model.DaoMaster;
import com.autonomyway.model.DaoSession;
import com.autonomyway.model.Expense;
import com.autonomyway.model.Income;
import com.autonomyway.model.Wealth;

import org.greenrobot.greendao.database.Database;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MigrationTests {
    Context ctx = InstrumentationRegistry.getTargetContext();
    AutonomyWayFacade facade = AutonomyWay.getInstance(ctx);


    @Test
    public void testFixBalance() {
        Wealth account = facade.createWealth("Bank Account", 20);
        Wealth house = facade.createWealth("House", 1);
        Income income = facade.createIncome("Salary", 1, 2, Income.Type.WORK);
        Expense expense = facade.createExpense("Taxes", 2);
        Date dt = new Date();
        facade.createTransfer(account, house, dt, 6, 3, "Buying Home");
        facade.createTransfer(income, account, dt, 60, 3, "Buying Home");
        facade.createTransfer(house, expense, dt, 10, 3, "Buying Home");
        facade.createTransfer(income, expense, dt, 20, 3, "Buying Home");
        account = facade.getWealth(account.getId());
        house = facade.getWealth(house.getId());
        assertEquals(20 - 6 + 60, account.getBalance());
        assertEquals(1 + 6 - 10, house.getBalance());
        DaoMaster.OpenHelper helper = new DatabaseUpgradeHelper(ctx, "autonomy_way.db");
        Database db = helper.getWritableDb();

        DaoSession session = new DaoMaster(db).newSession();
//        Setting wrong balance
        account.setBalance(10);
        house.setBalance(10);
        session.getWealthDao().updateInTx(account, house);
        session.clear();
        ((AutonomyWay) facade).cleanAllCache();

        helper.onUpgrade(db, 1, 2);
        account = facade.getWealth(account.getId());
        house = facade.getWealth(house.getId());
        assertEquals(20 - 6 + 60, account.getBalance());
        assertEquals(1 + 6 - 10, house.getBalance());
    }

    @After
    @Before
    public void cleanDB() {
        AutonomyWay facade = (AutonomyWay) this.facade;
        facade.cleanAllCache();
        DaoSession session = facade.session;
        session.getIncomeDao().deleteAll();
        session.getWealthDao().deleteAll();
        session.getExpenseDao().deleteAll();
        session.getTransferDao().deleteAll();
    }


}
