package com.autonomyway.component;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.autonomyway.R;


public class DurationInput extends LinearLayout {
    private EditText hourInput;
    private EditText minuteInput;

    public DurationInput(Context context) {
        this(context, null);
    }

    public DurationInput(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DurationInput(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public DurationInput(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.duration_input, this);
        hourInput = (EditText) findViewById(R.id.hour_input);
        minuteInput = (EditText) findViewById(R.id.minute_input);
    }

    public void setDuration(long totalMinutes) {
        Long hour = totalMinutes / 60;
        String hourStr = hour.toString();
        if (hour == 0) {
            hourStr = "";
        }
        Long minutes = totalMinutes % 60;
        String minutesStr = minutes.toString();
        if (minutes == 0) {
            minutesStr = "";
        }
        hourInput.setText(hourStr);
        minuteInput.setText(minutesStr);
    }

    private long parseInt(EditText input){
        String valoe = input.getText().toString().trim();
        if ("".equals(valoe)){
            return 0;
        }
        return Long.parseLong(valoe);
    }

    public long getDuration() {
        long hours=parseInt(hourInput);
        long minutes = parseInt(minuteInput);
        return hours * 60 + minutes;
    }

    public void clear() {
        setDuration(0);
    }
}
