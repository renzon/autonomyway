package com.autonomyway.component.transfer.direction;

import android.support.v7.widget.RecyclerView;

import com.autonomyway.model.Node;

import java.util.List;


public class NodeViewHolder extends RecyclerView.ViewHolder {
    private NodeRow nodeRow;

    public NodeViewHolder(NodeRow nodeRow) {
        super(nodeRow);
        this.nodeRow = nodeRow;
    }

    public void populate(List<Node> nodes, int position) {
        nodeRow.populate(nodes.get(position), position);
    }
}
