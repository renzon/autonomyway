package com.autonomyway.component.income;

import android.support.annotation.NonNull;

import com.autonomyway.R;
import com.autonomyway.component.base.BaseListActivity;
import com.autonomyway.model.Income;

import java.util.List;

public class IncomeListActivity extends BaseListActivity<Income,IncomeRow,IncomeListViewHolder,
        IncomeListAdapter> {

    @Override
    protected IncomeListAdapter createListAdapter(List modelList) {
        return new IncomeListAdapter(getModelList(), getResources());
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
    protected List<Income> getModelList() {
        return autonomy.getIncomeList();
    }
}
