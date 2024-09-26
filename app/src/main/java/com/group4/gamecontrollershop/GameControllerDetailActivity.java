package com.group4.gamecontrollershop;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.group4.gamecontrollershop.database_helper.DatabaseHelper;
import com.group4.gamecontrollershop.fragments.FragmentHome;
import com.group4.gamecontrollershop.model.Product;

import java.text.DecimalFormat;

public class GameControllerDetailActivity extends AppCompatActivity {
    private TextView productName, productDescription, productPrice;
    private ImageView productImage, productImageFirst, productImageSecond, productImageThird;
    private DatabaseHelper myDB;
    private ImageButton btnDecreaseQuantity;
    private ImageButton btnIncreaseQuantity;
    private ImageView btnBack;
    private TextView tvQuantity;
    private double currentProductPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.fragment_product_detail);
        productName = findViewById(R.id.tvProductName);
        productDescription = findViewById(R.id.tvProductDescription);
        productPrice = findViewById(R.id.tvProductPrice);
        productImage = findViewById(R.id.ivProductImage);
        productImageFirst = findViewById(R.id.ivThumbnail1);
        productImageSecond = findViewById(R.id.ivThumbnail2);
        productImageThird = findViewById(R.id.ivThumbnail3);
        btnDecreaseQuantity = findViewById(R.id.btnDecreaseQuantity);
        btnIncreaseQuantity = findViewById(R.id.btnIncreaseQuantity);
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
                productImage.setImageURI(Uri.parse(product.getImgUrl()));
                productImageFirst.setImageURI(Uri.parse(product.getDetailImgUrlFirst()));
                productImageSecond.setImageURI(Uri.parse(product.getDetailImgUrlSecond()));
                productImageThird.setImageURI(Uri.parse(product.getDetailImgUrlThird()));
                currentProductPrice = product.getNewPrice();
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
    }
}
