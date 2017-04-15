package com.autonomyway.business.transfer;

import android.content.res.Resources;

import com.autonomyway.R;
import com.autonomyway.model.Income;
import com.autonomyway.model.Transformation;

public class IncomeRateVisitor extends TransferVisitorAdapter {
    long totalCash = 0;

    public IncomeRateVisitor(long duration, Resources resources) {
        super(duration, resources);
    }

    @Override
    public void visitAsOrigin(Income income, long transferCash, long transferDuration) {
        totalCash += transferCash;

    }

    @Override
    public void visitAsDestination(Income income, long transferCash, long transferDuration) {
        totalCash -= transferCash;
    }

    @Override
    public String getMetric() {
        if (getTotalDuration() == 0) {
            return getString(R.string.metric_not_enough_data_available);
        }
        return Transformation.cashRate(getMetricNumber(), getResources());
    }
    @Override
    public long getMetricNumber() {
        return totalCash / getTotalDuration();
    }

}
