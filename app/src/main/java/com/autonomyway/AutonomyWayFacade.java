package com.autonomyway;

import com.autonomyway.model.Income;
import com.autonomyway.model.Wealth;

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
}
