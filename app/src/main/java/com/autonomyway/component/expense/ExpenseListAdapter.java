package com.autonomyway.component.expense;

import android.content.res.Resources;

import com.autonomyway.R;
import com.autonomyway.component.base.BaseListAdapter;
import com.autonomyway.model.Expense;

import java.util.List;


public class ExpenseListAdapter extends BaseListAdapter<Expense, ExpenseRow, ExpenseListViewHolder> {
    public ExpenseListAdapter(List<Expense> dataSet, Resources resources) {
        super(dataSet, resources);
    }

    @Override
    protected ExpenseListViewHolder createViewHolder(ExpenseRow expenseRow) {
        return new ExpenseListViewHolder(expenseRow);
    }

    @Override
    protected int getRowLayoutId() {
        return R.layout.expense_row;
    }

}
