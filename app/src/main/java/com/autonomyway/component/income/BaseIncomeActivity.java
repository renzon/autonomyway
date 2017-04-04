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

public abstract class BaseIncomeActivity extends ActivityWithFacadeAccess {

    private EditText nameInput;
    private TextInputLayout nameLayout;
    private CashInput cashInput;
    private DurationInput durationInput;
    private RadioGroup typeGroup;
    private boolean clearFlag=false;


    private String getName() {
        return nameInput.getText().toString().trim();
    }

    protected void setName(String name) {
        nameInput.setText(name);
    }

    protected void cleanForm() {
        clearFlag=true;
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
        clearFlag=false;
        return true;
    }

    protected void validateForm() {
        boolean isValid = validateName();
        if (!isValid) {
            throw new GUIValidationException();
        }
    }

    protected Income.Type getIncomeType() {

        if (typeGroup.getCheckedRadioButtonId() == R.id.radio_work) {
            return Income.Type.WORK;
        }
        return Income.Type.BUSINESS;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nameInput = (EditText) findViewById(R.id.income_name_input);
        durationInput = (DurationInput) findViewById(R.id.income_duration_input);
        cashInput = (CashInput) findViewById(R.id.income_cash_input);
        typeGroup = (RadioGroup) findViewById(R.id.income_type_group);
        nameLayout = (TextInputLayout) findViewById(R.id.income_name_layout);
        Button saveButton = (Button) findViewById(R.id.income_save_button);
        final BaseIncomeActivity that = this;
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    that.validateForm();
                } catch (GUIValidationException e) {
                    that.showMsg(v, R.string.income_error_msg);
                    return;
                }
                that.saveIncome(v, getName(), durationInput.getDuration(),
                        cashInput.getCash(), getIncomeType());

            }


        });

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

    protected void showMsg(View v, int id) {
        Snackbar.make(v, getString(id), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

}
