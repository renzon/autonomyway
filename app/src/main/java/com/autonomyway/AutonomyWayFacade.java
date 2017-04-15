package com.autonomyway;

import android.content.res.Resources;

import com.autonomyway.model.Expense;
import com.autonomyway.model.Income;
import com.autonomyway.model.Node;
import com.autonomyway.model.Transfer;
import com.autonomyway.model.Wealth;

import java.util.Date;
import java.util.List;

public interface AutonomyWayFacade {
    Income createIncome(String name, long recurrentTime, long recurrentCash, Income.Type type);

    List<Income> getIncomeList();

    void createInitialData();

    Income getIncome(long id);

    void editIncome(Income income);

    Wealth createWealth(String name, long initialCash);

    Wealth getWealth(Long id);

    List<Wealth> getWealthList();

    void editWealth(Wealth wealth);

    Expense createExpense(String name, long recurrentCash);

    Expense getExpense(Long id);

    void editExpense(Expense expense);

    List<Expense> getExpenseList();

    Transfer createTransfer(Node origin, Node destination, Date date, long cash, long duration, String detail);

    void editTransfer(Transfer transfer);

    Transfer getTransfer(long id);

    List<Transfer> getTransferList();

    Metrics calculateMetrics(Resources resources);
}
