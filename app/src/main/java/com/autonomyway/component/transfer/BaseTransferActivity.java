package com.autonomyway.component.transfer;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.EditText;

import com.autonomyway.R;
import com.autonomyway.component.base.BaseFormActivity;
import com.autonomyway.component.base.GUIValidationException;
import com.autonomyway.component.common.CashInput;
import com.autonomyway.component.common.DateInput;
import com.autonomyway.component.common.DurationInput;
import com.autonomyway.component.transfer.direction.DirectionInput;
import com.autonomyway.component.transfer.direction.NodeMediator;
import com.autonomyway.model.Income;
import com.autonomyway.model.Node;
import com.autonomyway.model.Transfer;

import java.util.Date;

public abstract class BaseTransferActivity extends BaseFormActivity {

    private EditText detailInput;
    private CashInput cashInput;
    private boolean clearFlag = false;
    private DurationInput durationInput;


    private String getDetail() {
        return detailInput.getText().toString().trim();
    }

    private void setDetail(String name) {
        detailInput.setText(name);
    }

    protected void populateForm(Transfer transfer) {
        setDetail(transfer.getDetail());
        cashInput.setCash(transfer.getCash());
    }

    @Override
    protected void cleanForm() {
        clearFlag = true;
        setDetail("");
        cashInput.clear();
        detailInput.setText("");
        durationInput.clear();
        durationInput.clear();
    }

    private boolean validateName() {

        clearFlag = false;
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
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        ((DateInput) findViewById(R.id.date_input)).
                setSupportFragmentManager(supportFragmentManager);
        DirectionInput directionInput = (DirectionInput) findViewById(R.id.direction_input);
        final View[] durationViews = {
                findViewById(R.id.duration_input_label),
                findViewById(R.id.duration_input)
        };
        directionInput.setOriginSelectionListener(new NodeMediator.OnOriginSelectionListener() {
            @Override
            public void selected(Node node) {
                int visibility = View.GONE;
                if (node != null && node instanceof Income) {
                    visibility = View.VISIBLE;
                }
                for (View v : durationViews) {
                    v.setVisibility(visibility);
                }
            }
        });
        directionInput.
                setDependencies(supportFragmentManager, autonomy, getResources());


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
