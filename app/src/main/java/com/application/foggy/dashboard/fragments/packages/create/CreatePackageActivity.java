package com.application.foggy.dashboard.fragments.packages.create;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.application.foggy.R;
import com.application.foggy.api.ApiInstance;
import com.application.foggy.loadingspinner.LoadingSpinner;
import com.application.foggy.modals.ProductModals;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreatePackageActivity extends AppCompatActivity {

    private RecyclerView productRecycler;

    private void initInstanceVariables() {
        productRecycler = findViewById(R.id.package_product_recycler);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_package);
        setTitle("Create Package");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initInstanceVariables();
        initMethods();
    }

    private void initMethods() {
        initPackageRecycler();
    }

    private void initPackageRecycler() {
        LoadingSpinner.show(this);
        Call<List<ProductModals>> api = ApiInstance.getApiRepositoryWithToken().getAllProducts();
        api.enqueue(new Callback<List<ProductModals>>() {
            @Override
            public void onResponse(Call<List<ProductModals>> call, Response<List<ProductModals>> response) {
                if (response.code() == 200) {
                    CreatePackageProductAdapter adapter = new CreatePackageProductAdapter(response.body());
                    LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false);
                    productRecycler.setLayoutManager(layoutManager);
                    productRecycler.setAdapter(adapter);
                    productRecycler.addItemDecoration(new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL));
                }
                LoadingSpinner.dismissIf();
            }

            @Override
            public void onFailure(Call<List<ProductModals>> call, Throwable t) {
                LoadingSpinner.dismissIf();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}