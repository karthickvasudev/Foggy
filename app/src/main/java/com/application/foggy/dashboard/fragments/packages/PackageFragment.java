package com.application.foggy.dashboard.fragments.packages;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.application.foggy.R;
import com.application.foggy.api.ApiInstance;
import com.application.foggy.dashboard.fragments.packages.create.CreatePackageActivity;
import com.application.foggy.loadingspinner.LoadingSpinner;
import com.application.foggy.modals.ProductModals;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PackageFragment extends Fragment {

    private View root;
    private FloatingActionButton createPackageFloatBtn;
    private View layout;

    private void initInstanceVariables() {
        layout = root.findViewById(R.id.package_fragment_layout);
        createPackageFloatBtn = root.findViewById(R.id.add_package_floating_btn);
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
        createPackageBtnAction();
    }

    private void createPackageBtnAction() {
        createPackageFloatBtn.setOnClickListener(view -> {

            Call<List<ProductModals>> api = ApiInstance.getApiRepositoryWithToken().getAllProducts();
            api.enqueue(new Callback<List<ProductModals>>() {
                @Override
                public void onResponse(Call<List<ProductModals>> call, Response<List<ProductModals>> response) {
                    if (response.code() == 200) {
                        if (response.body().size() == 0) {
                            Snackbar.make(layout, "No products available!", Snackbar.LENGTH_LONG).show();
                        } else {
                            startActivity(new Intent(root.getContext(), CreatePackageActivity.class));
                        }
                    }
                }
                @Override
                public void onFailure(Call<List<ProductModals>> call, Throwable t) {
                    Snackbar.make(layout, "Oops! Something went wrong", Snackbar.LENGTH_LONG).show();
                }
            });
        });
    }

    private void packageListRecycler() {
        RecyclerView productListRecycler = root.findViewById(R.id.package_list_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.VERTICAL, false);
        productListRecycler.setLayoutManager(layoutManager);
        productListRecycler.setAdapter(new PackageListAdapter());
        productListRecycler.addItemDecoration(new DividerItemDecoration(root.getContext(), DividerItemDecoration.VERTICAL));
    }


}