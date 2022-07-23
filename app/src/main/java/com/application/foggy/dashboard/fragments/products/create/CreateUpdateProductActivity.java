package com.application.foggy.dashboard.fragments.products.create;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.application.foggy.R;
import com.application.foggy.api.ApiInstance;
import com.application.foggy.loadingspinner.LoadingSpinner;
import com.application.foggy.modals.ProductModals;
import com.google.android.gms.common.util.Strings;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateUpdateProductActivity extends AppCompatActivity {

    private Button createProductBtn;
    private TextInputLayout productName;
    private EditText productQty;
    private EditText productPrice;
    private Switch productActiveSwitch;
    private TextWatcher watcher;
    private String updateProductId;

    private void initInstanceVariable() {
        createProductBtn = findViewById(R.id.create_product_btn);
        productName = findViewById(R.id.product_name_label);
        productQty = findViewById(R.id.product_quantity);
        productPrice = findViewById(R.id.product_price);
        productActiveSwitch = findViewById(R.id.product_active_swtich);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_product);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initInstanceVariable();
        initMethods();
    }

    private void initMethods() {
        initThisActivity();
        initFormValidation();
        createUpdateProductBtnAction();
    }

    private void createUpdateProductBtnAction() {
        createProductBtn.setOnClickListener(view -> {
            LoadingSpinner.show(this);
            ProductModals product = ProductModals.builder()
                    .productName(productName.getEditText().getText().toString())
                    .quantity(Integer.parseInt(productQty.getText().toString()))
                    .price(Double.parseDouble(productPrice.getText().toString()))
                    .active(productActiveSwitch.isChecked())
                    .build();
            Call<ProductModals> api;
            if (getIntent().getStringExtra("type").equals("create")) {
                api = ApiInstance.getApiRepositoryWithToken().createProduct(product);
            } else {
                api = ApiInstance.getApiRepositoryWithToken().updateProduct(updateProductId, product);
            }
            api.enqueue(new Callback<ProductModals>() {
                @Override
                public void onResponse(Call<ProductModals> call, Response<ProductModals> response) {
                    if(response.code()==200) {
                        finish();
                    }else{
                        LoadingSpinner.dismissIf();
                        new AlertDialog.Builder(view.getContext())
                                .setTitle("Error")
                                .setMessage("Oops something went wrong")
                                .setCancelable(false)
                                .setNegativeButton("Ok", (dialogInterface, i) -> {
                                    dialogInterface.dismiss();
                                })
                                .create()
                                .show();
                    }
                }

                @Override
                public void onFailure(Call<ProductModals> call, Throwable t) {
                    LoadingSpinner.dismissIf();
//                    Snackbar.make(view,"Oops! Something went wrong",Snackbar.LENGTH_LONG).show();
                    new AlertDialog.Builder(view.getContext())
                            .setTitle("Error")
                            .setMessage("Oops something went wrong")
                            .setCancelable(false)
                            .setNegativeButton("Ok", (dialogInterface, i) -> {
                                dialogInterface.dismiss();
                            })
                            .create()
                            .show();

                }
            });

        });
    }

    private void initFormValidation() {
        watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!Strings.isEmptyOrWhitespace(productName.getEditText().getText().toString())
                        && !Strings.isEmptyOrWhitespace(productQty.getText().toString())
                        && !Strings.isEmptyOrWhitespace(productPrice.getText().toString())) {
                    createProductBtn.setEnabled(true);
                } else {
                    createProductBtn.setEnabled(false);
                }
                System.out.println("working");
                System.out.println(productQty.getText().toString());
                System.out.println(Strings.isEmptyOrWhitespace(productQty.getText().toString()));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        productName.getEditText().addTextChangedListener(watcher);
        productQty.addTextChangedListener(watcher);
        productPrice.addTextChangedListener(watcher);
    }

    private void initThisActivity() {
        if (getIntent().getStringExtra("type").equals("create")) {
            this.setTitle("Create Product");
            createProductBtn.setText("Create Product");
        }
        if (getIntent().getStringExtra("type").equals("update")) {
            ProductModals product = (ProductModals) getIntent().getSerializableExtra("productModal");
            this.setTitle("Update Product - "+product.getProductId());
            createProductBtn.setText("Update Product");
            setValuesForUpdate(product);
        }
    }

    private void setValuesForUpdate(ProductModals productModal) {
        updateProductId = productModal.getProductId();
        productName.getEditText().setText(productModal.getProductName());
        productQty.setText(String.valueOf(productModal.getQuantity()));
        productPrice.setText(String.valueOf(productModal.getPrice()));
        productActiveSwitch.setChecked(productModal.getActive());
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