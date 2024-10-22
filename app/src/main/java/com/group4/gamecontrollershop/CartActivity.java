package com.group4.gamecontrollershop;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.group4.gamecontrollershop.adapter.ProductCartAdapter;
import com.group4.gamecontrollershop.database_helper.DatabaseHelper;
import com.group4.gamecontrollershop.model.CartItem;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductCartAdapter productCartAdapter;
    private List<CartItem> cartItemList = new ArrayList<>();
    private TextView tvTotalPrice;
    private ImageView ivBack;
    private static final int PAYPAL_REQUEST_CODE = 123;
    private DatabaseHelper myDB;
    private int userId;
    private Double totalAmount;

    // PayPal Configuration
    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_NO_NETWORK)
            .clientId("AXIwvWt-qyVQPaXC-fwEu9iRJqXzgDgRiEvCBN1qs9ktvpGRVHvxtYW5xj3SEmcJMno1xi3sJc15dOZf");

    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_cart);

        myDB = new DatabaseHelper(this);

        // Add PayPal intent
        Intent intents = new Intent(this, PayPalService.class);
        intents.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intents);

        // Sự kiện click cho nút "Add to Cart"
        findViewById(R.id.btnAddToCart).setOnClickListener(v -> processPayment());
        
        recyclerView = findViewById(R.id.recycleView);
        tvTotalPrice = findViewById(R.id.tvProductPrice);
        ivBack = findViewById(R.id.ivBack);

        int userId = 1; // TODO: Get user ID from current user
        cartItemList = myDB.getCartItems(userId);

        productCartAdapter = new ProductCartAdapter(cartItemList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(productCartAdapter);

        ivBack.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        productCartAdapter.setOnItemClickListener(new ProductCartAdapter.OnItemClickListener() {
            @Override
            public void onQuantityChanged(int position) {
                updateTotalPrice();
                CartItem cartItem = cartItemList.get(position);
                myDB.updateCartItem(cartItem.getId(), cartItem.getQuantity());
            }

            @Override
            public void onItemRemoved(int position) {
                updateTotalPrice();
                CartItem cartItem = cartItemList.get(position);
                myDB.deleteCartItem(userId, cartItem.getProductId());
            }
        });
        updateTotalPrice();
    }

    private void updateTotalPrice() {
        double totalPrice = 0.0;
        for (CartItem cartItem: cartItemList) {
            totalPrice += cartItem.getProduct().getNewPrice() * cartItem.getQuantity();
        }
        tvTotalPrice.setText("$" + String.format("%.2f", totalPrice));
    }

    private void processPayment() {
        double amount = Double.parseDouble(tvTotalPrice.getText().toString().replace("$", ""));
        PayPalPayment payment = new PayPalPayment(new BigDecimal(String.valueOf(amount)), "USD",
                "Game Controller", PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PAYPAL_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirm != null) {
                    try {
                        String paymentDetails = confirm.toJSONObject().toString(4);
                        // Xử lý khi thanh toán thành công
                        Log.i("PaymentDetails", paymentDetails);

                        // Parse payment details
                        JSONObject jsonDetails = new JSONObject(paymentDetails);
                        String paymentState = jsonDetails.getJSONObject("response").getString("state");

                        // Store payment details to the database
                        userId = 1; // TODO: Get user ID from current user
                        String price = tvTotalPrice.getText().toString().replace("$", "");
                        totalAmount = Double.parseDouble(price);
                        myDB.insertOrder(userId, totalAmount, new Date(), "success");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("Payment", "User cancelled the payment.");
                myDB.insertOrder(userId, totalAmount, new Date(), "failure");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i("Payment", "Invalid payment or PayPalConfiguration submitted.");
                myDB.insertOrder(userId, totalAmount, new Date(), "failure");
            }
        }
    }

    @Override
    public void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }
}
