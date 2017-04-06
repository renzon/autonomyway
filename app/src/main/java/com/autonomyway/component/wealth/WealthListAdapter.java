package com.autonomyway.component.wealth;

import android.content.res.Resources;

import com.autonomyway.R;
import com.autonomyway.component.base.BaseListAdapter;
import com.autonomyway.model.Wealth;

import java.util.List;


public class WealthListAdapter extends BaseListAdapter<Wealth, WealthRow, WealthListViewHolder> {
    public WealthListAdapter(List<Wealth> dataSet, Resources resources) {
        super(dataSet, resources);
    }

    @Override
    protected WealthListViewHolder createViewHolder(WealthRow wealthRow) {
        return new WealthListViewHolder(wealthRow);
    }

    @Override
    protected int getRowLayoutId() {
        return R.layout.wealth_row;
    }

}
