package com.autonomyway.component;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputType;
import android.util.AttributeSet;

import com.autonomyway.model.Transformation;


public class CashInput extends AppCompatEditText {
    public CashInput(Context context) {
        super(context);
        init();

    }

    private void init() {
        setRawInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
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

    public Long getCash(){
        return Transformation.numberTocash(getText().toString().trim());
    }

    public void clear() {
        setCash(null);
    }
}
