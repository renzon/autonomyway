package com.autonomyway.component.base;

import android.animation.LayoutTransition;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.autonomyway.R;


public class MetricView extends CardView {
    private final TextView titleTextView;
    private final TextView valueTextView;
    private final TextView helpTextView;

    public MetricView(Context context) {
        this(context, null);
    }

    public MetricView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MetricView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayoutTransition(new LayoutTransition());
        inflate(getContext(), R.layout.metric_view, this);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MetricView);
        titleTextView = (TextView) findViewById(R.id.title);
        String title = a.getString(R.styleable.MetricView_metric_title);
        valueTextView = (TextView) findViewById(R.id.value);
        setTitle(title);
        String value = a.getString(R.styleable.MetricView_metric_value);
        setValue(value);

        String help = a.getString(R.styleable.MetricView_metric_help);
        helpTextView = (TextView) findViewById(R.id.help);
        setHelp(help);
        a.recycle();
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int visibility = helpTextView.getVisibility() == VISIBLE ? GONE : VISIBLE;
                helpTextView.setVisibility(visibility);
            }
        });
    }


    public void setTitle(String title) {
        titleTextView.setText(title);
    }

    public void setHelp(String help) {
        helpTextView.setText(help);
    }

    public void setValue(String title) {
        valueTextView.setText(title);
    }
}
