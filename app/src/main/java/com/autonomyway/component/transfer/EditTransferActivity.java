package com.autonomyway.component.transfer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.autonomyway.model.Node;
import com.autonomyway.model.Transfer;

import java.util.Date;


public class EditTransferActivity extends BaseTransferActivity {
    public static final String TRANSFER_ID ="TRANSFER_ID";
    private Transfer transferToEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        long id = getIntent().getLongExtra(TRANSFER_ID, -1);
        transferToEdit=autonomy.getTransfer(id);
        populateForm(transferToEdit);
    }

    @Override
    protected void saveTransfer(View v, Node origin, Node destination, Date date, long
            cash, long duration, String detail) {
        transferToEdit.setOrigin(origin);
        transferToEdit.setDestination(destination);
        transferToEdit.setDate(date);
        transferToEdit.setCash(cash);
        transferToEdit.setDuration(duration);
        transferToEdit.setDetail(detail);
        autonomy.editTransfer(transferToEdit);
        Intent intent = new Intent(this, TransferListActivity.class);
        intent.putExtra(EditTransferActivity.TRANSFER_ID,transferToEdit.getId());
        startActivity(intent);
    }
}
