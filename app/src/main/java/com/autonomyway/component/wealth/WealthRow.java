package com.autonomyway.component.wealth;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.TextView;

import com.autonomyway.R;
import com.autonomyway.component.base.BaseRow;
import com.autonomyway.model.Transformation;
import com.autonomyway.model.Wealth;


public class WealthRow extends BaseRow<Wealth> {

    public WealthRow(Context context) {
        this(context, null);
    }

    public WealthRow(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WealthRow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void populateRow(Wealth wealth) {
        TextView titleTextView = (TextView) findViewById(R.id.wealth_row_title);
        TextView wealthBalanceTextView = (TextView) findViewById(R.id.wealth_balance);
        titleTextView.setText(wealth.getName());
        wealthBalanceTextView.setText(Transformation.cashToCurrency(wealth.getBalance()));
    }

    @NonNull
    @Override
    protected Class<EditWealthActivity> getEditActivity() {
        return EditWealthActivity.class;
    }

    @NonNull
    @Override
    protected String getIntentId() {
        return EditWealthActivity.WEALTH_ID;
    }
}
