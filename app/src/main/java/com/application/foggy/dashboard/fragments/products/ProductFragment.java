package com.application.foggy.dashboard.fragments.products;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.application.foggy.R;
import com.application.foggy.dashboard.fragments.products.create.CreateUpdateProductActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ProductFragment extends Fragment {
    private boolean onCreate = true;
    private View root;
    private FloatingActionButton createProductActionBtn;

    private void initInstanceVariable() {
        createProductActionBtn = root.findViewById(R.id.create_product_action_btn);
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

    @Override
    public void onResume() {
        super.onResume();
        if (!onCreate) {

        }
        onCreate = false;
    }

    private void initMethods() {
        productListRecycler();
        createProductAction();
    }


    private void formValidation(){

    }

    private void createProductAction() {
        createProductActionBtn.setOnClickListener(view -> {
            Intent intent = new Intent(root.getContext(), CreateUpdateProductActivity.class);
            intent.putExtra("type", "create");
            startActivity(intent);
        });
    }

    private void productListRecycler() {
        RecyclerView productList = root.findViewById(R.id.product_list_recycler);
        productList.setVisibility(View.VISIBLE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.VERTICAL, false);
        ProductListAdapter productListAdapter = new ProductListAdapter();
        productList.setLayoutManager(layoutManager);
        productList.setAdapter(productListAdapter);
        productList.addItemDecoration(new DividerItemDecoration(root.getContext(), DividerItemDecoration.VERTICAL));
    }

}