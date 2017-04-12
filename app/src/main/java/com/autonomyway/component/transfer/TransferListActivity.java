package com.autonomyway.component.transfer;

import android.support.annotation.NonNull;

import com.autonomyway.R;
import com.autonomyway.component.base.BaseListActivity;
import com.autonomyway.model.Transfer;

import java.util.List;

public class TransferListActivity extends BaseListActivity<Transfer, TransferRow,TransferListViewHolder,
        TransferListAdapter> {

    @Override
    protected TransferListAdapter createListAdapter(List modelList) {
        return new TransferListAdapter(getModelList(), getResources());
    }

    @Override
    @NonNull
    protected Class<NewTransferActivity> getNewModelActivityClass() {
        return NewTransferActivity.class;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.transfer_activity_list;
    }


    @Override
    protected List<Transfer> getModelList() {
        return autonomy.getTransferList();
    }
}
