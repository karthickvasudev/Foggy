package com.application.foggy.dashboard.fragments.products.create;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.application.foggy.R;
import com.application.foggy.api.ApiInstance;
import com.application.foggy.loadingspinner.LoadingSpinner;
import com.application.foggy.modals.ProductModals;
import com.google.android.gms.common.util.Strings;
import com.google.android.material.snackbar.Snackbar;
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
        initInstanceVariable();
        initMethods();
    }

    private void initMethods() {
        initThisActivity();
        initFormValidation();
        createProductBtnAction();
    }

    private void createProductBtnAction() {
        createProductBtn.setOnClickListener(view -> {
            LoadingSpinner.show(view.getContext());
            ProductModals product = ProductModals.builder()
                    .productName(productName.getEditText().getText().toString())
                    .quantity(Integer.parseInt(productQty.getText().toString()))
                    .price(Double.parseDouble(productPrice.getText().toString()))
                    .active(productActiveSwitch.isActivated())
                    .build();
            Call<ProductModals> api = ApiInstance.getApiRepositoryWithToken().createProduct(product);
            api.enqueue(new Callback<ProductModals>() {
                @Override
                public void onResponse(Call<ProductModals> call, Response<ProductModals> response) {

                }

                @Override
                public void onFailure(Call<ProductModals> call, Throwable t) {
                    LoadingSpinner.dismissIf();
//                    Snackbar.make(view,"Oops! Something went wrong",Snackbar.LENGTH_LONG).show();
                    new AlertDialog.Builder(view.getContext())
                            .setTitle("Error")
                            .setMessage("Oops something went wrong")
                            .setCancelable(false)
                            .setNegativeButton("Ok",(dialogInterface, i) -> {dialogInterface.dismiss();})
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (getIntent().getStringExtra("type").equals("create")) {
            this.setTitle("Create Product");
            createProductBtn.setText("Create Product");
        }
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