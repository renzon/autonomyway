package com.autonomyway.component.transfer.direction;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.autonomyway.R;
import com.autonomyway.model.Node;

import java.util.List;


public class NodeAdapter extends RecyclerView.Adapter<NodeViewHolder> {
    private List<Node> nodeList;
    private NodeRow.OnNodeSelectionListener listener;

    public NodeAdapter(List<Node> nodeList, NodeRow.OnNodeSelectionListener listener) {
        this.nodeList = nodeList;
        this.listener = listener;
    }

    @Override
    public NodeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        NodeRow row= (NodeRow) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.node_row, parent, false);
        row.setOnOnNodeSelectionLister(listener);
        return new NodeViewHolder(row);
    }

    @Override
    public void onBindViewHolder(NodeViewHolder holder, int position) {
        holder.populate(nodeList,position);
    }

    @Override
    public int getItemCount() {
        return nodeList.size();
    }
}
