package com.autonomyway.component.wealth;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.autonomyway.R;
import com.autonomyway.model.Wealth;

import java.util.List;


public class WealthListAdapter extends RecyclerView.Adapter<WealthListAdapter.ViewHolder> {
    List<Wealth> dataSet;
    private final Resources resources;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private WealthRow wealthRow;

        public ViewHolder(WealthRow v) {
            super(v);
            wealthRow = v;
        }

        public void populate(List<Wealth> dataSet, int position) {
            Wealth wealth = dataSet.get(position);
            wealthRow.populate(wealth);
        }
    }

    public WealthListAdapter(List<Wealth> dataSet, Resources resources) {
        this.dataSet = dataSet;
        this.resources = resources;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public WealthListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        // create a new view
        WealthRow v = (WealthRow) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.wealth_row, parent, false);
        v.setResources(resources);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
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
