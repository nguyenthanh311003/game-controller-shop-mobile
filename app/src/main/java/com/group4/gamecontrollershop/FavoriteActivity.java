package com.group4.gamecontrollershop;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.group4.gamecontrollershop.adapter.FavoriteAdapter;
import com.group4.gamecontrollershop.adapter.ProductCartAdapter;
import com.group4.gamecontrollershop.database_helper.DatabaseHelper;
import com.group4.gamecontrollershop.model.Favorite;
import com.group4.gamecontrollershop.model.Product;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FavoriteAdapter favoriteAdapter;
    private List<Favorite> favoriteList;
    private ImageView ivBack, ivCart;
    private DatabaseHelper myDB;

    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        
        recyclerView = findViewById(R.id.recycleView);
        ivBack = findViewById(R.id.ivBack);
        ivCart = findViewById(R.id.ivCart);
        myDB = new DatabaseHelper(this);

        int userId = 1; // TODO: Get user ID from current user
        favoriteList = myDB.getFavoriteList(userId);

        if (favoriteList.isEmpty()) {
//            String xboxOneSWhiteUrl = "https://product.hstatic.net/200000722513/product/tay-cam-choi-gam-dareu-h105-01-trang-01_d7721a3d7ae04a1ea551f34499ddec23_grande.png";
//
//            Date releaseDate = null;
//            try {
//                releaseDate = dateFormat.parse("2023-08-15");
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//
//            Product xboxOneSWhite = new Product(
//                    "Xbox One S White Controller",
//                    "The Xbox One S Controller is an ergonomic game pad designed for Xbox One consoles and Windows PCs. It features Bluetooth connectivity, textured grips, and responsive buttons for a comfortable and precise gaming experience. Customizable settings through the Xbox Accessories app enhance personalization for gamers.",
//                    xboxOneSWhiteUrl,
//                    xboxOneSWhiteUrl,
//                    xboxOneSWhiteUrl,
//                    xboxOneSWhiteUrl,
//                    59.99,
//                    39.99,
//                    10,
//                    "Xbox",
//                    releaseDate,
//                    "ACTIVE"
//            );
//
//            Favorite favorite = new Favorite(1, 1, null, 1, xboxOneSWhite);
            List<Product> products = myDB.getActiveProducts();

            myDB.insertFavorite(userId, products.get(0).getId());
            myDB.insertFavorite(userId, products.get(1).getId());
            myDB.insertFavorite(userId, products.get(2).getId());
            myDB.insertFavorite(userId, products.get(3).getId());
            myDB.insertFavorite(userId, products.get(4).getId());

            favoriteList = myDB.getFavoriteList(userId);
        }


        favoriteAdapter = new FavoriteAdapter(favoriteList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(favoriteAdapter);

        favoriteAdapter.setOnItemClickListener(new FavoriteAdapter.OnItemClickListener() {
            @Override
            public void removeInDatabase(int position) {
                Favorite fav = favoriteList.get(position);
                myDB.removeFavorite(fav.getUserId(), fav.getProductId());
            }
        });

        ivBack.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        ivCart.setOnClickListener(view -> {
            Intent intent = new Intent(this, CartActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
