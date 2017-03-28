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
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(ctx, "autonomy-db");
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
    public Income createIncome(String name, int recurrentTime, int recurrentCash) {
        Income income = new Income(name, recurrentTime, recurrentCash);
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
            String[] incomeNames = {
                    ctx.getString(R.string.init_salary),
                    ctx.getString(R.string.init_stocks_income)
            };
            List<Income> incomes = new ArrayList<>();
            for (String name : incomeNames) {
                incomes.add(new Income(name, 0, 0));
            }
            session.getIncomeDao().insertInTx(incomes);
        }
    }
}
