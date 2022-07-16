package com.application.foggy.dashboard.fragments.packages;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.application.foggy.R;

public class PackageListAdapter extends RecyclerView.Adapter<PackageListAdapter.ViewHolder> {
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView packageName;
        private TextView validity;
        private TextView price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            packageName = itemView.findViewById(R.id.vh_p_package_name);
            validity = itemView.findViewById(R.id.vh_p_validity);
            price = itemView.findViewById(R.id.vh_p_price);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_package_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.packageName.setText("Basic 3 Months");
        holder.validity.setText("3 Months");
        holder.price.setText("₹.999");
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
