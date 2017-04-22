package com.autonomyway;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.autonomyway.component.base.ActivityWithFacadeAccess;
import com.autonomyway.component.expense.ExpenseListActivity;
import com.autonomyway.component.income.IncomeListActivity;
import com.autonomyway.component.transfer.NewTransferActivity;
import com.autonomyway.component.transfer.TransferListActivity;
import com.autonomyway.component.wealth.WealthListActivity;

import java.util.HashMap;
import java.util.Map;

public class DashboardActivity extends ActivityWithFacadeAccess
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView workFreeTimeTextView;
    private TextView mandatoryWorkTimeTextView;
    private TextView expenseRateTextView;
    private TextView workRateTextView;
    private TextView businessRateTextView;
    private TextView incomeRateTextView;
    private TextView totalWealthTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        autonomy.createInitialData();
        setContentView(R.layout.dashboard_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        final DashboardActivity that = this;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(that, NewTransferActivity.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        workFreeTimeTextView=(TextView)findViewById(R.id.work_free_time);
        mandatoryWorkTimeTextView=(TextView)findViewById(R.id.mandatory_work_time);
        expenseRateTextView=(TextView)findViewById(R.id.expense_rate);
        workRateTextView=(TextView)findViewById(R.id.work_rate);
        businessRateTextView=(TextView)findViewById(R.id.business_rate);
        incomeRateTextView=(TextView)findViewById(R.id.income_rate);
        totalWealthTextView=(TextView)findViewById(R.id.total_wealth);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Metrics metrics=autonomy.calculateMetrics();
        expenseRateTextView.setText(metrics.getExpenseRate());
        incomeRateTextView.setText(metrics.getIncomeRate());
        workRateTextView.setText(metrics.getWorkByWorkedTimeRate());
        businessRateTextView.setText(metrics.getBusinessRate());
        totalWealthTextView.setText(metrics.getTotalWealth());

        workFreeTimeTextView.setText(metrics.getWorkFreeTime(getApplicationContext().getResources()));
        mandatoryWorkTimeTextView.setText(metrics.getMandatoryWorkTimePerMonth(getApplicationContext().getResources()));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.nav_incomes) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Map<Integer, Class<? extends Activity>> activityMap = new HashMap<>();
        activityMap.put(R.id.nav_incomes, IncomeListActivity.class);
        activityMap.put(R.id.nav_wealth, WealthListActivity.class);
        activityMap.put(R.id.nav_expenses, ExpenseListActivity.class);
        activityMap.put(R.id.nav_transfers, TransferListActivity.class);
        Intent intent = new Intent(this, activityMap.get(id));
        startActivity(intent);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
