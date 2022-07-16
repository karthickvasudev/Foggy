package com.application.foggy.dashboard.fragments.packages;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.application.foggy.R;

public class PackageFragment extends Fragment {

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
        root = inflater.inflate(R.layout.fragment_package, container, false);
        initInstanceVariables();
        initMethods();
        return root;
    }

    private void initMethods() {
        packageListRecycler();
    }

    private void packageListRecycler() {
        RecyclerView productListRecycler = root.findViewById(R.id.product_list_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(root.getContext(), RecyclerView.VERTICAL, false);
        productListRecycler.setLayoutManager(layoutManager);
        productListRecycler.setAdapter(new PackageListAdapter());
        productListRecycler.addItemDecoration(new DividerItemDecoration(root.getContext(), DividerItemDecoration.VERTICAL));
    }
}