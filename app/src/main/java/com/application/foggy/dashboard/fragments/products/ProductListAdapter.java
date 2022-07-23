package com.application.foggy.dashboard.fragments.products;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.application.foggy.R;
import com.application.foggy.api.ApiInstance;
import com.application.foggy.dashboard.fragments.products.create.CreateUpdateProductActivity;
import com.application.foggy.dashboard.fragments.products.view.ViewProductActivity;
import com.application.foggy.loadingspinner.LoadingSpinner;
import com.application.foggy.modals.ProductModals;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {
    private List<ProductModals> products;

    public ProductListAdapter(List<ProductModals> products) {
        this.products = products;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        private final View layout;
        private TextView productName;
        private TextView qty;
        private TextView price;
        private Switch status;
        private String productId;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.vh_pl_product_name);
            qty = itemView.findViewById(R.id.vh_pl_qty);
            price = itemView.findViewById(R.id.vh_pl_price);
            status = itemView.findViewById(R.id.vh_pl_status_switch);
            layout = itemView.findViewById(R.id.vh_pl_layout);
            layout.setOnCreateContextMenuListener(this);
        }


        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.setHeaderTitle("Actions");
            contextMenu.add(this.getAdapterPosition(), 100, 0, "Update");
            contextMenu.add(this.getAdapterPosition(), 101, 1, "Delete");
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
        holder.productId = product.getProductId();
        holder.productName.setText(product.getProductName());
        holder.qty.setText(String.valueOf(product.getQuantity()));
        holder.price.setText("₹ " + product.getPrice());
        holder.status.setChecked(product.getActive());
        viewProductDetails(holder.layout,product.getProductId());
    }

    private void viewProductDetails(View layout, String productId) {
        layout.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), ViewProductActivity.class);
            intent.putExtra("productId",productId);
            view.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void deleteProduct(ProductFragment productFragment, int position) {
        new AlertDialog.Builder(productFragment.getContext())
                .setTitle("Are you sure want to delete?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        LoadingSpinner.show(productFragment.getContext());
                        Call<Void> api = ApiInstance.getApiRepositoryWithToken().deleteProduct(products.get(position).getProductId());
                        api.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                LoadingSpinner.dismissIf();
                                if (response.code() == 200) {
                                    productFragment.loadProductListRecycler();
                                } else {
                                    Snackbar.make(productFragment.getView(), "Oops! something went wrong", Snackbar.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                LoadingSpinner.dismissIf();
                            }
                        });

                    }
                }).setNegativeButton("No", (dialogInterface, i) -> dialogInterface.dismiss())
                .setCancelable(false)
                .create()
                .show();
    }


    public void updateProduct(ProductFragment productFragment, int groupId) {
        ProductModals productModal = products.get(groupId);
        Intent intent = new Intent(productFragment.getContext(), CreateUpdateProductActivity.class);
        intent.putExtra("type", "update");
        intent.putExtra("productModal", productModal);
        productFragment.startActivity(intent);
    }

}
