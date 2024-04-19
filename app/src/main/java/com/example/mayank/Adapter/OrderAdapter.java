package com.example.mayank.Adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mayank.Domain.Order;
import com.example.mayank.R;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private List<Order> orderList;

    public OrderAdapter(List<Order> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);

        // Bind the order data to the views in the ViewHolder
        holder.emailTextView.setText(order.getEmail());
        holder.addressTextView.setText(order.getAddress());
        holder.phoneTextView.setText(order.getPhone());
        holder.amountTextView.setText(String.format("$%.2f", order.getAmount()));
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView emailTextView, addressTextView, phoneTextView, amountTextView;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);

            emailTextView = itemView.findViewById(R.id.emailTextView);
            addressTextView = itemView.findViewById(R.id.addressTextView);
            phoneTextView = itemView.findViewById(R.id.phoneTextView);
            amountTextView = itemView.findViewById(R.id.amountTextView);
        }
    }
}
