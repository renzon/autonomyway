package com.autonomyway.component.wealth;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.autonomyway.ActivityWithFacadeAccess;
import com.autonomyway.R;
import com.autonomyway.model.Wealth;

import java.util.List;

public class WealthListActivity extends ActivityWithFacadeAccess {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wealth_activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.wealth_recycle_view);

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        final WealthListActivity that = this;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(that, NewWealthActivity.class);
                startActivity(intent);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Wealth> wealthList = autonomy.getWealthList();
        recyclerView.setAdapter(new WealthListAdapter(wealthList, getResources()));
    }
}
