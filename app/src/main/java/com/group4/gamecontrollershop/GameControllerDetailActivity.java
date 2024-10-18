package com.group4.gamecontrollershop;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.group4.gamecontrollershop.database_helper.DatabaseHelper;
import com.group4.gamecontrollershop.fragments.FragmentHome;
import com.group4.gamecontrollershop.model.Product;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class GameControllerDetailActivity extends AppCompatActivity {
    private TextView productName, productDescription, productPrice;
    private ImageView productImage, productImageFirst, productImageSecond, productImageThird;
    private DatabaseHelper myDB;
    private ImageButton btnDecreaseQuantity;
    private ImageButton btnIncreaseQuantity;
    private Button btnAddToCart;
    private ImageView btnBack;
    private TextView tvQuantity;
    private double currentProductPrice;
    private static final int PAYPAL_REQUEST_CODE = 123;

    // PayPal Configuration
    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId("AXIwvWt-qyVQPaXC-fwEu9iRJqXzgDgRiEvCBN1qs9ktvpGRVHvxtYW5xj3SEmcJMno1xi3sJc15dOZf");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.fragment_product_detail);
        Intent intents = new Intent(this, PayPalService.class);
        intents.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intents);

//        // Sự kiện click cho nút "Add to Cart"
        findViewById(R.id.btnAddToCart).setOnClickListener(v -> processPayment());

        productName = findViewById(R.id.tvProductName);
        productDescription = findViewById(R.id.tvProductDescription);
        productPrice = findViewById(R.id.tvProductPrice);
        productImage = findViewById(R.id.ivProductImage);
        productImageFirst = findViewById(R.id.ivThumbnail1);
        productImageSecond = findViewById(R.id.ivThumbnail2);
        productImageThird = findViewById(R.id.ivThumbnail3);
        btnDecreaseQuantity = findViewById(R.id.btnDecreaseQuantity);
        btnIncreaseQuantity = findViewById(R.id.btnIncreaseQuantity);
        btnAddToCart = findViewById(R.id.btnAddToCart);
        btnBack = findViewById(R.id.ivBack);
        tvQuantity = findViewById(R.id.tvQuantity);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });

        myDB = new DatabaseHelper(this);

        int productId = getIntent().getIntExtra("product_id", -1);

        if (productId != -1) {
            Product product = myDB.getProduct(productId);
            if (product != null) {
                productName.setText(product.getName());
                productDescription.setText(product.getDescription());
                productPrice.setText(String.valueOf(product.getNewPrice()));
                currentProductPrice = product.getNewPrice();
                Glide.with(this)
                        .load(product.getImgUrl())
                        .into(productImage);
                Glide.with(this)
                        .load(product.getDetailImgUrlFirst())
                        .into(productImageFirst);
                Glide.with(this)
                        .load(product.getDetailImgUrlSecond())
                        .into(productImageSecond);
                Glide.with(this)
                        .load(product.getDetailImgUrlThird())
                        .into(productImageThird);
            }
        }

        btnDecreaseQuantity.setOnClickListener(v -> {
            int quantity = Integer.parseInt(tvQuantity.getText().toString());

            if (quantity > 1) {
                quantity--;
                double totalPrice = quantity * currentProductPrice;
                DecimalFormat decimalFormat = new DecimalFormat("#.00");
                String formattedPrice = decimalFormat.format(totalPrice);
                productPrice.setText(formattedPrice);
                tvQuantity.setText(String.valueOf(quantity));
            }
        });

        btnIncreaseQuantity.setOnClickListener(v -> {
            int quantity = Integer.parseInt(tvQuantity.getText().toString());

            quantity++;
            double totalPrice = quantity * currentProductPrice;
            DecimalFormat decimalFormat = new DecimalFormat("#.00");
            String formattedPrice = decimalFormat.format(totalPrice);
            productPrice.setText(formattedPrice);
            tvQuantity.setText(String.valueOf(quantity));
        });

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(this, FragmentHome.class);
            startActivity(intent);
            finish();
        });

        btnAddToCart.setOnClickListener(v -> {
            Intent intent = new Intent(this, CartActivity.class);
            startActivity(intent);
        });
    }

    private void processPayment() {
        double amount = Double.parseDouble(productPrice.getText().toString().replace("$", ""));
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
