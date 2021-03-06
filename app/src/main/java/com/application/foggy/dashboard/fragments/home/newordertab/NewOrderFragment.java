package com.application.foggy.dashboard.fragments.home.newordertab;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.application.foggy.R;
import com.application.foggy.dashboard.fragments.home.PendingOrderAdapter;

public class NewOrderFragment extends Fragment {
    private View root;

    private void initInstanceVariables() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_new_order, container, false);
        initInstanceVariables();
        initMethods();
        return root;
    }

    private void initMethods() {
        newOrderRecyclerView();
    }

    private void newOrderRecyclerView() {
        RecyclerView newOrderRecycler = root.findViewById(R.id.inprogress_order_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.VERTICAL, false);
        PendingOrderAdapter adapter = new PendingOrderAdapter();
        newOrderRecycler.setLayoutManager(layoutManager);
        newOrderRecycler.setAdapter(adapter);
        newOrderRecycler.addItemDecoration(new DividerItemDecoration(root.getContext(), DividerItemDecoration.VERTICAL));
    }
}