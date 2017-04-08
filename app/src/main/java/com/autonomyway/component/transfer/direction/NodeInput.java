package com.autonomyway.component.transfer.direction;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.autonomyway.R;


public class NodeInput extends LinearLayout {
    public NodeInput(Context context) {
        this(context, null);
    }

    public NodeInput(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NodeInput(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public NodeInput(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        inflate(getContext(), R.layout.transfer_node_input,this);
    }
}
