package com.autonomyway.business.transfer;

import android.content.res.Resources;

import com.autonomyway.model.Expense;
import com.autonomyway.model.Income;
import com.autonomyway.model.Wealth;

public abstract class TransferVisitorAdapter implements TransferVisitor {
    final private long totalDuration;
    private Resources resources;

    protected long getTotalDuration() {
        return totalDuration;
    }


    protected Resources getResources() {
        return resources;
    }

    public TransferVisitorAdapter(long totalDuration, Resources resources) {
        this.totalDuration = totalDuration;
        this.resources = resources;
    }


    @Override
    public void visitAsOrigin(Expense expense, long transferCash, long transferDuration) {

    }

    @Override
    public void visitAsOrigin(Income income, long transferCash, long transferDuration) {

    }

    @Override
    public void visitAsOrigin(Wealth wealth, long transferCash, long transferDuration) {

    }

    @Override
    public void visitAsDestination(Expense expense, long transferCash, long transferDuration) {

    }

    @Override
    public void visitAsDestination(Income income, long transferCash, long transferDuration) {

    }

    @Override
    public void visitAsDestination(Wealth wealth, long transferCash, long transferDuration) {

    }

    protected String getString(int string_id) {
        return resources.getString(string_id);
    }
}
