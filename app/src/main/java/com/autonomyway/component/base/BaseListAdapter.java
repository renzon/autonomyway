package com.autonomyway.component.base;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.autonomyway.model.Identifiable;

import java.util.List;


public abstract class BaseListAdapter<M extends Identifiable, BR extends BaseRow<M>,
        BLVH extends BaseListViewHolder<M, BR>>
        extends RecyclerView.Adapter<BLVH> {

    List<M> dataSet;
    private final Resources resources;

    public BaseListAdapter(List<M> dataSet, Resources resources) {
        this.dataSet = dataSet;
        this.resources = resources;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public BLVH onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        BR baseRow = (BR) LayoutInflater.from(parent.getContext())
                .inflate(getRowLayoutId(), parent, false);
        baseRow.setResources(resources);
        // set the view's size, margins, paddings and layout parameters
        return createViewHolder(baseRow);
    }

    protected abstract BLVH createViewHolder(BR baseRow);

    protected abstract int getRowLayoutId();

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(BLVH holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.populate(dataSet, position);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
