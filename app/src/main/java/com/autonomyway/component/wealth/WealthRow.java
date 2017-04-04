package com.autonomyway.component.wealth;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.autonomyway.R;
import com.autonomyway.model.Transformation;
import com.autonomyway.model.Wealth;


public class WealthRow extends CardView {
    private Resources resources;
    private Wealth wealth;

    public WealthRow(Context context) {
        this(context,null);
    }

    public WealthRow(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public WealthRow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setResources(Resources resources) {
        this.resources = resources;
    }

    public void populate(Wealth wealth) {
        this.wealth = wealth;
        TextView titleTextView = (TextView) findViewById(R.id.wealth_row_title);
        TextView wealthBalanceTextView = (TextView) findViewById(R.id.wealth_balance);
        titleTextView.setText(wealth.getName());
        wealthBalanceTextView.setText(Transformation.cashToCurrency(wealth.getInitialBalance()));
        setOnClickListener(new ClickListener(wealth));
    }


    class ClickListener implements OnClickListener{
        private Wealth wealth;

        public ClickListener(Wealth wealth) {
            this.wealth = wealth;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getContext(), EditWealthActivity.class);
            intent.putExtra(EditWealthActivity.WEALTH_ID,wealth.getId());
            getContext().startActivity(intent);
        }
    }
}
