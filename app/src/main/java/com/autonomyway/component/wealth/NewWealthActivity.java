package com.autonomyway.component.wealth;

import android.view.View;

import com.autonomyway.R;

public class NewWealthActivity extends BaseWealthActivity {
    @Override
    protected void saveWealth(View v, String name, Long initialBalance) {
        autonomy.createWealth(name, initialBalance);
        showMsg(v, R.string.wealth_save_msg);
    }
}
