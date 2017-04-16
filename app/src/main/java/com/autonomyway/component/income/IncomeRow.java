package com.autonomyway.component.income;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.TextView;

import com.autonomyway.AutonomyWayFacade;
import com.autonomyway.R;
import com.autonomyway.business.AutonomyWay;
import com.autonomyway.component.base.BaseRow;
import com.autonomyway.model.Income;


public class IncomeRow extends BaseRow<Income> {

    public IncomeRow(Context context) {
        this(context, null);
    }

    public IncomeRow(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IncomeRow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


    }

    @Override
    protected void populateRow(Income income) {
        TextView titleTextView = (TextView) findViewById(R.id.income_row_title);
        TextView incomeRateTextView = (TextView) findViewById(R.id.income_rate);
        titleTextView.setText(income.getNameDashType(resources));
        AutonomyWayFacade facade = AutonomyWay.getInstance(getContext());
        incomeRateTextView.setText(facade.calculateMetrics(resources).getIncomeRate(income));
    }


    @Override
    @NonNull
    protected Class<EditIncomeActivity> getEditActivity() {
        return EditIncomeActivity.class;
    }

    @Override
    @NonNull
    protected String getIntentId() {
        return EditIncomeActivity.INCOME_ID;
    }
}
