package com.autonomyway.component;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputType;
import android.util.AttributeSet;

import com.autonomyway.model.Transformation;


public class CashInput extends AppCompatEditText {
    public CashInput(Context context) {
        super(context);
        setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        
    }

    public CashInput(Context context, AttributeSet attrs) {
        super(context, attrs);
        setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
    }

    public CashInput(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
    }

    public void setCash(Long cashInCents) {
        if (cashInCents == null || cashInCents==0) {
            setText("");
        } else {
            setText(Transformation.cashToNumber(cashInCents));
        }
    }

    public Long getCash(){
        return Transformation.numberTocash(getText().toString().trim());
    }
}
