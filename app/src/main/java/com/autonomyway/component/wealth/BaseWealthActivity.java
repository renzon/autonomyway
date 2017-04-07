package com.autonomyway.component.wealth;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.autonomyway.R;
import com.autonomyway.component.base.GUIValidationException;
import com.autonomyway.component.common.CashInput;
import com.autonomyway.component.base.BaseFormActivity;
import com.autonomyway.model.Wealth;

public abstract class BaseWealthActivity extends BaseFormActivity {

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
    @Override
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

    @Override
    protected void validateForm() {
        boolean isValid = validateName();
        if (!isValid) {
            throw new GUIValidationException();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        nameInput = (EditText) findViewById(R.id.wealth_name_input);
        initialBalanceInput = (CashInput) findViewById(R.id.wealth_initial_balance_input);
        nameLayout = (TextInputLayout) findViewById (R.id.wealth_name_layout);
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

    @Override
    protected void save(View v) {
        saveWealth(v, getName(), initialBalanceInput.getCash());
    }

    @Override
    protected int getContentViewId() {
        return R.layout.wealth_activity_form;
    }

    protected abstract void saveWealth(View v, String name, Long initialBalance);


}
