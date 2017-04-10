package com.autonomyway.component.transfer.direction;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.autonomyway.R;
import com.autonomyway.model.Node;

import java.util.List;


public class NodeInput extends LinearLayout {

    private final TextView label;
    private NodeMediator nodeMediator;
    private NodeRow.OnNodeSelectionListener listener;

    public NodeInput(Context context) {
        this(context, null);
    }

    public NodeInput(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public void setText(String text){
        label.setText(text);
    }

    public NodeInput(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public void setOnNodeSelectionListener(NodeRow.OnNodeSelectionListener listener){
        this.listener = listener;
    }

    public NodeInput(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        inflate(getContext(), R.layout.transfer_node_input,this);
        label= (TextView) findViewById(R.id.label);
    }

    public void setSupportFragmentManager(final FragmentManager supportFragmentManager) {
        findViewById(R.id.button).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                new NodeDialogFragment(buildAvailableNodes(), new NodeRow.OnNodeSelectionListener() {
                    @Override
                    public void selected(Node node) {
                        if (node==null){
                            addErrorState();
                        }else{
                            removeErrorState();
                        }
                        if (listener!=null){
                            listener.selected(node);
                        }
                    }
                }).show(supportFragmentManager);
            }
        });
    }

    private List<Node> buildAvailableNodes() {
        return nodeMediator.buildAvailableList(this);
    }

    public void setNodeMediator(NodeMediator nodeMediator) {
        this.nodeMediator = nodeMediator;
    }

    public void addErrorState(){
        label.setError("Required Field");

    }

    public void removeErrorState(){
        label.setError(null);
    }
}
