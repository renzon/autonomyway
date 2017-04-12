package com.autonomyway.component.transfer.direction;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.autonomyway.R;
import com.autonomyway.model.Expense;
import com.autonomyway.model.Income;
import com.autonomyway.model.Node;
import com.autonomyway.model.Wealth;

import java.util.HashMap;
import java.util.Map;


public class NodeRow extends LinearLayout {
    private static final Map<Class, Integer> classToColorId;

    static {
        classToColorId = new HashMap<>();
        classToColorId.put(Income.class, R.color.colorIncome);
        classToColorId.put(Wealth.class, R.color.colorWealth);
        classToColorId.put(Expense.class, R.color.colorExpense);
    }

    private  OnNodeSelectionListener listener;


    public NodeRow(Context context) {
        this(context, null);
    }

    public NodeRow(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NodeRow(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public NodeRow(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

    }


    public void populate(final Node node, int position) {
        TextView title = (TextView) findViewById(R.id.title);
        title.setTextColor(ContextCompat.getColor(getContext(), classToColorId.get(node.getClass())));
        title.setText(node.getName());
        int backgroundColor = position % 2 == 0 ? R.color.nodeRowBackgroundGray : R.color
                .nodeRowBackgroundWhite;
        setBackgroundColor(ContextCompat.getColor(getContext(), backgroundColor));

        if (listener!=null){
            this.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.selected(node);

                }
            });
        }

    }

    public void setOnOnNodeSelectionLister(OnNodeSelectionListener listener) {
        this.listener = listener;
    }

}
