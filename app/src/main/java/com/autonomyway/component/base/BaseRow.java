package com.autonomyway.component.base;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;

import com.autonomyway.model.Model;

public abstract class BaseRow<M extends Model> extends CardView {
    protected Resources resources;

    public BaseRow(Context context) {
        super(context);
    }

    public BaseRow(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseRow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void populate(M model){
        populateRow(model);
        setOnClickListener(new ClickListener(model));
    }

    public void setResources(Resources resources) {
        this.resources = resources;
    }

    protected abstract void populateRow(M model);

    @NonNull
    protected abstract Class<? extends BaseFormActivity> getEditActivity();

    @NonNull
    protected abstract String getIntentId();

    class ClickListener implements OnClickListener {
        private M model;

        public ClickListener(M model) {
            this.model = model;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getContext(), getEditActivity());
            intent.putExtra(getIntentId(), model.getId());
            getContext().startActivity(intent);
        }
    }
}
