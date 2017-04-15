package com.autonomyway.business.transfer;

import android.content.res.Resources;

import com.autonomyway.R;
import com.autonomyway.model.Income;
import com.autonomyway.model.Transformation;

public class WorkRateVisitor extends TransferVisitorAdapter {
    long totalCash = 0;
    long totalWorkMinutes = 0;

    public WorkRateVisitor(long duration, Resources resources) {
        super(duration, resources);
    }

    @Override
    public void visitAsOrigin(Income income, long transferCash, long transferDuration) {
        if (income.getType() == Income.Type.WORK) {
            totalCash += transferCash;
            totalWorkMinutes += transferDuration;
        }

    }

    @Override
    public void visitAsDestination(Income income, long transferCash, long transferDuration) {
        if (income.getType() == Income.Type.WORK) {
            totalCash -= transferCash;
            totalWorkMinutes -= transferDuration;
        }
    }

    @Override
    public String getMetric() {
        if (totalWorkMinutes == 0) {
            return getString(R.string.metric_not_enough_data_available);
        }
        int hour_in_minutes = 60;
        long rate = totalCash* hour_in_minutes / totalWorkMinutes;
        return Transformation.cashRate(rate, getResources());
    }

}
