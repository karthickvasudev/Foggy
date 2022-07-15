package com.application.foggy.dashboard.fragments.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.application.foggy.R;

public class PendingOrderAdapter extends RecyclerView.Adapter<PendingOrderAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView orderId;
        private TextView customerName;
        private TextView customerNumber;
        private TextView count;
        private TextView deliveryDate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orderId= itemView.findViewById(R.id.vh_po_order_id);
            customerName= itemView.findViewById(R.id.vh_po_name);
            customerNumber= itemView.findViewById(R.id.vh_po_phone_number);
            count= itemView.findViewById(R.id.vh_po_count);
            deliveryDate= itemView.findViewById(R.id.vh_po_delivery_date);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_pending_orders, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.orderId.setText("ORD-10999");
        holder.customerName.setText("Karthick");
        holder.customerNumber.setText("+91 9361312424");
        holder.count.setText("10");
        holder.deliveryDate.setText("10-09-2022\n10:23 AM");
    }

    @Override
    public int getItemCount() {
        return 15;
    }


}
