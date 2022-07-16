package com.application.foggy.dashboard.fragments.products;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.application.foggy.R;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {

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
        holder.productName.setText("General Garments");
        holder.qty.setText("1");
        holder.price.setText("₹.8");
    }

    @Override
    public int getItemCount() {
        return 3;
    }


}
