package com.autonomyway.component.transfer;

import android.view.View;

import com.autonomyway.R;
import com.autonomyway.model.Nameable;

import java.util.Date;

public class NewTransferActivity extends BaseTransferActivity {
    @Override
    protected void saveTransfer(View v, Nameable origin, Nameable destination, Date date, long
            cash, long duration, String detail) {
        autonomy.createTransfer(origin, destination, date, cash, duration, detail);
        showMsg(v, R.string.transfer_save_msg);
    }
}
