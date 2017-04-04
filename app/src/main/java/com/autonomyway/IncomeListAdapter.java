package com.autonomyway;

import android.content.res.Resources;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.autonomyway.component.IncomeRow;
import com.autonomyway.model.Income;

import java.util.List;


public class IncomeListAdapter extends RecyclerView.Adapter<IncomeListAdapter.ViewHolder> {
    List<Income> dataSet;
    private final Resources resources;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private IncomeRow incomeRow;

        public ViewHolder(IncomeRow v) {
            super(v);
            incomeRow = v;
        }

        public void populate(List<Income> dataSet, int position) {
            Income income = dataSet.get(position);
            incomeRow.populate(income);
        }
    }

    public IncomeListAdapter(List<Income> dataSet, Resources resources) {
        this.dataSet = dataSet;
        this.resources = resources;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public IncomeListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        // create a new view
        IncomeRow v = (IncomeRow) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.income_row, parent, false);
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
