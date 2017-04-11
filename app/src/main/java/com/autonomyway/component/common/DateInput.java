package com.autonomyway.component.common;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.autonomyway.R;
import com.autonomyway.model.Transformation;

import java.util.Calendar;
import java.util.Date;


public class DateInput extends LinearLayout implements DatePickerDialog.OnDateSetListener {
    private final TextView dateView;
    private Date date;
    private FragmentManager supportFragmentManager;


    public DateInput(Context context) {
        this(context, null);
    }

    public DateInput(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DateInput(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public Date getDate() {
        return date;
    }

    public DateInput(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        inflate(getContext(), R.layout.date_input, this);
        dateView = (TextView) findViewById(R.id.date_view);
        setDate(new Date());
        final DateInput that = this;
        findViewById(R.id.change_date_button).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                c.setTime(that.getDate());
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                final DatePickerDialog dpd = new DatePickerDialog(getContext(), that, year, month,
                        day);
                DatePickerFragment datePickerFragment = new DatePickerFragment(dpd);
                datePickerFragment.show(that.getSupportFragmentManager(),"datePicker");
            }
        });

    }

    public void setDate(Date date) {
        this.date = date;
        dateView.setText(Transformation.dateToString(date));
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c =Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        setDate(c.getTime());

    }

    private FragmentManager getSupportFragmentManager() {
        return supportFragmentManager;
    }

    public void setSupportFragmentManager(FragmentManager supportFragmentManager) {
        this.supportFragmentManager = supportFragmentManager;
    }



    public static final class DatePickerFragment extends DialogFragment {
        private DatePickerDialog datePickerDialog;

        public DatePickerFragment() {
        }
        @SuppressLint("ValidFragment")
        public DatePickerFragment(DatePickerDialog datePickerDialog) {
            this.datePickerDialog = datePickerDialog;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return datePickerDialog;
        }


    }
}
