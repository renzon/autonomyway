package com.autonomyway.component.income;

import android.content.res.Resources;

import com.autonomyway.R;
import com.autonomyway.component.base.BaseListAdapter;
import com.autonomyway.model.Income;

import java.util.List;


public class IncomeListAdapter extends BaseListAdapter<Income, IncomeRow, IncomeListViewHolder> {
    public IncomeListAdapter(List<Income> dataSet, Resources resources) {
        super(dataSet, resources);
    }

    @Override
    protected IncomeListViewHolder createViewHolder(IncomeRow incomeRow) {
        return new IncomeListViewHolder(incomeRow);
    }

    @Override
    protected int getRowLayoutId() {
        return R.layout.income_row;
    }

}
