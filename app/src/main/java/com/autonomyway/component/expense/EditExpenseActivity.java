package com.autonomyway.component.expense;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.autonomyway.model.Expense;

public class EditExpenseActivity extends BaseExpenseActivity {
    public static final String EXPENSE_ID ="EXPENSE_ID";
    private Expense expenseToEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        long id = getIntent().getLongExtra(EXPENSE_ID, -1);
        expenseToEdit=autonomy.getExpense(id);
        populateForm(expenseToEdit);
    }

    @Override
    protected void saveExpense(View v, String name, Long recurrentCash) {
        expenseToEdit.setName(name);
        expenseToEdit.setRecurrentCash(recurrentCash);
        autonomy.editExpense(expenseToEdit);
        Intent intent = new Intent(this, ExpenseListActivity.class);
        intent.putExtra(EditExpenseActivity.EXPENSE_ID,expenseToEdit.getId());
        startActivity(intent);
    }
}
