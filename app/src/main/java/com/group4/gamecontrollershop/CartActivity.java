package com.group4.gamecontrollershop;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.group4.gamecontrollershop.adapter.ProductCartAdapter;
import com.group4.gamecontrollershop.model.Product;

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

    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_cart);
        
        recyclerView = findViewById(R.id.recycleView);
        tvTotalPrice = findViewById(R.id.tvProductPrice);

        String xboxOneSWhiteUrl = "https://product.hstatic.net/200000722513/product/tay-cam-choi-gam-dareu-h105-01-trang-01_d7721a3d7ae04a1ea551f34499ddec23_grande.png";

        Date releaseDate = null;
        try {
            releaseDate = dateFormat.parse("2023-08-15");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        productList = new ArrayList<>();

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
}
