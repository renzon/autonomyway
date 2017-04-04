package com.autonomyway.component;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputType;
import android.util.AttributeSet;

import com.autonomyway.business.GUIValidationException;
import com.autonomyway.model.Transformation;

import java.text.ParseException;


public class CashInput extends AppCompatEditText {
    public CashInput(Context context) {
        super(context);
        init();

    }

    private void init() {
        setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
    }

    public CashInput(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CashInput(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setCash(Long cashInCents) {
        if (cashInCents == null || cashInCents==0) {
            setText("");
        } else {
            setText(Transformation.cashToNumber(cashInCents));
        }
    }

    public boolean validate() {
        try {
            Transformation.numberTocash(getText().toString().trim());
        } catch (ParseException e) {
           return false;
        }
        return true;
    }

    public Long getCash(){
        try {
            return Transformation.numberTocash(getText().toString().trim());
        } catch (ParseException e) {
            // No need to handle once validate should be used
        }
        return 0L;
    }

    public void clear() {
        setCash(null);
    }
}
