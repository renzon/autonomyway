package com.autonomyway.component.income;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.autonomyway.model.Income;

public class EditIncomeActivity extends BaseIncomeActivity {
    public static final String INCOME_ID="INCOME_ID";
    private Income incomeToEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        long id = getIntent().getLongExtra(INCOME_ID, -1);
        incomeToEdit=autonomy.getIncome(id);
        populateForm(incomeToEdit);
    }

    @Override
    protected void saveIncome(View v, String name, long duration, Long cash, Income.Type incomeType) {
        incomeToEdit.setName(name);
        incomeToEdit.setRecurrentDuration(duration);
        incomeToEdit.setRecurrentCash(cash);
        incomeToEdit.setType(incomeType);
        autonomy.editIncome(incomeToEdit);
        Intent intent = new Intent(this, IncomeListActivity.class);
        intent.putExtra(EditIncomeActivity.INCOME_ID,incomeToEdit.getId());
        startActivity(intent);

    }
}
