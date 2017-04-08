package com.autonomyway.component.transfer;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.autonomyway.R;
import com.autonomyway.component.common.CashInput;
import com.autonomyway.component.base.BaseFormActivity;
import com.autonomyway.component.base.GUIValidationException;
import com.autonomyway.component.common.DurationInput;
import com.autonomyway.model.Node;
import com.autonomyway.model.Transfer;

import java.util.Date;

public abstract class BaseTransferActivity extends BaseFormActivity {

    private EditText detailInput;
    private CashInput cashInput;
    private boolean clearFlag=false;
    private DurationInput durationInput;


    private String getDetail() {
        return detailInput.getText().toString().trim();
    }

    private void setDetail(String name) {
        detailInput.setText(name);
    }

    protected void populateForm(Transfer transfer){
        setDetail(transfer.getDetail());
        cashInput.setCash(transfer.getCash());
    }
    @Override
    protected void cleanForm() {
        clearFlag=true;
        setDetail("");
        cashInput.clear();
        detailInput.setText("");
        durationInput.clear();
        durationInput.clear();
    }

    private boolean validateName() {

        clearFlag=false;
        return true;
    }

    @Override
    protected void validateForm() {
        boolean isValid = validateName();
        if (!isValid) {
            throw new GUIValidationException();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailInput = (EditText) findViewById(R.id.name_input);
        cashInput = (CashInput) findViewById(R.id.cash_input);
        durationInput = (DurationInput) findViewById(R.id.duration_input);

    }

    @Override
    protected void save(View v) {
//        saveTransfer(v, getDetail(), cashInput.getCash());
    }

    @Override
    protected int getContentViewId() {
        return R.layout.transfer_activity_form;
    }

    protected abstract void saveTransfer(View v, Node origin, Node destination, Date date, long
            cash, long duration, String detail);


}
