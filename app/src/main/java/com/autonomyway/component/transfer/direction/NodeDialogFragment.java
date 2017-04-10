package com.autonomyway.component.transfer.direction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.autonomyway.R;
import com.autonomyway.model.Node;

import java.util.Arrays;
import java.util.List;

public class NodeDialogFragment extends DialogFragment {
    private static final String LIST_BUNDLE_KEY = "LIST_BUNDLE_KEY";
    private static final String NODE_SELECTION = "nodeSelection";

    private List<Node> nodeList;
    private NodeRow.OnNodeSelectionListener listener;

    public NodeDialogFragment() {
    }

    @SuppressLint("ValidFragment")
    public NodeDialogFragment(List<Node> nodeList, NodeRow.OnNodeSelectionListener listener) {
        this.nodeList = nodeList;
        this.listener = listener;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(LIST_BUNDLE_KEY, nodeList.toArray(new Node[nodeList.size()]));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null && nodeList == null) {
            Node[] nodes = (Node[]) savedInstanceState.getSerializable(LIST_BUNDLE_KEY);
            nodeList = Arrays.asList(nodes);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.node_picker, container, false);
        RecyclerView rv = (RecyclerView) v.findViewById(R.id.recycle_view);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(new NodeAdapter(nodeList, new NodeRow.OnNodeSelectionListener() {
            @Override
            public void selected(Node node) {
                if (listener != null) {
                    listener.selected(node);
                }
                dismiss();
            }
        }));
        return v;
    }


    public void show(FragmentManager supportFragmentManager) {
        show(supportFragmentManager, NODE_SELECTION);
    }


}