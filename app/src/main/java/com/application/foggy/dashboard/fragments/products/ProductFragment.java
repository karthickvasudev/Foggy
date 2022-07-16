package com.application.foggy.dashboard.fragments.products;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.application.foggy.R;

public class ProductFragment extends Fragment {

    private View root;

    private void initInstanceVariable() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_product, container, false);
        initInstanceVariable();
        initMethods();
        return root;
    }

    private void initMethods() {
        productListRecycler();
    }

    private void productListRecycler() {
        RecyclerView productList = root.findViewById(R.id.product_list_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.VERTICAL, false);
        ProductListAdapter productListAdapter = new ProductListAdapter();
        productList.setLayoutManager(layoutManager);
        productList.setAdapter(productListAdapter);
        productList.addItemDecoration(new DividerItemDecoration(root.getContext(), DividerItemDecoration.VERTICAL));
    }

}