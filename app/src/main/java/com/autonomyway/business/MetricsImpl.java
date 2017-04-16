package com.autonomyway.business;

import android.content.res.Resources;

import com.autonomyway.Metrics;
import com.autonomyway.R;
import com.autonomyway.business.transfer.BusinessRateVisitor;
import com.autonomyway.business.transfer.ExpenseRateVisitor;
import com.autonomyway.business.transfer.IncomeRateVisitor;
import com.autonomyway.business.transfer.SingleIncomeRateVisitor;
import com.autonomyway.business.transfer.TransferVisitor;
import com.autonomyway.business.transfer.WorkByWorkedTimeRateVisitor;
import com.autonomyway.model.Expense;
import com.autonomyway.model.Income;
import com.autonomyway.model.Transfer;
import com.autonomyway.model.Transformation;
import com.autonomyway.model.Wealth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MetricsImpl implements Metrics {
    final private IncomeRateVisitor incomeRateVisitor;
    final private Resources resources;
    final private TransferVisitor expenseRateVisitor;
    final private TransferVisitor businessRateVisitor;
    final private TransferVisitor workRateVisitor;
    final private Map<Long, TransferVisitor> singleIncomeVisitors;
    final private long totalWealth;
    private final static long HOUR_IN_MILISECONDS = 1000 * 60 * 60;


    public MetricsImpl(Resources resources, Map<Long, Income> incomeMap, List<Wealth> wealthList,
                       List<Expense> expenses, List<Transfer> transfersOrderedByDateAsc) {
        this.resources = resources;


        long totalWealth = 0;
        for (Wealth w : wealthList) {
            totalWealth += w.getBalance();
        }
        this.totalWealth = totalWealth;
        long totalDuration=0;
        if (!transfersOrderedByDateAsc.isEmpty()) {
            long begin = transfersOrderedByDateAsc.get(0).getDate().getTime();
            long end = transfersOrderedByDateAsc.get(transfersOrderedByDateAsc.size() - 1).getDate()
                    .getTime();
            totalDuration = (end - begin) / HOUR_IN_MILISECONDS;
        }
        expenseRateVisitor = new ExpenseRateVisitor(totalDuration, resources);
        businessRateVisitor = new BusinessRateVisitor(totalDuration, resources);
        workRateVisitor = new WorkByWorkedTimeRateVisitor(totalDuration, resources);
        incomeRateVisitor = new IncomeRateVisitor(totalDuration, resources);
        singleIncomeVisitors=new HashMap<>();
        for(Map.Entry<Long, Income> entry: incomeMap.entrySet()){
            singleIncomeVisitors.put(
                    entry.getKey(),
                    new SingleIncomeRateVisitor(entry.getValue(), totalDuration,resources));
        }
        List<TransferVisitor> visitors = new ArrayList<>(singleIncomeVisitors.values());
        visitors.add(expenseRateVisitor);
        visitors.add(businessRateVisitor);
        visitors.add(workRateVisitor);
        visitors.add(incomeRateVisitor);
        for (Transfer t : transfersOrderedByDateAsc) {
            for (TransferVisitor v : visitors) {
                t.getOrigin().acceptAsOrigin(v, t.getCash(), t.getDuration());
                t.getDestination().acceptAsDestination(v, t.getCash(), t.getDuration());
            }
        }
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
    public String getWorkByWorkedTimeRate() {
        return extractMetric(workRateVisitor);
    }

    @Override
    public String getIncomeRate() {
        return extractMetric(incomeRateVisitor);
    }

    @Override
    public String getTotalWealth() {
        return Transformation.cashToCurrency(totalWealth);
    }

    @Override
    public String getMandatoryWorkTimePerMonth() {
        long expenseRate=0;
        long businessRate=0;
        try {
            expenseRate = expenseRateVisitor.getMetricNumber();
            businessRate = businessRateVisitor.getMetricNumber();
        }catch (ArithmeticException _){
            return resources.getString(R.string.metric_not_enough_data_available);
        }
        if (expenseRate <= 0 || businessRate >= expenseRate) {
            return resources.getString(R.string.metric_can_leave_forever_without_working);
        }
        long workRate = workRateVisitor.getMetricNumber();
        if (workRate == 0) {
            return resources.getString(R.string.metric_not_enough_data_available);
        }
        long burnRate = expenseRate - businessRate;
        long amountNeedByMonth = burnRate * 30 * 24;
        return Transformation.durationForHumans(amountNeedByMonth / workRate);
    }

    @Override
    public String getWorkFreeTime() {
        long expenseRate=0;
        long businessRate=0;
        try {
             expenseRate = expenseRateVisitor.getMetricNumber();
             businessRate = businessRateVisitor.getMetricNumber();
        }catch (ArithmeticException _){
            return resources.getString(R.string.metric_not_enough_data_available);
        }
        if (expenseRate <= 0 || businessRate >= expenseRate) {
            return resources.getString(R.string.metric_can_leave_forever_without_working);
        }

        long burnRate = expenseRate - businessRate;
        long hoursToLive = totalWealth / burnRate;
        return Transformation.durationForHumans(hoursToLive);

    }

    @Override
    public String getIncomeRate(Income income) {
        if (singleIncomeVisitors == null) {
            return resources.getString(R.string.metric_not_enough_data_available);
        }
        return singleIncomeVisitors.get(income.getId()).getMetric();
    }
}
