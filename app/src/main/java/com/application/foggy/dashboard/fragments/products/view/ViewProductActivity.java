package com.application.foggy.dashboard.fragments.products.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Switch;
import android.widget.TextView;

import com.application.foggy.R;
import com.application.foggy.api.ApiInstance;
import com.application.foggy.loadingspinner.LoadingSpinner;
import com.application.foggy.modals.ProductModals;
import com.application.foggy.resuables.Reusable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewProductActivity extends AppCompatActivity {

    private TextView productIdTxt;
    private TextView productName;
    private TextView quantity;
    private TextView price;
    private Switch active;
    private TextView createdUserName;
    private TextView createdUserEmail;
    private TextView createdOn;
    private TextView lastedUpdatedUserName;
    private TextView lastedUpdatedUserEmail;
    private TextView lastUpdatedOn;

    private void initInstanceVariables() {
        productIdTxt = findViewById(R.id.vp_product_id);
        productName = findViewById(R.id.vp_product_name);
        quantity = findViewById(R.id.vp_qty);
        price = findViewById(R.id.vp_price);
        active = findViewById(R.id.vp_active_switch);
        createdUserName = findViewById(R.id.vp_created_by);
        createdUserEmail = findViewById(R.id.vp_created_email);
        createdOn = findViewById(R.id.vp_created_on);
        lastedUpdatedUserName = findViewById(R.id.vp_lastupdated_by);
        lastedUpdatedUserEmail = findViewById(R.id.vp_lastupdated_email);
        lastUpdatedOn = findViewById(R.id.vp_lastupdated_on);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initInstanceVariables();
        initMethods();
    }

    private void initMethods() {
        getProductDetailsApiAndSetValues();
    }

    private void getProductDetailsApiAndSetValues() {
        LoadingSpinner.show(this);
        String productId = getIntent().getStringExtra("productId");
        this.setTitle(productId);
        Call<ProductModals> api = ApiInstance.getApiRepositoryWithToken().getProductDetails(productId);
        api.enqueue(new Callback<ProductModals>() {
            @Override
            public void onResponse(Call<ProductModals> call, Response<ProductModals> response) {
                if (response.code() == 200) {
                    ProductModals product = response.body();
                    productIdTxt.setText(product.getProductId());
                    productName.setText(product.getProductName());
                    quantity.setText(String.valueOf(product.getQuantity()));
                    price.setText("₹ " + product.getPrice());
                    active.setChecked(product.getActive());
                    createdOn.setText(product.getCreatedOn());
                    createdUserName.setText(product.getCreatedUserName());
                    createdUserEmail.setText(product.getCreatedUserEmail());
                    lastUpdatedOn.setText(product.getLastUpdatedOn());
                    lastedUpdatedUserName.setText(product.getLastUpdatedUserName());
                    lastedUpdatedUserEmail.setText(product.getLastUpdatedEmail());
                    LoadingSpinner.dismissIf();
                }
            }

            @Override
            public void onFailure(Call<ProductModals> call, Throwable t) {
                LoadingSpinner.dismissIf();
                Reusable.createErrorDialog(getApplicationContext(), "Error", "failed to get product details");
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