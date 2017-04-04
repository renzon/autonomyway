package com.autonomyway.component.income;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.autonomyway.ActivityWithFacadeAccess;
import com.autonomyway.R;
import com.autonomyway.business.GUIValidationException;
import com.autonomyway.component.CashInput;
import com.autonomyway.component.DurationInput;
import com.autonomyway.model.Income;

public class NewIncomeActivity extends BaseIncomeActivity {
    @Override
    protected void saveIncome(View v, String name, long duration, Long cash, Income.Type incomeType) {
        autonomy.createIncome(name, duration, cash, incomeType);
        cleanForm();
        showMsg(v, R.string.income_save_msg);
    }
}
