package com.autonomyway.component.wealth;

import android.support.annotation.NonNull;

import com.autonomyway.R;
import com.autonomyway.component.base.BaseListActivity;
import com.autonomyway.model.Wealth;

import java.util.List;

public class WealthListActivity extends BaseListActivity<Wealth,WealthRow,WealthListViewHolder,
        WealthListAdapter> {

    @Override
    protected WealthListAdapter createListAdapter(List modelList) {
        return new WealthListAdapter(getModelList(), getResources());
    }

    @Override
    @NonNull
    protected Class<NewWealthActivity> getNewModelActivityClass() {
        return NewWealthActivity.class;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.wealth_activity_list;
    }


    @Override
    protected List<Wealth> getModelList() {
        return autonomy.getWealthList();
    }
}
