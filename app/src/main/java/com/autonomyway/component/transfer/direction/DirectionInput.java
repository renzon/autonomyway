package com.autonomyway.component.transfer.direction;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.autonomyway.AutonomyWayFacade;
import com.autonomyway.R;
import com.autonomyway.model.Node;


public class DirectionInput extends LinearLayout {
    private NodeMediator nodeMediator;
    private NodeMediator.OnOriginSelectionListener listener;

    public DirectionInput(Context context) {
        this(context, null);
    }

    public DirectionInput(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DirectionInput(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public DirectionInput(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        inflate(getContext(), R.layout.transfer_direction_input, this);

    }



    public void setDependencies(FragmentManager supportFragmentManager, AutonomyWayFacade autonomyFacade, Resources resources) {

        NodeInput originInput = (NodeInput) findViewById(R.id.origin_input);
        NodeInput destinationInput = (NodeInput) findViewById(R.id.destination_input);
        nodeMediator = new NodeMediator(autonomyFacade, originInput, destinationInput, resources);
        nodeMediator.setOnOriginSelectionListener(listener);
        originInput.setOnNodeSelectionListener(new NodeRow.OnNodeSelectionListener() {
            @Override
            public void selected(Node node) {
                nodeMediator.setSelectedOrigin(node);
            }
        });
        destinationInput.setOnNodeSelectionListener(new NodeRow.OnNodeSelectionListener() {
            @Override
            public void selected(Node node) {
                nodeMediator.setSelectedDestination(node);
            }
        });
        originInput.setSupportFragmentManager(supportFragmentManager);
        destinationInput.setSupportFragmentManager(supportFragmentManager);
    }


    public void setOriginSelectionListener(NodeMediator.OnOriginSelectionListener listener) {
        this.listener = listener;
    }
}
