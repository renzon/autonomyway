package com.autonomyway.component.income;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.autonomyway.R;
import com.autonomyway.component.base.GUIValidationException;
import com.autonomyway.component.common.CashInput;
import com.autonomyway.component.common.DurationInput;
import com.autonomyway.component.base.BaseFormActivity;
import com.autonomyway.model.Income;

public abstract class BaseIncomeActivity extends BaseFormActivity {

    private EditText nameInput;
    private TextInputLayout nameLayout;
    private CashInput cashInput;
    private DurationInput durationInput;
    private RadioGroup typeGroup;
    private boolean clearFlag = false;
    private RadioButton radioWork;
    private RadioButton radioBusiness;
    private TextInputLayout cashLayout;


    private String getName() {
        return nameInput.getText().toString().trim();
    }

    protected void setName(String name) {
        nameInput.setText(name);
    }

    protected void populateForm(Income income) {
        setName(income.getName());
        durationInput.setDuration(income.getRecurrentTime());
        cashInput.setCash(income.getRecurrentCash());
        setIncomeType(income.getType());
    }

    @Override
    protected void cleanForm() {
        clearFlag = true;
        setName("");
        cashInput.clear();
        durationInput.clear();

    }

    private boolean validateName() {
        if (!clearFlag && "".equals(getName())) {
            nameLayout.setError(getString(R.string.income_name_error));
            return false;
        }
        nameLayout.setError(null);
        clearFlag = false;
        return true;
    }

    @Override
    protected void validateForm() {
        boolean isValid = validateName();
        isValid = validateCash(isValid) && isValid;

        if (!isValid) {
            throw new GUIValidationException();
        }
    }

    private boolean validateCash(boolean isValid) {
        if (cashInput.validate()) {
            cashLayout.setError(null);
            return true;

        }
        cashLayout.setError(getString(R.string.cash_error));
        return false;
    }

    protected Income.Type getIncomeType() {
        if (typeGroup.getCheckedRadioButtonId() == R.id.radio_work) {
            return Income.Type.WORK;
        }
        return Income.Type.BUSINESS;
    }

    protected void setIncomeType(Income.Type type) {

        if (Income.Type.WORK.equals(type)) {
            radioWork.setChecked(true);
            return;
        }
        radioBusiness.setChecked(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        radioWork = (RadioButton) findViewById(R.id.radio_work);
        radioBusiness = (RadioButton) findViewById(R.id.radio_business);
        nameInput = (EditText) findViewById(R.id.income_name_input);
        durationInput = (DurationInput) findViewById(R.id.income_duration_input);
        cashInput = (CashInput) findViewById(R.id.income_cash_input);
        typeGroup = (RadioGroup) findViewById(R.id.income_type_group);
        nameLayout = (TextInputLayout) findViewById(R.id.income_name_layout);
        cashLayout = (TextInputLayout) findViewById(R.id.income_cash_layout);

        nameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                validateName();
            }
        });

        nameInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validateName();
                }
            }
        });


    }

    protected abstract void saveIncome(View v, String name, long duration, Long cash, Income.Type incomeType);

    @Override
    protected int getContentViewId() {
        return R.layout.income_activity_form;
    }

    @Override
    protected void save(View v) {
        saveIncome(v, getName(), durationInput.getDuration(), cashInput.getCash(), getIncomeType());
    }
}
