package com.application.foggy.dashboard.fragments.products;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.application.foggy.R;
import com.application.foggy.modals.ProductModals;

import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {
    private List<ProductModals> products;

    public ProductListAdapter(List<ProductModals> products) {
        this.products = products;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView productName;
        private TextView qty;
        private TextView price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.vh_pl_product_name);
            qty = itemView.findViewById(R.id.vh_pl_qty);
            price = itemView.findViewById(R.id.vh_pl_price);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_product_list, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductModals product = products.get(position);
        holder.productName.setText(product.getProductName());
        holder.qty.setText(String.valueOf(product.getQuantity()));
        holder.price.setText("₹." + product.getPrice());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }


}
