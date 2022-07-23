package com.application.foggy.dashboard.fragments.orders.create;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;

import com.application.foggy.R;
import com.application.foggy.api.ApiInstance;
import com.application.foggy.loadingspinner.LoadingSpinner;
import com.application.foggy.modals.CustomerModal;
import com.application.foggy.modals.ProductModals;

import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateOrderActivity extends AppCompatActivity {

    private RecyclerView productList;
    private TextView totalPrice;
    private AutoCompleteTextView phoneNumber;
    private TextView customerName;
    private List<String> phoneNumberList;
    private TextView newCustomerNotify;

    private void initInstanceVariable() {
        productList = findViewById(R.id.co_product_list_recycler);
        totalPrice = findViewById(R.id.co_total_price);
        totalPrice.setText("₹ 0");
        phoneNumber = findViewById(R.id.co_customer_number);
        customerName = findViewById(R.id.co_customer_name);
        newCustomerNotify = findViewById(R.id.co_new_customer_notify);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initInstanceVariable();
        initMethod();

    }

    private void initMethod() {
        phoneNumberInit();
        productListRecyclerViewInit();
        autoCompleteCustomerDetails();

    }

    private void phoneNumberInit() {
        phoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 5) {
                    boolean flag = phoneNumberList.stream().anyMatch(number->number.startsWith(charSequence.toString()));
                    if (flag) {
                        newCustomerNotify.setVisibility(View.GONE);
                    } else {
                        newCustomerNotify.setVisibility(View.VISIBLE);
                    }
                }else{
                    newCustomerNotify.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void autoCompleteCustomerDetails() {
        LoadingSpinner.show(this);
        Call<List<CustomerModal>> api = ApiInstance.getApiRepositoryWithToken().getAllCustomers();
        api.enqueue(new Callback<List<CustomerModal>>() {
            @Override
            public void onResponse(Call<List<CustomerModal>> call, Response<List<CustomerModal>> response) {
                if (response.code() == 200) {
                    phoneNumberList = response.body().stream().map(customerModal -> {
                        return customerModal.getPhoneNumber() + "-" + customerModal.getName();
                    }).collect(Collectors.toList());
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, phoneNumberList);
                    phoneNumber.setAdapter(arrayAdapter);
                    phoneNumber.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            TextView textView = view.findViewById(android.R.id.text1);
                            String[] split = textView.getText().toString().split("-");
                            phoneNumber.setText(split[0]);
                            customerName.setText(split[1]);
                            newCustomerNotify.setVisibility(View.GONE);
                        }
                    });
                }
                LoadingSpinner.dismissIf();
            }

            @Override
            public void onFailure(Call<List<CustomerModal>> call, Throwable t) {
                LoadingSpinner.dismissIf();
            }
        });

    }

    private void productListRecyclerViewInit() {
        CreateOrderActivity createOrderActivity = this;
        Call<List<ProductModals>> api = ApiInstance.getApiRepositoryWithToken().getAllProducts();
        api.enqueue(new Callback<List<ProductModals>>() {
            @Override
            public void onResponse(Call<List<ProductModals>> call, Response<List<ProductModals>> response) {
                if (response.code() == 200) {
                    CreateOrderProductListAdapter adapter = new CreateOrderProductListAdapter(createOrderActivity, response.body());
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                    productList.setAdapter(adapter);
                    productList.setLayoutManager(layoutManager);
                    productList.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
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

    public void updateTotalPrice(Integer price) {
        totalPrice.setText("₹ " + String.valueOf(price));
    }
}