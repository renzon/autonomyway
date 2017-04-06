package com.autonomyway.business;

import android.content.Context;

import com.autonomyway.AutonomyWayFacade;
import com.autonomyway.R;
import com.autonomyway.model.DaoMaster;
import com.autonomyway.model.DaoSession;
import com.autonomyway.model.Expense;
import com.autonomyway.model.ExpenseDao;
import com.autonomyway.model.Income;
import com.autonomyway.model.IncomeDao;
import com.autonomyway.model.Wealth;
import com.autonomyway.model.WealthDao;

import org.greenrobot.greendao.database.Database;

import java.util.List;


public class AutonomyWay implements AutonomyWayFacade {
    private static AutonomyWayFacade facade = null;

    DaoSession session;
    private Context ctx;

    private AutonomyWay(Context ctx) {
        this.ctx = ctx;
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(ctx, "fake4.db");
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
        WealthDao dao = session.getWealthDao();
        Wealth dbWealth = dao.load(wealth.getId());
        long delta=wealth.getInitialBalance()-dbWealth.getBalance();
        wealth.increaseBalance(delta);
        dao.saveInTx(wealth);

    }

    @Override
    public Wealth createWealth(String name, long initialCash) {
        Wealth wealth = new Wealth(name, initialCash);
        session.getWealthDao().saveInTx(wealth);
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
        session.getIncomeDao().saveInTx(income);
    }

    @Override
    public Income getIncome(long id) {
        return session.getIncomeDao().load(id);
    }

    @Override
    public Income createIncome(String name, long recurrentTime, long recurrentCash, Income.Type type) {
        Income income = new Income(name, recurrentTime, recurrentCash, type);
        session.getIncomeDao().insertInTx(income);
        return income;
    }

    @Override
    public List<Income> getIncomeList() {
        return session.getIncomeDao().queryBuilder().orderAsc(IncomeDao.Properties.Name).list();
    }

    @Override
    public Expense createExpense(String name, long recurrentCash) {
        Expense expense=new Expense(name, recurrentCash);
        session.getExpenseDao().insertInTx(expense);
        return expense;
    }

    @Override
    public Expense getExpense(Long id) {
        return session.getExpenseDao().load(id);
    }

    @Override
    public void editExpense(Expense expense) {
        session.getExpenseDao().saveInTx(expense);
    }

    @Override
    public List<Expense> getExpenseList() {
        return session.getExpenseDao().queryBuilder().orderAsc(ExpenseDao.Properties.Name).list();
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
            Expense[] expenses={
                    new Expense(ctx.getString(R.string.expense_init_car_expenses),0),
                    new Expense(ctx.getString(R.string.expense_init_children),0),
                    new Expense(ctx.getString(R.string.expense_init_depreciation),0),
                    new Expense(ctx.getString(R.string.expense_init_health),0),
                    new Expense(ctx.getString(R.string.expense_init_house_expenses),0),
                    new Expense(ctx.getString(R.string.expense_init_leisure),0),
                    new Expense(ctx.getString(R.string.expense_init_taxes),0),
            };
            session.getIncomeDao().insertInTx(incomes);
            session.getWealthDao().insertInTx(wealth);
            session.getExpenseDao().insertInTx(expenses);
        }
    }


}
