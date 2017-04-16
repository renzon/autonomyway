package com.autonomyway.component.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.autonomyway.AutonomyWayFacade;
import com.autonomyway.business.AutonomyWay;


public abstract class ActivityWithFacadeAccess extends AppCompatActivity {
    protected AutonomyWayFacade autonomy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        autonomy = AutonomyWay.getInstance(getApplicationContext());
    }
}
