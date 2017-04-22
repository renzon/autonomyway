package com.autonomyway.business.transfer;

import android.content.res.Resources;

import com.autonomyway.R;
import com.autonomyway.model.Expense;
import com.autonomyway.model.Transformation;

public class SingleExpenseRateVisitor extends TransferVisitorAdapter {
    private long totalCash = 0;
    private Expense expense;

    public SingleExpenseRateVisitor(Expense expense, long duration, Resources resources) {
        super(duration, resources);
        this.expense = expense;
    }

    @Override
    public void visitAsOrigin(Expense expense, long transferCash, long transferDuration) {
        if (this.expense.getId().equals(expense.getId())) {
            totalCash -= transferCash;
        }


    }

    @Override
    public void visitAsDestination(Expense expense, long transferCash, long transferDuration) {
        if (this.expense.getId().equals(expense.getId())) {
            totalCash += transferCash;
        }
    }

    @Override
    public String getMetric() {
        try {
            return Transformation.cashRate(getMetricNumber(), getResources());
        } catch (ArithmeticException e) {
        }
        return getString(R.string.metric_not_enough_data_available);
    }


    @Override
    public long getMetricNumber() {
        return totalCash / getTotalDuration();
    }

}
