package com.autonomyway;

import android.content.res.Resources;

import com.autonomyway.model.Expense;
import com.autonomyway.model.Income;

public interface Metrics {
    String getWorkFreeTime(Resources resources);

    String getExpenseRate();

    String getBusinessRate();

    String getWorkByWorkedTimeRate();

    String getIncomeRate();

    String getTotalWealth();

    String getMandatoryWorkTimePerMonth(Resources resources);

    String getIncomeRate(Income salary);

    String getExpenseRate(Expense expense);
}
