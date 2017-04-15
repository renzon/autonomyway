package com.autonomyway.business.transfer;

import android.content.res.Resources;

import com.autonomyway.R;
import com.autonomyway.model.Expense;
import com.autonomyway.model.Transformation;

public class ExpenseRateVisitor extends TransferVisitorAdapter {
    long totalCash = 0;

    public ExpenseRateVisitor(long duration, Resources resources) {
        super(duration, resources);
    }

    @Override
    public void visitAsOrigin(Expense expense, long transferCash, long transferDuration) {
        totalCash -= transferCash;

    }

    @Override
    public void visitAsDestination(Expense expense, long transferCash, long transferDuration) {
        totalCash += transferCash;
    }

    @Override
    public String getMetric() {
        if (getTotalDuration() == 0) {
            return getString(R.string.metric_not_enough_data_available);
        }
        long rate = totalCash / getTotalDuration();
        return Transformation.cashRate(rate,getResources());
    }

}
