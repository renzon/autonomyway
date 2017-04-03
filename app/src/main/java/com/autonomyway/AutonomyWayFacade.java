package com.autonomyway;

import com.autonomyway.model.Income;

import java.util.List;

public interface AutonomyWayFacade {
    Income createIncome(String name, long recurrentTime, long recurrentCash, Income.Type type);

    List<Income> getIncomeList();

    void createInitialData();
}
