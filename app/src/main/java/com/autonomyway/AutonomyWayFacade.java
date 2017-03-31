package com.autonomyway;

import com.autonomyway.model.Income;

import java.util.List;

public interface AutonomyWayFacade {
    Income createIncome(String name, int recurrentTime, int recurrentCash, Income.Type type);

    List<Income> getIncomeList();

    void createInitialData();
}
