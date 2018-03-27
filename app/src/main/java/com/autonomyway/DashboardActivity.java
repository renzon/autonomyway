package com.autonomyway;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
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

import com.autonomyway.component.base.ActivityWithFacadeAccess;
import com.autonomyway.component.base.MetricView;
import com.autonomyway.component.expense.ExpenseListActivity;
import com.autonomyway.component.income.IncomeListActivity;
import com.autonomyway.component.transfer.NewTransferActivity;
import com.autonomyway.component.transfer.TransferListActivity;
import com.autonomyway.component.wealth.WealthListActivity;
import com.autonomyway.model.InvalidData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class DashboardActivity extends ActivityWithFacadeAccess
        implements NavigationView.OnNavigationItemSelectedListener {

    private MetricView workFreeTimeMetricView;
    private MetricView mandatoryWorkTimeMetricView;
    private MetricView expenseRateMetricView;
    private MetricView workRateMetricView;
    private MetricView businessRateMetricView;
    private MetricView incomeRateMetricView;
    private MetricView totalWealthMetricView;

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
        workFreeTimeMetricView = (MetricView) findViewById(R.id.work_free_time);
        mandatoryWorkTimeMetricView = (MetricView) findViewById(R.id.mandatory_work_time);
        expenseRateMetricView = (MetricView) findViewById(R.id.expense_rate);
        workRateMetricView = (MetricView) findViewById(R.id.work_rate);
        businessRateMetricView = (MetricView) findViewById(R.id.business_rate);
        incomeRateMetricView = (MetricView) findViewById(R.id.income_rate);
        totalWealthMetricView = (MetricView) findViewById(R.id.total_wealth);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Metrics metrics = autonomy.calculateMetrics();
        expenseRateMetricView.setValue(metrics.getExpenseRate());
        incomeRateMetricView.setValue(metrics.getIncomeRate());
        workRateMetricView.setValue(metrics.getWorkByWorkedTimeRate());
        businessRateMetricView.setValue(metrics.getBusinessRate());
        totalWealthMetricView.setValue(metrics.getTotalWealth());
        workFreeTimeMetricView.setValue(
                metrics.getWorkFreeTime(getApplicationContext().getResources()));
        mandatoryWorkTimeMetricView.setValue(
                metrics.getMandatoryWorkTimePerMonth(getApplicationContext().getResources()));
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
        if (id == R.id.nav_export) {
            Intent export = new Intent();
            export.setAction(Intent.ACTION_SEND);
            Uri backupUri = autonomy.backupDb();
            export.putExtra(Intent.EXTRA_STREAM, backupUri);
            export.putExtra(Intent.EXTRA_SUBJECT, "autonomy_way.db");
            export.setType("*/*");
            export.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Intent intent = Intent.createChooser(export, getResources().getText(R.string
                    .export_to));
            startActivity(intent);

        } else if (id == R.id.nav_import) {
            Intent import_intent = new Intent(Intent.ACTION_GET_CONTENT);
            import_intent.setType("*/*");
            import_intent.addCategory(Intent.CATEGORY_OPENABLE);
            Intent intent = Intent.createChooser(import_intent, getResources().getText(R.string
                    .import_database));
            startActivityForResult(intent, 0);

        } else {
            Map<Integer, Class<? extends Activity>> activityMap = new HashMap<>();
            activityMap.put(R.id.nav_incomes, IncomeListActivity.class);
            activityMap.put(R.id.nav_wealth, WealthListActivity.class);
            activityMap.put(R.id.nav_expenses, ExpenseListActivity.class);
            activityMap.put(R.id.nav_transfers, TransferListActivity.class);
            Intent intent = new Intent(this, activityMap.get(id));
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        Uri uri = intent.getData();
        try {
            InputStream stream = getContentResolver().openInputStream(uri);
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
            String line = null;
            StringBuilder builder = new StringBuilder();
            while ((line = reader.readLine()) != null)
                builder.append(line);
            JSONObject json = new JSONObject(builder.toString());
            autonomy.restoreData(json);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InvalidData invalidData) {
            invalidData.printStackTrace();
        }
        startActivity(new Intent(this, this.getClass()));
    }

}
