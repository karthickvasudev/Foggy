package com.application.foggy.dashboard.fragments.home.pendingordertab;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.application.foggy.R;
import com.application.foggy.dashboard.fragments.home.PendingOrderAdapter;

public class PendingOrderFragment extends Fragment {
    private View root;

    private void initInstanceVariable(){

    }

    private void initMethods(){
        pendingOrderRecyclerView();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_pending_order, container, false);
        initInstanceVariable();
        initMethods();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void pendingOrderRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.VERTICAL, false);
        PendingOrderAdapter pendingOrderAdapter = new PendingOrderAdapter();

        RecyclerView pendingOrderRecyclerView = root.findViewById(R.id.pending_order_recycler_view);
        pendingOrderRecyclerView.setLayoutManager(linearLayoutManager);
        pendingOrderRecyclerView.setAdapter(pendingOrderAdapter);
        pendingOrderRecyclerView.addItemDecoration(new DividerItemDecoration(root.getContext(), 1));

    }
}