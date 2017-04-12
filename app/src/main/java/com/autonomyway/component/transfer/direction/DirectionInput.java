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
    private OnNodeSelectionListener originSelectionListener;
    private OnNodeSelectionListener destinationSelectionListener;
    private NodeInput originInput;
    private NodeInput destinationInput;

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

        originInput = (NodeInput) findViewById(R.id.origin_input);
         destinationInput = (NodeInput) findViewById(R.id.destination_input);
        nodeMediator = new NodeMediator(autonomyFacade, originInput, destinationInput, resources);
        nodeMediator.setOriginSelectionListener(originSelectionListener);
        nodeMediator.setDestinationSelectionListener(destinationSelectionListener);
        originInput.setOnNodeSelectionListener(new OnNodeSelectionListener() {
            @Override
            public void selected(Node node) {
                nodeMediator.setOrigin(node);
            }
        });
        destinationInput.setOnNodeSelectionListener(new OnNodeSelectionListener() {
            @Override
            public void selected(Node node) {
                nodeMediator.setDestination(node);
            }
        });
        originInput.setSupportFragmentManager(supportFragmentManager);
        destinationInput.setSupportFragmentManager(supportFragmentManager);
    }


    public void setOriginSelectionListener(OnNodeSelectionListener listener) {
        this.originSelectionListener = listener;
    }



    public Node getOrigin() {
        return nodeMediator.getOrigin();
    }


    public Node getDestination() {
        return nodeMediator.getDestination();
    }

    public boolean validate() {
        
        return validateDestination() & validateOrigin();
    }

    protected boolean validateOrigin() {
        if (nodeMediator.getOrigin()==null){
            originInput.addErrorState();
            return false;
        }
        originInput.removeErrorState();
        return true;
    }

    protected boolean validateDestination() {
        if (nodeMediator.getDestination()==null){
            destinationInput.addErrorState();
            return false;
        }
        destinationInput.removeErrorState();
        return true;
    }


    public void setDestinationSelectionListener(OnNodeSelectionListener destinationSelectionListener) {
        this.destinationSelectionListener = destinationSelectionListener;
    }
}
