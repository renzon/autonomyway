package com.autonomyway.business.transfer;

import android.content.res.Resources;

import com.autonomyway.R;
import com.autonomyway.model.Income;
import com.autonomyway.model.Transformation;

public class BusinessRateVisitor extends TransferVisitorAdapter {
    long totalCash = 0;

    public BusinessRateVisitor(long duration, Resources resources) {
        super(duration, resources);
    }

    @Override
    public void visitAsOrigin(Income income, long transferCash, long transferDuration) {
        if(income.getType()==Income.Type.BUSINESS){
            totalCash += transferCash;
        }

    }

    @Override
    public void visitAsDestination(Income income, long transferCash, long transferDuration) {
        if(income.getType()==Income.Type.BUSINESS){
            totalCash -= transferCash;
        }
    }

    @Override
    public String getMetric() {
        if (getTotalDuration() == 0) {
            return getString(R.string.metric_not_enough_data_available);
        }
        long rate = totalCash / getTotalDuration();
        return Transformation.cashRate(rate,getResources());
    }

}
