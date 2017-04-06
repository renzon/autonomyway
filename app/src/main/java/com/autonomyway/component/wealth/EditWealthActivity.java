package com.autonomyway.component.wealth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.autonomyway.model.Wealth;

public class EditWealthActivity extends BaseNewWealthActivity {
    public static final String WEALTH_ID ="WEALTH_ID";
    private Wealth wealthToEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        long id = getIntent().getLongExtra(WEALTH_ID, -1);
        wealthToEdit=autonomy.getWealth(id);
        populateForm(wealthToEdit);
    }

    @Override
    protected void saveWealth(View v, String name, Long initialCash) {
        wealthToEdit.setName(name);
        wealthToEdit.setInitialBalance(initialCash);
        autonomy.editWealth(wealthToEdit);
        Intent intent = new Intent(this, WealthListActivity.class);
        intent.putExtra(EditWealthActivity.WEALTH_ID,wealthToEdit.getId());
        startActivity(intent);
    }
}
