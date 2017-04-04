package com.autonomyway.business;

import android.content.Context;

import com.autonomyway.AutonomyWayFacade;
import com.autonomyway.R;
import com.autonomyway.model.DaoMaster;
import com.autonomyway.model.DaoSession;
import com.autonomyway.model.Income;
import com.autonomyway.model.IncomeDao;
import com.autonomyway.model.Wealth;
import com.autonomyway.model.WealthDao;

import org.greenrobot.greendao.database.Database;

import java.util.ArrayList;
import java.util.List;


public class AutonomyWay implements AutonomyWayFacade {
    private static AutonomyWayFacade facade = null;

    DaoSession session;
    private Context ctx;

    private AutonomyWay(Context ctx) {
        this.ctx = ctx;
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(ctx, "fake2.db");
        Database db = helper.getWritableDb();
        this.session = new DaoMaster(db).newSession();
    }

    @Override
    public Wealth getWealth(Long id) {
        return session.getWealthDao().load(id);
    }

    @Override
    public List<Wealth> getWealthList() {
        return session.getWealthDao().queryBuilder().orderAsc(WealthDao.Properties.Name).list();
    }

    @Override
    public void editWealth(Wealth wealth) {
        session.getWealthDao().save(wealth);

    }

    @Override
    public Wealth createWealth(String name, long initialCash) {
        Wealth wealth = new Wealth(name, initialCash);
        session.getWealthDao().save(wealth);
        return wealth;
    }

    public static AutonomyWayFacade getInstance(Context ctx) {
        if (facade == null) {
            facade = new AutonomyWay(ctx);
        }
        return facade;
    }

    @Override
    public void editIncome(Income income) {
        session.getIncomeDao().save(income);
    }

    @Override
    public Income getIncome(long id) {
        return session.getIncomeDao().load(id);
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
            Income[] incomes = {
                    new Income(ctx.getString(R.string.income_init_salary), 0, 0, Income.Type.WORK),
                    new Income(ctx.getString(R.string.income_init_stocks), 0, 0, Income.Type.BUSINESS)
            };

            Wealth[] wealth={
                    new Wealth(ctx.getString(R.string.wealth_init_bank_account),0),
                    new Wealth(ctx.getString(R.string.wealth_init_car),0),
                    new Wealth(ctx.getString(R.string.wealth_init_credit_card),0),
                    new Wealth(ctx.getString(R.string.wealth_init_house),0),
                    new Wealth(ctx.getString(R.string.wealth_init_stocks),0),
                    new Wealth(ctx.getString(R.string.wealth_init_wallet),0)
            };
            session.getIncomeDao().insertInTx(incomes);
            session.getWealthDao().insertInTx(wealth);
        }
    }


}
