package com.autonomyway.business.transfer;

import com.autonomyway.model.Expense;
import com.autonomyway.model.Income;
import com.autonomyway.model.Wealth;

public interface TransferVisitor {
    void visitAsOrigin(Expense expense, long transferCash,long transferDuration);
    void visitAsOrigin(Income income, long transferCash, long transferDuration);
    void visitAsOrigin(Wealth wealth, long transferCash, long transferDuration);
    void visitAsDestination(Expense expense, long transferCash, long transferDuration);
    void visitAsDestination(Income income, long transferCash, long transferDuration);
    void visitAsDestination(Wealth wealth, long transferCash, long transferDuration);

    String getMetric();
}
