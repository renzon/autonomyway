package com.autonomyway;

import com.autonomyway.model.Income;

import java.util.List;

public interface AutonomyWayFacade {
    Income createIncome(String name, int recurrentTime, int recurrentCash);

    List<Income> getIncomeList();

    void createInitialData();
}
