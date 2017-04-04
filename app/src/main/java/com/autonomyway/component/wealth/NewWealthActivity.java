package com.autonomyway.component.wealth;

import android.view.View;

import com.autonomyway.R;
import com.autonomyway.model.Income;

public class NewWealthActivity extends BaseWealthActivity {
    @Override
    protected void saveWealth(View v, String name, Long initialBalance) {
        autonomy.createWealth(name, initialBalance);
        cleanForm();
        showMsg(v, R.string.wealth_save_msg);
    }
}
