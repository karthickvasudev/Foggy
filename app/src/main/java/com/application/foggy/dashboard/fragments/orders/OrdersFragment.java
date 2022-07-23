package com.application.foggy.dashboard.fragments.orders;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.application.foggy.R;
import com.application.foggy.dashboard.fragments.orders.create.CreateOrderActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class OrdersFragment extends Fragment {
    private View root;
    private FloatingActionButton createOrderBtn;


    private void initInstanceVariables() {
        createOrderBtn = root.findViewById(R.id.create_order_btn_in_list_page);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_orders, container, false);
        initInstanceVariables();
        initMethod();
        return root;
    }

    private void initMethod() {
        createOrderBtnAction();
    }

    private void createOrderBtnAction() {
        createOrderBtn.setOnClickListener(view -> {
            root.getContext().startActivity(new Intent(root.getContext(), CreateOrderActivity.class));
        });
    }
}