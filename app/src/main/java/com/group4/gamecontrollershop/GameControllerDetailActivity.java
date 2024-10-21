package com.group4.gamecontrollershop;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.group4.gamecontrollershop.database_helper.DatabaseHelper;
import com.group4.gamecontrollershop.fragments.FragmentHome;
import com.group4.gamecontrollershop.model.Favorite;
import com.group4.gamecontrollershop.model.Product;

import java.text.DecimalFormat;
import java.util.List;

public class GameControllerDetailActivity extends AppCompatActivity {
    private TextView productName, productDescription, productPrice;
    private ImageView productImage, productImageFirst, productImageSecond, productImageThird, ivFavorite;
    private DatabaseHelper myDB;
    private ImageButton btnDecreaseQuantity;
    private ImageButton btnIncreaseQuantity;
    private Button btnAddToCart;
    private ImageView btnBack;
    private TextView tvQuantity;
    private double currentProductPrice;
    private boolean isAlreadyFavorite = false;

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
        btnAddToCart = findViewById(R.id.btnAddToCart);
        btnBack = findViewById(R.id.ivBack);
        tvQuantity = findViewById(R.id.tvQuantity);
        ivFavorite = findViewById(R.id.ivFavorite);

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
            intent.putExtra("productId", productId);
            int quantity = Integer.parseInt(tvQuantity.getText().toString());
            intent.putExtra("quantity", quantity);
            startActivity(intent);
            finish();
        });

        int userId = 1; // Get user ID from current user
        List<Favorite> favoriteList = myDB.getFavoriteList(userId);
        for (Favorite favoriteItem: favoriteList) {
            if (favoriteItem.getProduct().getId() == productId) {
                isAlreadyFavorite = true;
                break;
            }
        }

        if (isAlreadyFavorite) {
            ivFavorite.setImageResource(R.drawable.heart_fill);
        }

        ivFavorite.setOnClickListener(v -> {
            if (isAlreadyFavorite) {
                Favorite favoriteItem = favoriteList.stream()
                        .filter(item -> item.getProductId() == productId && item.getUserId() == userId)
                        .findFirst().orElse(null);

                if (favoriteItem != null) {
                    boolean isSuccess = myDB.removeFavorite(favoriteItem.getUserId(), favoriteItem.getProductId());
                    Toast.makeText(this, isSuccess ? "Remove from favorite successfully!" : "Cannot remove from favorite", Toast.LENGTH_SHORT).show();
                    ivFavorite.setImageResource(isSuccess ? R.drawable.heart_outline : R.drawable.heart_fill);
                }
            } else {
                myDB.insertFavorite(1, productId);
                ivFavorite.setImageResource(R.drawable.heart_fill);
            }
        });

    }
}
