package com.group4.gamecontrollershop.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group4.gamecontrollershop.R;
import com.group4.gamecontrollershop.model.Order;
import com.group4.gamecontrollershop.OrderDetailActivity; // New activity for showing order details

import java.text.SimpleDateFormat;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private static final String ORDER_SUCCESS = "success";
    private List<Order> orderList;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private Context context; // To start the new activity

    public HistoryAdapter(List<Order> orderList, Context context) {
        this.orderList = orderList;
        this.context = context;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.bind(order);

        // Set an onClickListener to the entire order item
        holder.itemView.setOnClickListener(v -> {
            // Open the order detail activity and pass the order details
            Intent intent = new Intent(context, OrderDetailActivity.class);
            intent.putExtra("orderId", order.getId());
            intent.putExtra("orderTotalAmount", order.getTotalAmount());
            intent.putExtra("orderDate", dateFormat.format(order.getOrderDate()));
            intent.putExtra("orderStatus", order.getStatus());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    class HistoryViewHolder extends RecyclerView.ViewHolder {
        private ImageView orderStatus;
        private TextView orderId, orderPrice, orderDate;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            orderId = itemView.findViewById(R.id.orderIdText);
            orderPrice = itemView.findViewById(R.id.totalAmountText);
            orderDate = itemView.findViewById(R.id.orderDateText);
            orderStatus = itemView.findViewById(R.id.orderStatus);
        }

        @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
        public void bind(Order order) {
            orderId.setText("Order: #" + order.getId());
            orderPrice.setText("Amount: $" + order.getTotalAmount());
            orderDate.setText("Order at " + dateFormat.format(order.getOrderDate()));

            if (ORDER_SUCCESS.equals(order.getStatus())) {
                orderStatus.setImageResource(R.drawable.success);
            } else {
                orderStatus.setImageResource(R.drawable.failure);
            }
        }
    }

}
