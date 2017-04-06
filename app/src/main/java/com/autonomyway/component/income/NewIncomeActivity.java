package com.autonomyway.component.income;

import android.view.View;

import com.autonomyway.R;
import com.autonomyway.model.Income;

public class NewIncomeActivity extends BaseIncomeActivity {
    @Override
    protected void saveIncome(View v, String name, long duration, Long cash, Income.Type incomeType) {
        autonomy.createIncome(name, duration, cash, incomeType);
        showMsg(v, R.string.income_save_msg);
    }
}
