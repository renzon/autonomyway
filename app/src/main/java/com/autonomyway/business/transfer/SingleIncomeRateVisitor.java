package com.autonomyway.business.transfer;

import android.content.res.Resources;

import com.autonomyway.R;
import com.autonomyway.model.Income;
import com.autonomyway.model.Transformation;

public class SingleIncomeRateVisitor extends TransferVisitorAdapter {
    private long totalCash = 0;
    private Income income;
    private long totalTransfersDurationInMinutes = 0;

    public SingleIncomeRateVisitor(Income income, long duration, Resources resources) {
        super(duration, resources);
        this.income = income;
    }

    @Override
    public void visitAsOrigin(Income income, long transferCash, long transferDuration) {
        if (this.income.getId().equals(income.getId())) {
            totalCash += transferCash;
            totalTransfersDurationInMinutes += transferDuration;
        }


    }

    @Override
    public void visitAsDestination(Income income, long transferCash, long transferDuration) {
        if (this.income.getId().equals(income.getId())) {
            totalCash -= transferCash;
            totalTransfersDurationInMinutes -= transferDuration;
        }
    }

    @Override
    public String getMetric() {
        try {
            return Transformation.cashRate(getMetricNumber(), getResources());
        }catch (ArithmeticException _){}
        return getString(R.string.metric_not_enough_data_available);
    }



    @Override
    public long getMetricNumber() {
        if (income.getType() == Income.Type.BUSINESS) {

            return totalCash / getTotalDuration();
        }
        long hourInMinutes=60;
        return totalCash*hourInMinutes / totalTransfersDurationInMinutes;
    }

}
