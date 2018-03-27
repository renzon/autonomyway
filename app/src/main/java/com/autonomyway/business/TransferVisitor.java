package com.autonomyway.business;

import com.autonomyway.model.Expense;
import com.autonomyway.model.Income;
import com.autonomyway.model.Wealth;

public interface TransferVisitor {
    void visitAsOrigin(Expense expense, long transferCash,long transferDuration);
    void visitAsOrigin(Income income, long transferCash, long transferDuration);
    void visitAsOrigin(Wealth wealth, long transferCash, long transferDuration);
    void visitasDestination(Expense expense, long transferCash,long transferDuration);
    void visitasDestination(Income income, long transferCash, long transferDuration);
    void visitasDestination(Wealth wealth, long transferCash, long transferDuration);
}
