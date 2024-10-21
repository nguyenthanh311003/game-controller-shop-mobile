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
