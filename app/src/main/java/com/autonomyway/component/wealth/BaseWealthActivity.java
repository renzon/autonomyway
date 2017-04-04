package com.autonomyway.component.wealth;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.autonomyway.ActivityWithFacadeAccess;
import com.autonomyway.R;
import com.autonomyway.business.GUIValidationException;
import com.autonomyway.component.CashInput;
import com.autonomyway.model.Wealth;

public abstract class BaseWealthActivity extends ActivityWithFacadeAccess {

    private EditText nameInput;
    private TextInputLayout nameLayout;
    private CashInput initialBalanceInput;
    private boolean clearFlag=false;


    private String getName() {
        return nameInput.getText().toString().trim();
    }

    protected void setName(String name) {
        nameInput.setText(name);
    }

    protected void populateForm(Wealth wealth){
        setName(wealth.getName());
        initialBalanceInput.setCash(wealth.getInitialBalance());
    }

    protected void cleanForm() {
        clearFlag=true;
        setName("");
        initialBalanceInput.clear();
    }

    private boolean validateName() {
        if (!clearFlag && "".equals(getName())) {
            nameLayout.setError(getString(R.string.wealth_name_error));
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wealth_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        nameInput = (EditText) findViewById(R.id.wealth_name_input);
        initialBalanceInput = (CashInput) findViewById(R.id.wealth_initial_balance_input);
        nameLayout = (TextInputLayout) findViewById (R.id.wealth_name_layout);
        Button saveButton = (Button) findViewById(R.id.wealth_save_button);
        final BaseWealthActivity that = this;
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    that.validateForm();
                } catch (GUIValidationException e) {
                    that.showMsg(v, R.string.wealth_error_msg);
                    return;
                }
                that.saveWealth(v, getName(), initialBalanceInput.getCash());

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

    protected abstract void saveWealth(View v, String name, Long initialBalance);


    protected void showMsg(View v, int id) {
        Snackbar.make(v, getString(id), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

}
