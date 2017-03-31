package com.autonomyway;

import android.content.res.Resources;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.autonomyway.model.Income;

import java.util.List;


public class IncomeListAdapter extends RecyclerView.Adapter<IncomeListAdapter.ViewHolder> {
    List<Income> dataSet;
    private final Resources resources;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView titleTextView;
        public TextView incomeRateTextView;
        private final Resources resources;

        public ViewHolder(CardView v, Resources resources) {
            super(v);
            titleTextView = (TextView) v.findViewById(R.id.income_row_title);
            incomeRateTextView = (TextView) v.findViewById(R.id.income_rate);
            this.resources = resources;
        }

        public void populate(List<Income> dataSet, int position) {
            Income income = dataSet.get(position);


            this.titleTextView.setText(income.getNameDashType(resources));

            this.incomeRateTextView.setText("R$13,43/hora");

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
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.income_row, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v, resources);
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
