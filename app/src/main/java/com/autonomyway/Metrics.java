package com.autonomyway;

import com.autonomyway.model.Income;

public interface Metrics {
    String getWorkFreeTime();

    String getExpenseRate();

    String getBusinessRate();

    String getWorkByWorkedTimeRate();

    String getIncomeRate();

    String getTotalWealth();

    String getMandatoryWorkTimePerMonth();

    String getIncomeRate(Income salary);
}
