package com.autonomyway.business;

import android.content.res.Resources;

import com.autonomyway.Metrics;
import com.autonomyway.R;
import com.autonomyway.business.transfer.BusinessRateVisitor;
import com.autonomyway.business.transfer.ExpenseRateVisitor;
import com.autonomyway.business.transfer.IncomeRateVisitor;
import com.autonomyway.business.transfer.TransferVisitor;
import com.autonomyway.business.transfer.WorkRateVisitor;
import com.autonomyway.model.Expense;
import com.autonomyway.model.Income;
import com.autonomyway.model.Transfer;
import com.autonomyway.model.Transformation;
import com.autonomyway.model.Wealth;

import java.util.List;


public class MetricsImpl implements Metrics {
    private IncomeRateVisitor incomeRateVisitor;
    private Resources resources;
    private TransferVisitor expenseRateVisitor;
    private TransferVisitor businessRateVisitor;
    private TransferVisitor workRateVisitor;
    private String totalWealth;
    private final static long HOUR_IN_MILISECONDS = 1000 * 60 * 60;


    public MetricsImpl(Resources resources, List<Income> incomes, List<Wealth> wealthList,
                       List<Expense> expenses, List<Transfer> transfersOrderedByDateAsc) {
        this.resources = resources;

        long totalWealthLong = 0;
        for (Wealth w : wealthList) {
            totalWealthLong += w.getBalance();
        }
        this.totalWealth = Transformation.cashToCurrency(totalWealthLong);
        if (transfersOrderedByDateAsc.size() <= 1) {
            return;
        }


        long begin = transfersOrderedByDateAsc.get(0).getDate().getTime();
        long end = transfersOrderedByDateAsc.get(transfersOrderedByDateAsc.size() - 1).getDate()
                .getTime();
        long totalDuration = (end - begin) / HOUR_IN_MILISECONDS;
        if (totalDuration == 0) {
            return;
        }
        expenseRateVisitor = new ExpenseRateVisitor(totalDuration, resources);
        businessRateVisitor = new BusinessRateVisitor(totalDuration, resources);
        workRateVisitor = new WorkRateVisitor(totalDuration, resources);
        incomeRateVisitor = new IncomeRateVisitor(totalDuration, resources);
        TransferVisitor[] visitors = {
                expenseRateVisitor, businessRateVisitor, workRateVisitor, incomeRateVisitor};
        for (Transfer t : transfersOrderedByDateAsc) {
            for (TransferVisitor v : visitors) {
                t.getOrigin().acceptAsOrigin(v, t.getCash(), t.getDuration());
                t.getDestination().acceptAsDestination(v, t.getCash(), t.getDuration());
            }
        }
    }


    @Override
    public String getRetirement() {
        return resources.getString(R.string.metric_not_enough_data_available);
    }

    private String extractMetric(TransferVisitor visitor) {
        if (visitor == null) {
            return resources.getString(R.string.metric_not_enough_data_available);
        }
        return visitor.getMetric();
    }

    @Override
    public String getExpenseRate() {
        return extractMetric(expenseRateVisitor);
    }

    @Override
    public String getBusinessRate() {
        return extractMetric(businessRateVisitor);
    }

    @Override
    public String getWorkRate() {
        return extractMetric(workRateVisitor);
    }

    @Override
    public String getIncomeRate() {
        return extractMetric(incomeRateVisitor);
    }

    @Override
    public String getTotalWealth() {
        return totalWealth;
    }
}
