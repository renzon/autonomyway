package com.autonomyway.component.income;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.autonomyway.R;
import com.autonomyway.model.Income;


public class IncomeRow extends CardView {
    private Resources resources;
    private Income income;

    public IncomeRow(Context context) {
        this(context,null);
    }

    public IncomeRow(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public IncomeRow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


    }

    public void setResources(Resources resources) {
        this.resources = resources;
    }

    public void populate(Income income) {
        this.income = income;
        TextView titleTextView = (TextView) findViewById(R.id.income_row_title);
        TextView incomeRateTextView = (TextView) findViewById(R.id.income_rate);
        titleTextView.setText(income.getNameDashType(resources));
        incomeRateTextView.setText("R$13,43/hora");
        setOnClickListener(new ClickListener(income));
    }

    class ClickListener implements OnClickListener{
        private Income income;

        public ClickListener(Income income) {
            this.income = income;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getContext(), EditIncomeActivity.class);
            intent.putExtra(EditIncomeActivity.INCOME_ID,income.getId());
            getContext().startActivity(intent);
        }
    }
}
