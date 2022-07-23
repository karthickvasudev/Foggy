package com.application.foggy.dashboard.fragments.orders.create;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.application.foggy.R;
import com.application.foggy.modals.ProductModals;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import retrofit2.Callback;


public class CreateOrderProductListAdapter extends RecyclerView.Adapter<CreateOrderProductListAdapter.ViewHolder> {
    private List<ProductModals> productList;
    private CreateOrderActivity createOrderActivity;
    private List<Double> totalPrice=new ArrayList<>();
    public CreateOrderProductListAdapter(CreateOrderActivity createOrderActivity, List<ProductModals> products) {
        this.createOrderActivity = createOrderActivity;
        productList = products.stream().filter(product -> product.getActive().equals(true)).collect(Collectors.toList());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView productName;
        private TextView pricePerCount;
        private Button incrementBtn;
        private EditText count;
        private Button decrementBtn;
        private TextView price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.vh_co_pl_product_name);
            pricePerCount = itemView.findViewById(R.id.vh_co_pl_product_price_per_count);
            incrementBtn = itemView.findViewById(R.id.vh_co_pl_increment);
            count = itemView.findViewById(R.id.vh_co_pl_product_count);
            decrementBtn = itemView.findViewById(R.id.vh_co_pl_decrement);
            price = itemView.findViewById(R.id.vh_co_pl_product_price);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_product_list_create_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int pos=position;
        AtomicInteger count = new AtomicInteger(0);
        Double price = productList.get(position).getPrice();

        holder.productName.setText(productList.get(position).getProductName());
        holder.pricePerCount.setText("₹ " + productList.get(position).getPrice());
        holder.count.setText(String.valueOf(count.get()));
        holder.price.setText("₹ " + 0);
        totalPrice.add((double) 0);

        holder.count.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().isEmpty()) {
                    count.set(Integer.parseInt(charSequence.toString()));
                    holder.price.setText("₹ " + (count.get() * price));
                    totalPrice.set(pos,Double.parseDouble(String.valueOf(count.get() * price)));
                    createOrderActivity.updateTotalPrice(totalPrice.stream().mapToInt(Double::intValue).sum());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        holder.decrementBtn.setOnClickListener(view -> {
            if (count.get() > 0) {
                count.getAndDecrement();
                holder.count.setText(String.valueOf(count));
                holder.price.setText("₹ " + (count.get() * price));
                totalPrice.set(position,Double.parseDouble(String.valueOf(count.get() * price)));
                createOrderActivity.updateTotalPrice(totalPrice.stream().mapToInt(Double::intValue).sum());
            }
        });
        holder.incrementBtn.setOnClickListener(view -> {
            count.incrementAndGet();
            holder.count.setText(String.valueOf(count));
            holder.price.setText("₹ " + (count.get() * price));
            totalPrice.set(position,Double.parseDouble(String.valueOf(count.get() * price)));
            createOrderActivity.updateTotalPrice(totalPrice.stream().mapToInt(Double::intValue).sum());
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

}
