package com.autonomyway.component.transfer;

import android.content.res.Resources;

import com.autonomyway.R;
import com.autonomyway.component.base.BaseListAdapter;
import com.autonomyway.model.Transfer;

import java.util.List;


public class TransferListAdapter extends BaseListAdapter<Transfer, TransferRow, TransferListViewHolder> {
    public TransferListAdapter(List<Transfer> dataSet, Resources resources) {
        super(dataSet, resources);
    }

    @Override
    protected TransferListViewHolder createViewHolder(TransferRow transferRow) {
        return new TransferListViewHolder(transferRow);
    }

    @Override
    protected int getRowLayoutId() {
        return R.layout.transfer_row;
    }

}
