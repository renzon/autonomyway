package com.autonomyway;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.autonomyway.business.GUIValidationException;
import com.autonomyway.component.CashInput;

public class IncomeFormActivity extends ActivityWithFacadeAccess {

    private EditText nameInput;
    private TextInputLayout nameLayout;
    private CashInput cashInput;



    private String getName() {
        return nameInput.getText().toString().trim();
    }

    private void setName(String name) {
        nameInput.setText(name);
    }

    public void cleanForm() {
        setName("");
        cashInput.setCash(null);
    }

    private boolean validateName() {
        if ("".equals(getName())) {
            nameLayout.setError(getString(R.string.income_name_error));
            return false;
        }
        nameLayout.setError(null);
        return true;
    }

    public void validateForm() {
        boolean isValid = validateName();
        if (!isValid) {
            throw new GUIValidationException();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nameInput = (EditText) findViewById(R.id.income_name_input);
        cashInput = (CashInput) findViewById(R.id.income_cash_input);
        nameLayout = (TextInputLayout) findViewById(R.id.income_name_layout);
        Button saveButton = (Button) findViewById(R.id.income_save_button);
        final IncomeFormActivity that = this;
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    that.validateForm();
                } catch (GUIValidationException e) {
                    showMsg(v, R.string.income_error_msg);
                    return;
                }
//                autonomy.createIncome(getName())
                that.cleanForm();
                showMsg(v, R.string.income_save_msg);
            }

            private void showMsg(View v, int id) {
                Snackbar.make(v, getString(id), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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

}
