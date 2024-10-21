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
import com.group4.gamecontrollershop.fragments.FragmentHome;
import com.group4.gamecontrollershop.model.Product;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductCartAdapter productCartAdapter;
    private List<Product> productList;
    private TextView tvTotalPrice;
    private ImageView ivBack;
    private static final int PAYPAL_REQUEST_CODE = 123;
    private DatabaseHelper myDB;

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

        String xboxOneSWhiteUrl = "https://product.hstatic.net/200000722513/product/tay-cam-choi-gam-dareu-h105-01-trang-01_d7721a3d7ae04a1ea551f34499ddec23_grande.png";

        Date releaseDate = null;
        try {
            releaseDate = dateFormat.parse("2023-08-15");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Retrieve product ID from Game Controller Detail
        Intent cartIntent = getIntent();
        int productId = cartIntent.getIntExtra("productId", -1);
        int quantity = cartIntent.getIntExtra("quantity", 0);
        Product addedProduct = myDB.getProduct(productId);
        addedProduct.setQuantity(quantity);

        productList = new ArrayList<>();

        productList.add(addedProduct);

        Product xboxOneSWhite = new Product(
                "Xbox One S White Controller",
                "The Xbox One S Controller is an ergonomic game pad designed for Xbox One consoles and Windows PCs. It features Bluetooth connectivity, textured grips, and responsive buttons for a comfortable and precise gaming experience. Customizable settings through the Xbox Accessories app enhance personalization for gamers.",
                xboxOneSWhiteUrl,
                xboxOneSWhiteUrl,
                xboxOneSWhiteUrl,
                xboxOneSWhiteUrl,
                59.99,
                39.99,
                10,
                "Xbox",
                releaseDate,
                "ACTIVE"
        );

        productList.add(xboxOneSWhite);
        productList.add(xboxOneSWhite);
        productList.add(xboxOneSWhite);
        productList.add(xboxOneSWhite);
        productList.add(xboxOneSWhite);

        productCartAdapter = new ProductCartAdapter(productList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(productCartAdapter);

        ivBack.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        productCartAdapter.setOnItemClickListener(new ProductCartAdapter.OnItemClickListener() {
            @Override
            public void onQuantityChanged() {
                updateTotalPrice();
            }

            @Override
            public void onItemRemoved(int position) {
                updateTotalPrice();
            }
        });
        updateTotalPrice();
    }

    private void updateTotalPrice() {
        double totalPrice = 0.0;
        for (Product product : productList) {
            totalPrice += product.getNewPrice() * product.getQuantity();
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
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("Payment", "User cancelled the payment.");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i("Payment", "Invalid payment or PayPalConfiguration submitted.");
            }
        }
    }

    @Override
    public void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }
}
