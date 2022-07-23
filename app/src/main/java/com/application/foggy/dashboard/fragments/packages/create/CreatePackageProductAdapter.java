package com.application.foggy.dashboard.fragments.packages.create;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.application.foggy.R;
import com.application.foggy.modals.ProductModals;

import java.util.List;
import java.util.stream.Collectors;

public class CreatePackageProductAdapter extends RecyclerView.Adapter<CreatePackageProductAdapter.ViewHolder> {
    private List<ProductModals> productList;

    public CreatePackageProductAdapter(List<ProductModals> productList) {
        this.productList = productList.stream().filter(productModals -> productModals.getActive().equals(true)).collect(Collectors.toList());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView productName;
        private EditText quantity;
        private EditText price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.vh_pp_product_name);
            quantity = itemView.findViewById(R.id.vh_pp_qty);
            price = itemView.findViewById(R.id.vh_pp_price);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_package_product_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductModals product = productList.get(position);
        if (product.getActive()) {
            holder.productName.setText(product.getProductName());
        }

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


}
