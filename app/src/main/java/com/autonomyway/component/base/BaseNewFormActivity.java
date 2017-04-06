package com.autonomyway.component.base;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.autonomyway.R;


public abstract class BaseNewFormActivity extends ActivityWithFacadeAccess {

    protected abstract void cleanForm();

    protected abstract void validateForm();

    protected int getToolbarId() {
        return R.id.toolbar;
    }

    protected int getSaveButtonId() {
        return R.id.save_button;
    }

    protected abstract int getContentViewId();

    protected abstract void save(View v);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        Toolbar toolbar = (Toolbar) findViewById(getToolbarId());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Button saveButton = (Button) findViewById(getSaveButtonId());
        final BaseNewFormActivity that = this;
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    that.validateForm();
                } catch (GUIValidationException e) {
                    that.showMsg(v, R.string.form_error_msg);
                    return;
                }
                that.save(v);
            }


        });
    }

    protected void showMsg(View v, int id) {
        Snackbar.make(v, getString(id), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
}
