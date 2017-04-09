package com.autonomyway.component.transfer.direction;

import com.autonomyway.AutonomyWayFacade;
import com.autonomyway.model.Node;

import java.util.ArrayList;
import java.util.List;

public class NodeMediator {
    private AutonomyWayFacade facade;
    private NodeInput originInput;
    private NodeInput destinationInput;
    private Node selectedOrigin=null;
    private Node selectedDestination=null;

    public NodeMediator(AutonomyWayFacade facade, NodeInput originInput, NodeInput destinationInput) {
        this.facade = facade;
        this.originInput = originInput;
        this.destinationInput = destinationInput;
        originInput.setNodeMediator(this);
        destinationInput.setNodeMediator(this);
    }

    public Node getSelectedOrigin() {
        return selectedOrigin;
    }

    public void setSelectedOrigin(Node selectedOrigin) {
        this.selectedOrigin = selectedOrigin;
    }

    public Node getSelectedDestination() {
        return selectedDestination;
    }

    public void setSelectedDestination(Node selectedDestination) {
        this.selectedDestination = selectedDestination;
    }

    public List<Node> buildAvailableList(NodeInput input){

        ArrayList<Node> nodes = new ArrayList<>();
        nodes.addAll(facade.getIncomeList());
        nodes.addAll(facade.getWealthList());
        nodes.addAll(facade.getExpenseList());
        return nodes;
    }
}
