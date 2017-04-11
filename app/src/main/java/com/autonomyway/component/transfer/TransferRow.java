package com.autonomyway.component.transfer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.TextView;

import com.autonomyway.R;
import com.autonomyway.component.base.BaseRow;
import com.autonomyway.model.Transformation;
import com.autonomyway.model.Transfer;


public class TransferRow extends BaseRow<Transfer> {

    public TransferRow(Context context) {
        this(context, null);
    }

    public TransferRow(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TransferRow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void populateRow(Transfer transfer) {
        TextView originTextView = (TextView) findViewById(R.id.transfer_origin);
        TextView destinationTextView = (TextView) findViewById(R.id.transfer_destination);
        TextView dateTextView = (TextView) findViewById(R.id.transfer_date);
        TextView durationTextView = (TextView) findViewById(R.id.transfer_duration);
        TextView cashTextView = (TextView) findViewById(R.id.transfer_cash);
        originTextView.setText(transfer.getOrigin().getName());
        destinationTextView.setText(transfer.getDestination().getName());
        cashTextView.setText(Transformation.cashToCurrency(transfer.getCash()));
        dateTextView.setText(Transformation.dateToString(transfer.getDate()));
        durationTextView.setText(Transformation.durationToString(transfer.getDuration()));
    }

    @NonNull
    @Override
    protected Class<EditTransferActivity> getEditActivity() {
        return EditTransferActivity.class;
    }

    @NonNull
    @Override
    protected String getIntentId() {
        return EditTransferActivity.TRANSFER_ID;
    }
}
