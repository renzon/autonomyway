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
import com.autonomyway.model.Node;
import com.autonomyway.model.Transfer;
import com.autonomyway.model.TransferDao;
import com.autonomyway.model.Wealth;
import com.autonomyway.model.WealthDao;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.database.Database;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AutonomyWay implements AutonomyWayFacade {
    private static AutonomyWayFacade facade = null;
    private Map<Class<? extends Node>, List<? extends Node>> nodeListCache;
    private Map<Class<? extends Node>, Map<Long, ? extends Node>> nodeByIdCache;

    DaoSession session;
    private Context ctx;


    @Override
    public Transfer createTransfer(final Node origin, final Node destination, Date date, long cash, long duration, String detail) {
        final Transfer transfer = new Transfer(origin, destination, date, cash, duration, detail);
        origin.handleTransferCreationAsOrigin(transfer);
        destination.handleTransferCreationAsDestination(transfer);

        session.runInTx(new Runnable() {
            @Override
            public void run() {
                session.getTransferDao().insert(transfer);
                getDao(origin).save(origin);
                getDao(destination).save(destination);
            }
        });

        return transfer;
    }

    @Override
    public void editTransfer(Transfer transfer) {

    }

    @Override
    public Transfer getTransfer(long id) {
        return null;
    }

    @Override
    public List<Transfer> getTransferList() {
        List<Transfer> list = session.getTransferDao().queryBuilder().orderDesc(TransferDao.Properties.Date)
                .limit(100).list();

        // Warming cache
        getIncomeList();
        getExpenseList();
        getWealthList();
        for (Transfer t:list){
            injectNodes(t);
        }
        return list;
    }

    private void injectNodes(Transfer transfer) {
        Class<? extends Node> originClass = transfer.getOriginClassHolder().getNodeClass();
        Node origin=getFromCache(originClass, transfer.getOriginId());
        if (origin==null){
            origin= (Node) getDao(originClass).load(transfer.getOriginId());
        }

        Class<? extends Node> destinationClass = transfer.getDestinationClassHolder().getNodeClass();
        Node destination=getFromCache(destinationClass, transfer.getDestinationId());
        if (destination==null){
            destination= (Node) getDao(destinationClass).load(transfer.getDestinationId());
        }
        transfer.setOrigin(origin);
        transfer.setDestination(destination);

    }


    private AbstractDao getDao(Node node) {
        return getDao(node.getClass());
    }

    private AbstractDao getDao(Class<? extends Node> nodeClass) {
        if (nodeClass == Expense.class) {
            return session.getExpenseDao();
        } else if (nodeClass == Income.class) {
            return session.getIncomeDao();
        } else if (nodeClass == Wealth.class) {
            return session.getWealthDao();
        }
        throw new RuntimeException("No Dao found for " + nodeClass);
    }

    private AutonomyWay(Context ctx) {
        this.ctx = ctx;
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(ctx, "fake8.db");
        Database db = helper.getWritableDb();
        this.session = new DaoMaster(db).newSession();
        nodeByIdCache = new HashMap<>();
        nodeListCache = new HashMap<>();

        cleanAllCache();
    }

    void cleanAllCache() {
        Class[] nodeClasses = new Class[]{Income.class, Expense.class, Wealth.class};
        for (Class cls : nodeClasses) {
            cleanCache(cls);
        }
    }


    private void cleanCache(Class cls) {
        nodeListCache.put(cls, null);
        nodeByIdCache.put(cls, null);
    }

    private <N extends Node> List<N> getFromCache(Class<N> nodeClass) {
        return (List<N>) nodeListCache.get(nodeClass);
    }

    private <N extends Node> N getFromCache(Class<N> nodeClass, Long nodeId) {
        Map<Long, N> nodeMap = (Map<Long, N>) nodeByIdCache.get(nodeClass);
        if (nodeMap != null && nodeMap.containsKey(nodeId)) {
            return nodeMap.get(nodeId);
        }
        return null;
    }


    @Override
    public Wealth getWealth(Long id) {
        Wealth wealth = getFromCache(Wealth.class, id);
        if (wealth != null) {
            return wealth;
        }
        return session.getWealthDao().load(id);
    }

    @Override
    public List<Wealth> getWealthList() {
        List<Wealth> list = getFromCache(Wealth.class);
        if (list != null) {
            return list;
        }
        list = session.getWealthDao().queryBuilder().orderAsc(WealthDao.Properties.Name).list();
        setCache(Wealth.class, list);
        return list;
    }

    private <N extends Node> void setCache(Class<N> nodeClass, List<N> list) {
        nodeListCache.put(nodeClass, list);
        Map<Long, N> nodeIdMap = new HashMap<>();
        for (N node : list) {
            nodeIdMap.put(node.getId(), node);
        }
    }

    @Override
    public void editWealth(Wealth wealth) {
        WealthDao dao = session.getWealthDao();
        Wealth dbWealth = dao.load(wealth.getId());
        long delta = wealth.getInitialBalance() - dbWealth.getBalance();
        wealth.increaseBalance(delta);
        dao.saveInTx(wealth);

    }

    @Override
    public Wealth createWealth(String name, long initialCash) {
        cleanCache(Wealth.class);
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
        Income income = getFromCache(Income.class, id);
        if (income != null) {
            return income;
        }
        return session.getIncomeDao().load(id);
    }

    @Override
    public Income createIncome(String name, long recurrentTime, long recurrentCash, Income.Type type) {
        cleanCache(Income.class);
        Income income = new Income(name, recurrentTime, recurrentCash, type);
        session.getIncomeDao().insertInTx(income);
        return income;
    }

    @Override
    public List<Income> getIncomeList() {
        List<Income> list = getFromCache(Income.class);
        if (list != null) {
            return list;
        }
        list = session.getIncomeDao().queryBuilder().orderAsc(IncomeDao.Properties.Name).list();
        setCache(Income.class, list);
        return list;
    }

    @Override
    public Expense createExpense(String name, long recurrentCash) {
        cleanCache(Expense.class);
        Expense expense = new Expense(name, recurrentCash);
        session.getExpenseDao().insertInTx(expense);
        return expense;
    }

    @Override
    public Expense getExpense(Long id) {
        Expense expense = getFromCache(Expense.class, id);
        if (expense != null) {
            return expense;
        }
        return session.getExpenseDao().load(id);
    }

    @Override
    public void editExpense(Expense expense) {
        session.getExpenseDao().saveInTx(expense);
    }

    @Override
    public List<Expense> getExpenseList() {
        List<Expense> list = getFromCache(Expense.class);
        if (list != null) {
            return list;
        }
        list = session.getExpenseDao().queryBuilder().orderAsc(ExpenseDao.Properties.Name).list();
        setCache(Expense.class, list);
        return list;
    }

    @Override
    public void createInitialData() {
        cleanAllCache();
        if (getIncomeList().size() == 0) {
            cleanCache(Income.class);
            final Income[] incomes = {
                    new Income(ctx.getString(R.string.income_init_salary), 0, 0, Income.Type.WORK),
                    new Income(ctx.getString(R.string.income_init_stocks), 0, 0, Income.Type.BUSINESS)
            };

            final Wealth[] wealth = {
                    new Wealth(ctx.getString(R.string.wealth_init_bank_account), 0),
                    new Wealth(ctx.getString(R.string.wealth_init_car), 0),
                    new Wealth(ctx.getString(R.string.wealth_init_credit_card), 0),
                    new Wealth(ctx.getString(R.string.wealth_init_house), 0),
                    new Wealth(ctx.getString(R.string.wealth_init_stocks), 0),
                    new Wealth(ctx.getString(R.string.wealth_init_wallet), 0)
            };
            final Expense[] expenses = {
                    new Expense(ctx.getString(R.string.expense_init_car_expenses), 0),
                    new Expense(ctx.getString(R.string.expense_init_children), 0),
                    new Expense(ctx.getString(R.string.expense_init_depreciation), 0),
                    new Expense(ctx.getString(R.string.expense_init_health), 0),
                    new Expense(ctx.getString(R.string.expense_init_house_expenses), 0),
                    new Expense(ctx.getString(R.string.expense_init_leisure), 0),
                    new Expense(ctx.getString(R.string.expense_init_taxes), 0),
            };
            session.runInTx(new Runnable() {
                @Override
                public void run() {
                    session.getIncomeDao().insertInTx(incomes);
                    session.getWealthDao().insertInTx(wealth);
                    session.getExpenseDao().insertInTx(expenses);

                }
            });

        }
    }


}
