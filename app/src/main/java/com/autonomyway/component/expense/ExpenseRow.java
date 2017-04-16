package com.autonomyway.component.expense;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.TextView;

import com.autonomyway.AutonomyWayFacade;
import com.autonomyway.R;
import com.autonomyway.business.AutonomyWay;
import com.autonomyway.component.base.BaseRow;
import com.autonomyway.model.Expense;


public class ExpenseRow extends BaseRow<Expense> {

    public ExpenseRow(Context context) {
        this(context, null);
    }

    public ExpenseRow(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExpenseRow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void populateRow(Expense expense) {
        TextView titleTextView = (TextView) findViewById(R.id.expense_row_title);
        TextView expenseRateTextView = (TextView) findViewById(R.id.expense_rate);
        titleTextView.setText(expense.getName());
        AutonomyWayFacade facade= AutonomyWay.getInstance(getContext());
        expenseRateTextView.setText(facade.calculateMetrics().getExpenseRate(expense));
    }

    @NonNull
    @Override
    protected Class<EditExpenseActivity> getEditActivity() {
        return EditExpenseActivity.class;
    }

    @NonNull
    @Override
    protected String getIntentId() {
        return EditExpenseActivity.EXPENSE_ID;
    }
}
