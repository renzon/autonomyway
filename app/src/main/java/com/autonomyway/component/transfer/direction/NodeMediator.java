package com.autonomyway.component.transfer.direction;

import android.content.res.Resources;
import android.support.annotation.NonNull;

import com.autonomyway.AutonomyWayFacade;
import com.autonomyway.R;
import com.autonomyway.model.Expense;
import com.autonomyway.model.Income;
import com.autonomyway.model.Node;
import com.autonomyway.model.Wealth;

import java.util.ArrayList;
import java.util.List;

public class NodeMediator {
    private AutonomyWayFacade facade;
    private NodeInput originInput;
    private NodeInput destinationInput;
    private Resources resources;
    private Node origin = null;
    private Node destination = null;
    private OnOriginSelectionListener originSelectionlistener;


    public NodeMediator(AutonomyWayFacade facade, NodeInput originInput, NodeInput destinationInput, Resources resources) {
        this.facade = facade;
        this.originInput = originInput;
        this.destinationInput = destinationInput;
        this.resources = resources;
        originInput.setNodeMediator(this);
        destinationInput.setNodeMediator(this);
        setDestination(null);
        setOrigin(null);
    }

    public Node getOrigin() {
        return origin;
    }

    public void setOrigin(Node origin) {
        this.origin = origin;
        if (origin == null) {
            originInput.setText(resources.getString(R.string.select_origin));
        } else {
            originInput.setText(origin.getName());
            fixPossibleInconsistency(origin);
        }
        if (originSelectionlistener != null) {
            originSelectionlistener.selected(origin);
        }

    }

    private void fixPossibleInconsistency(@NonNull Node origin) {
        if (destination == null) {
            return;
        }
        if (origin.equals(destination) ||
                (origin instanceof Income && destination instanceof Income) ||
                (origin instanceof Expense && destination instanceof Expense)) {
            setDestination(null);
        }
    }

    public Node getDestination() {
        return destination;
    }

    public void setDestination(Node destination) {
        this.destination = destination;
        if (destination == null) {
            destinationInput.setText(resources.getString(R.string.select_destination));
        } else {
            destinationInput.setText(destination.getName());
        }
    }

    public List<Node> buildAvailableList(NodeInput input) {
        if (input == originInput) {
            return buildOriginAvailableList();
        } else if (input == destinationInput) {
            return buildDestinationAvailableList();
        }

        throw new RuntimeException("input param sholuld be on of " + originInput + " or " +
                destinationInput);
    }

    private List<Node> buildDestinationAvailableList() {
        ArrayList<Node> nodes = new ArrayList<>();
        if (origin == null) {
            nodes.addAll(facade.getExpenseList());
            nodes.addAll(facade.getWealthList());
            nodes.addAll(facade.getIncomeList());
            return nodes;
        }

        if (origin instanceof Income) {
            nodes.addAll(facade.getWealthList());
            nodes.addAll(facade.getExpenseList());
        } else if (origin instanceof Expense) {
            nodes.addAll(facade.getWealthList());
            nodes.addAll(facade.getIncomeList());
        } else if (origin instanceof Wealth) {
            nodes.addAll(facade.getExpenseList());
            for (Wealth w : facade.getWealthList()) {
                // It doesn't make sense transfer wealth to itself
                if (w != origin) {
                    nodes.add(w);
                }

            }
            nodes.addAll(facade.getIncomeList());
        }


        return nodes;
    }

    private List<Node> buildOriginAvailableList() {
        ArrayList<Node> nodes = new ArrayList<>();
        nodes.addAll(facade.getIncomeList());
        nodes.addAll(facade.getWealthList());
        nodes.addAll(facade.getExpenseList());
        return nodes;
    }

    public void setOnOriginSelectionListener(OnOriginSelectionListener listener) {

        this.originSelectionlistener = listener;
    }

    public interface OnOriginSelectionListener {
        void selected(Node node);
    }
}
