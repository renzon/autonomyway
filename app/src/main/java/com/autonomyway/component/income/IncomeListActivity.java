package com.autonomyway.component.income;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import com.autonomyway.R;
import com.autonomyway.component.base.BaseListActivity;
import com.autonomyway.model.Income;

import java.util.List;

public class IncomeListActivity extends BaseListActivity<Income> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    @NonNull
    protected Class<NewIncomeActivity> getNewModelActivityClass() {
        return NewIncomeActivity.class;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.income_activity_list;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getRecyclerView().setAdapter(new IncomeListAdapter(getModelList(), getResources()));
    }
    @Override
    protected List<Income> getModelList() {
        return autonomy.getIncomeList();
    }
}
