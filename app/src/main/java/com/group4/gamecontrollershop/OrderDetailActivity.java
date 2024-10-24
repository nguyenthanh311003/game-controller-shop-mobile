package com.group4.gamecontrollershop;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class OrderDetailActivity extends AppCompatActivity {

    private TextView orderIdText, totalAmountText, orderDateText, userFullNameText, userAddressText, userPhoneText, userEmailText, orderStatusText;
    private ImageView orderStatusImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail); // Ensure this matches the new layout XML

        // Bind views
        orderIdText = findViewById(R.id.orderId);
        totalAmountText = findViewById(R.id.orderTotal);
        orderDateText = findViewById(R.id.orderDate);
        userFullNameText = findViewById(R.id.userFullName);
        userAddressText = findViewById(R.id.userAddress);
        userPhoneText = findViewById(R.id.userPhone);
        userEmailText = findViewById(R.id.userEmail);
        orderStatusImage = findViewById(R.id.orderStatus);
        orderStatusText = findViewById(R.id.orderStatusText);

        // Get data from the intent
        int orderId = getIntent().getIntExtra("orderId", -1);
        double totalAmount = getIntent().getDoubleExtra("orderTotalAmount", 0.0);
        String orderDate = getIntent().getStringExtra("orderDate");
        String orderStatus = getIntent().getStringExtra("orderStatus");

        // User info (you can replace these with actual data if available)
        String userFullName = getIntent().getStringExtra("userFullName");
        String userAddress = getIntent().getStringExtra("userAddress");
        String userPhone = getIntent().getStringExtra("userPhone");
        String userEmail = getIntent().getStringExtra("userEmail");

        // Set the data to the views
        orderIdText.setText("Order ID: #" + orderId);
        totalAmountText.setText("Total Amount: $" + totalAmount);
        orderDateText.setText("Order Date: " + orderDate);

        // Set user information
        userFullNameText.setText("Full Name: " + (userFullName != null ? userFullName : "N/A"));
        userAddressText.setText("Address: " + (userAddress != null ? userAddress : "N/A"));
        userPhoneText.setText("Phone: " + (userPhone != null ? userPhone : "N/A"));
        userEmailText.setText("Email: " + (userEmail != null ? userEmail : "N/A"));

        // Set order status
        orderStatusText.setText("Status: " + orderStatus);
        if ("success".equals(orderStatus)) {
            orderStatusImage.setImageResource(R.drawable.success);
        } else {
            orderStatusImage.setImageResource(R.drawable.failure);
        }
    }
}
