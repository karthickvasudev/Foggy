package com.application.foggy.dashboard.fragments.products;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.application.foggy.R;
import com.application.foggy.api.ApiInstance;
import com.application.foggy.dashboard.fragments.products.create.CreateUpdateProductActivity;
import com.application.foggy.modals.ProductModals;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductFragment extends Fragment {
    private boolean onCreate = true;
    private View root;
    private FloatingActionButton createProductActionBtn;
    private ConstraintLayout productLayout;
    private ProgressBar productLoadingSpinner;
    private View oopsError;
    private ProductListAdapter productListAdapter;
    private TextView noProduct;

    private void initInstanceVariable() {
        createProductActionBtn = root.findViewById(R.id.create_product_action_btn);
        productLayout = root.findViewById(R.id.product_layout);
        productLoadingSpinner = root.findViewById(R.id.product_loading);
        oopsError = root.findViewById(R.id.oops_error);
        noProduct = root.findViewById(R.id.no_product_msg);
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
            System.out.println("resumed----");
            loadProductListRecycler();
        }
        onCreate = false;
    }

    private void initMethods() {
        loadProductListRecycler();
        createProductAction();
    }

    private void createProductAction() {
        createProductActionBtn.setOnClickListener(view -> {
            Intent intent = new Intent(root.getContext(), CreateUpdateProductActivity.class);
            intent.putExtra("type", "create");
            startActivity(intent);
        });
    }

    public void loadProductListRecycler() {
        oopsError.setVisibility(View.GONE);
        productLoadingSpinner.setVisibility(View.VISIBLE);
        Call<List<ProductModals>> api = ApiInstance.getApiRepositoryWithToken().getAllProducts();
        api.enqueue(new Callback<List<ProductModals>>() {
            @Override
            public void onResponse(Call<List<ProductModals>> call, Response<List<ProductModals>> response) {
                if (response.code() == 200) {
                    productLoadingSpinner.setVisibility(View.GONE);
                    RecyclerView productList = root.findViewById(R.id.product_list_recycler);
                    productList.setVisibility(View.VISIBLE);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.VERTICAL, false);
                    productListAdapter = new ProductListAdapter(response.body());
                    productList.setLayoutManager(layoutManager);
                    productList.setAdapter(productListAdapter);
                    productList.addItemDecoration(new DividerItemDecoration(root.getContext(), DividerItemDecoration.VERTICAL));
                    noProduct.setVisibility(response.body().size() == 0 ? View.VISIBLE : View.GONE);
                } else {
                    productLoadingSpinner.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "" + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ProductModals>> call, Throwable t) {
                productLoadingSpinner.setVisibility(View.GONE);
                oopsError.setVisibility(View.VISIBLE);
                Snackbar.make(getView(), "Oops something went wrong", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case 100:
                productListAdapter.updateProduct(this, item.getGroupId());
                break;
            case 101:
                productListAdapter.deleteProduct(this, item.getGroupId());
                break;
        }
        return super.onContextItemSelected(item);
    }
}