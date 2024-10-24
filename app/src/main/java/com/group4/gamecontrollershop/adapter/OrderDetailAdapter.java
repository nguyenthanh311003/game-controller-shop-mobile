package com.group4.gamecontrollershop.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group4.gamecontrollershop.R;
import com.group4.gamecontrollershop.model.OrderDetail;
import com.bumptech.glide.Glide; // Import Glide for loading images

import java.util.List;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.OrderDetailViewHolder> {

    private List<OrderDetail> orderDetailList;

    public OrderDetailAdapter(List<OrderDetail> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }

    @NonNull
    @Override
    public OrderDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_detail_item, parent, false);
        return new OrderDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailViewHolder holder, int position) {
        OrderDetail orderDetail = orderDetailList.get(position);

        // Set product name and quantity
        holder.productName.setText("Product ID: " + orderDetail.getProductId()); // Adjust as needed
        holder.productQuantity.setText("Quantity: " + orderDetail.getQuantity());

        // Assuming you have price information in OrderDetail
        holder.productPrice.setText("$" + orderDetail.getPrice());
        holder.productOldPrice.setText("$" + (orderDetail.getPrice() * 1.75)); // Example for old price

        // Load the product image (ensure you have a valid image URL or resource)
        String imageUrl = "your_image_url_here"; // Replace with actual image URL or resource
        Glide.with(holder.itemView.getContext())
                .load(imageUrl)
                .into(holder.productImage);
    }

    @Override
    public int getItemCount() {
        return orderDetailList.size();
    }

    public static class OrderDetailViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName;
        TextView productQuantity;
        TextView productPrice;
        TextView productOldPrice;

        public OrderDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
            productName = itemView.findViewById(R.id.product_name);
            productQuantity = itemView.findViewById(R.id.product_quantity); // Make sure this matches the XML ID
            productPrice = itemView.findViewById(R.id.product_price);
            productOldPrice = itemView.findViewById(R.id.product_old_price);
        }
    }
}
