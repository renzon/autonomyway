package com.autonomyway.component.expense;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.autonomyway.R;
import com.autonomyway.component.CashInput;
import com.autonomyway.component.base.BaseFormActivity;
import com.autonomyway.component.base.GUIValidationException;
import com.autonomyway.model.Expense;

public abstract class BaseExpenseActivity extends BaseFormActivity {

    private EditText nameInput;
    private TextInputLayout nameLayout;
    private CashInput recurrentExpenseInput;
    private boolean clearFlag=false;


    private String getName() {
        return nameInput.getText().toString().trim();
    }

    protected void setName(String name) {
        nameInput.setText(name);
    }

    protected void populateForm(Expense expense){
        setName(expense.getName());
        recurrentExpenseInput.setCash(expense.getRecurrentCash());
    }
    @Override
    protected void cleanForm() {
        clearFlag=true;
        setName("");
        recurrentExpenseInput.clear();
    }

    private boolean validateName() {
        if (!clearFlag && "".equals(getName())) {
            nameLayout.setError(getString(R.string.expense_name_error));
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

        nameInput = (EditText) findViewById(R.id.expense_name_input);
        recurrentExpenseInput = (CashInput) findViewById(R.id.expense_recurrent_cash_input);
        nameLayout = (TextInputLayout) findViewById (R.id.expense_name_layout);
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
        saveExpense(v, getName(), recurrentExpenseInput.getCash());
    }

    @Override
    protected int getContentViewId() {
        return R.layout.expense_activity_form;
    }

    protected abstract void saveExpense(View v, String name, Long initialBalance);


}
