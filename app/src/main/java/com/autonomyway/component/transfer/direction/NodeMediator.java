package com.autonomyway.component.transfer.direction;

import android.content.res.Resources;

import com.autonomyway.AutonomyWayFacade;
import com.autonomyway.R;
import com.autonomyway.model.Node;

import java.util.ArrayList;
import java.util.List;

public class NodeMediator {
    private AutonomyWayFacade facade;
    private NodeInput originInput;
    private NodeInput destinationInput;
    private Resources resources;
    private Node selectedOrigin=null;
    private Node selectedDestination=null;

    public NodeMediator(AutonomyWayFacade facade, NodeInput originInput, NodeInput destinationInput, Resources resources) {
        this.facade = facade;
        this.originInput = originInput;
        this.destinationInput = destinationInput;
        this.resources = resources;
        originInput.setNodeMediator(this);
        destinationInput.setNodeMediator(this);
        setSelectedDestination(null);
        setSelectedOrigin(null);
    }

    public Node getSelectedOrigin() {
        return selectedOrigin;
    }

    public void setSelectedOrigin(Node selectedOrigin) {
        this.selectedOrigin = selectedOrigin;
        if (selectedOrigin==null){
            originInput.setText(resources.getString(R.string.select_origin));
        }else{
            originInput.setText(selectedOrigin.getName());
        }

    }

    public Node getSelectedDestination() {
        return selectedDestination;
    }

    public void setSelectedDestination(Node selectedDestination) {
        this.selectedDestination = selectedDestination;
        if (selectedDestination==null){
            destinationInput.setText(resources.getString(R.string.select_destination));
        }else{
            destinationInput.setText(selectedDestination.getName());
        }
    }

    public List<Node> buildAvailableList(NodeInput input){

        ArrayList<Node> nodes = new ArrayList<>();
        nodes.addAll(facade.getIncomeList());
        nodes.addAll(facade.getWealthList());
        nodes.addAll(facade.getExpenseList());
        return nodes;
    }
}
