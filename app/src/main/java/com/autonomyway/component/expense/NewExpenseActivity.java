package com.autonomyway.component.expense;

import android.view.View;

import com.autonomyway.R;

public class NewExpenseActivity extends BaseExpenseActivity {
    @Override
    protected void saveExpense(View v, String name, Long recurrentCash) {
        autonomy.createExpense(name, recurrentCash);
        showMsg(v, R.string.expense_save_msg);
    }
}
