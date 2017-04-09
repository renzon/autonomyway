package com.autonomyway.component.transfer;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.autonomyway.R;
import com.autonomyway.component.common.DateInput;
import com.autonomyway.component.transfer.direction.DirectionInput;
import com.autonomyway.model.Node;

import java.util.Date;

public class NewTransferActivity extends BaseTransferActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        ((DateInput)findViewById(R.id.date_input)).
                setSupportFragmentManager(supportFragmentManager);
        DirectionInput directionInput = (DirectionInput) findViewById(R.id.direction_input);
        directionInput.
                setDependencies(supportFragmentManager, autonomy);

    }

    @Override
    protected void saveTransfer(View v, Node origin, Node destination, Date date, long
            cash, long duration, String detail) {
        autonomy.createTransfer(origin, destination, date, cash, duration, detail);
        showMsg(v, R.string.transfer_save_msg);

    }
}
