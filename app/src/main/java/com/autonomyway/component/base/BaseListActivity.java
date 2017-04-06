package com.autonomyway.component.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.autonomyway.R;
import com.autonomyway.model.Model;

import java.util.List;

public abstract class BaseListActivity<M extends Model, BR extends BaseRow<M>,BLVH extends
        BaseListViewHolder<M, BR>, BLA extends BaseListAdapter<M,BR, BLVH>>
        extends ActivityWithFacadeAccess {
    private RecyclerView recyclerView;

    protected RecyclerView getRecyclerView() {
        return recyclerView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recycle_view);

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        final BaseListActivity that = this;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(that, getNewModelActivityClass());
                startActivity(intent);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    protected void onResume() {
        super.onResume();
        BLA baseListAdapter=createListAdapter(getModelList());
        getRecyclerView().setAdapter(baseListAdapter);
    }

    protected abstract BLA createListAdapter(List<M> modelList);

    @NonNull
    protected abstract Class<? extends BaseNewFormActivity> getNewModelActivityClass();

    protected abstract int getContentViewId();

    protected abstract List<M> getModelList();
}
