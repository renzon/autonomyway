package com.autonomyway.component.base;

import android.support.v7.widget.RecyclerView;

import com.autonomyway.model.Model;

import java.util.List;


public abstract class BaseListViewHolder<M extends Model, BR extends BaseRow<M>> extends
        RecyclerView.ViewHolder {

    private BR baseRow;

    public BaseListViewHolder(BR baseRow) {
        super(baseRow);
        this.baseRow = baseRow;
    }

    public void populate(List<M> dataSet, int position) {
        baseRow.populate(dataSet.get(position));
    }
}
