package com.autonomyway.component.expense;

import android.support.annotation.NonNull;

import com.autonomyway.R;
import com.autonomyway.component.base.BaseListActivity;
import com.autonomyway.model.Expense;

import java.util.List;

public class ExpenseListActivity extends BaseListActivity<Expense,ExpenseRow,ExpenseListViewHolder,
        ExpenseListAdapter> {

    @Override
    protected ExpenseListAdapter createListAdapter(List modelList) {
        return new ExpenseListAdapter(getModelList(), getResources());
    }

    @Override
    @NonNull
    protected Class<NewExpenseActivity> getNewModelActivityClass() {
        return NewExpenseActivity.class;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.expense_activity_list;
    }


    @Override
    protected List<Expense> getModelList() {
        return autonomy.getExpenseList();
    }
}
