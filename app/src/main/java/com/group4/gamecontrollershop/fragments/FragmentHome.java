package com.group4.gamecontrollershop.fragments;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.group4.gamecontrollershop.R;
import com.group4.gamecontrollershop.adapter.ProductAdapter;
import com.group4.gamecontrollershop.database_helper.DatabaseHelper;
import com.group4.gamecontrollershop.model.Product;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FragmentHome extends Fragment {
    private RecyclerView productRV;
    private ProductAdapter productAdapter;
    private ImageButton btnSort;
    private DatabaseHelper myDB;
    private String sortStatus = "ALL";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        productRV = view.findViewById(R.id.productRV);
        btnSort = view.findViewById(R.id.btnSort);
        productRV.setLayoutManager(new GridLayoutManager(getContext(), 2));

        btnSort.setOnClickListener(v -> showSortMenu(v));

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date releaseDate = null;
        try {
            releaseDate = dateFormat.parse("2023-08-15");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String xboxOneSWhiteUrl = "https://firebasestorage.googleapis.com/v0/b/game-controller-shop-50da9.appspot.com/o/xbox1s_white.jpg?alt=media&token=0a338c00-af26-4732-877e-90df25693137";
        String xboxOneSBlackUrl = "https://firebasestorage.googleapis.com/v0/b/game-controller-shop-50da9.appspot.com/o/xbox1s_black.jpg?alt=media&token=a4c1b9dc-ac3d-4e1f-be22-2fc249a25fda";
        String xboxOneXWhiteUrl = "https://firebasestorage.googleapis.com/v0/b/game-controller-shop-50da9.appspot.com/o/xbox1x_white.jpg?alt=media&token=6332adc9-9caf-447e-8c6d-84022fb7d077";
        String xboxOneXBackUrl = "https://firebasestorage.googleapis.com/v0/b/game-controller-shop-50da9.appspot.com/o/xbox1x_black.jpg?alt=media&token=256f043a-7eda-445a-9eb2-3c3cd28cd787";
        String xboxOneXGreyCamoUrl = "https://firebasestorage.googleapis.com/v0/b/game-controller-shop-50da9.appspot.com/o/xbox1x_grey_camo.jpg?alt=media&token=942f5c77-28fd-4bc9-a04b-779947176cfb";
        String xboxOneXBlueCamoUrl = "https://firebasestorage.googleapis.com/v0/b/game-controller-shop-50da9.appspot.com/o/xbox1x_blue_camo.jpg?alt=media&token=8f4bcb71-1f06-4b2f-877c-ca0015b2ab88";
        String xboxOneXRedCamoUrl = "https://firebasestorage.googleapis.com/v0/b/game-controller-shop-50da9.appspot.com/o/xbox1x_red_camo.jpg?alt=media&token=ab57a9c8-1621-4967-a3e0-e5e154ffbe15";
        Product xboxOneSWhite = new Product(
                "Xbox One S White Controller",
                "The Xbox One S Controller is an ergonomic game pad designed for Xbox One consoles and Windows PCs. It features Bluetooth connectivity, textured grips, and responsive buttons for a comfortable and precise gaming experience. Customizable settings through the Xbox Accessories app enhance personalization for gamers.",
                xboxOneSWhiteUrl,
                xboxOneSWhiteUrl,
                xboxOneSWhiteUrl,
                xboxOneSWhiteUrl,
                59.99,
                39.99,
                100,
                "Xbox",
                releaseDate,
                "ACTIVE"
        );

        Product xboxOneSBlack = new Product(
                "Xbox One S Black Controller",
                "The Xbox One S Controller is an ergonomic game pad designed for Xbox One consoles and Windows PCs. It features Bluetooth connectivity, textured grips, and responsive buttons for a comfortable and precise gaming experience. Customizable settings through the Xbox Accessories app enhance personalization for gamers.",
                xboxOneSBlackUrl,
                xboxOneSBlackUrl,
                xboxOneSBlackUrl,
                xboxOneSBlackUrl,
                59.99,
                29.99,
                100,
                "Xbox",
                releaseDate,
                "ACTIVE"
        );

        Product xboxOneXWhite = new Product(
                "Xbox One X White Controller",
                "The Xbox One S Controller is an ergonomic game pad designed for Xbox One consoles and Windows PCs. It features Bluetooth connectivity, textured grips, and responsive buttons for a comfortable and precise gaming experience. Customizable settings through the Xbox Accessories app enhance personalization for gamers.",
                xboxOneXWhiteUrl,
                xboxOneXWhiteUrl,
                xboxOneXWhiteUrl,
                xboxOneXWhiteUrl,
                59.99,
                19.99,
                100,
                "Xbox",
                releaseDate,
                "ACTIVE"
        );

        Product xboxOneXBlack = new Product(
                "Xbox One X Black Controller",
                "The Xbox One S Controller is an ergonomic game pad designed for Xbox One consoles and Windows PCs. It features Bluetooth connectivity, textured grips, and responsive buttons for a comfortable and precise gaming experience. Customizable settings through the Xbox Accessories app enhance personalization for gamers.",
                xboxOneXBackUrl,
                xboxOneXBackUrl,
                xboxOneXBackUrl,
                xboxOneXBackUrl,
                59.99,
                9.99,
                100,
                "Xbox",
                releaseDate,
                "ACTIVE"
        );

        Product xboxOneXGreyCamo = new Product(
                "Xbox One X Grey Camo Controller",
                "The Xbox One S Controller is an ergonomic game pad designed for Xbox One consoles and Windows PCs. It features Bluetooth connectivity, textured grips, and responsive buttons for a comfortable and precise gaming experience. Customizable settings through the Xbox Accessories app enhance personalization for gamers.",
                xboxOneXGreyCamoUrl,
                xboxOneXGreyCamoUrl,
                xboxOneXGreyCamoUrl,
                xboxOneXGreyCamoUrl,
                59.99,
                8.99,
                100,
                "Xbox",
                releaseDate,
                "ACTIVE"
        );

        Product xboxOneXBlueCamo = new Product(
                "Xbox One X Blue Camo Controller",
                "The Xbox One S Controller is an ergonomic game pad designed for Xbox One consoles and Windows PCs. It features Bluetooth connectivity, textured grips, and responsive buttons for a comfortable and precise gaming experience. Customizable settings through the Xbox Accessories app enhance personalization for gamers.",
                xboxOneXBlueCamoUrl,
                xboxOneXBlueCamoUrl,
                xboxOneXBlueCamoUrl,
                xboxOneXBlueCamoUrl,
                59.99,
                7.99,
                100,
                "Xbox",
                releaseDate,
                "ACTIVE"
        );

        Product xboxOneXRedCamo = new Product(
                "Xbox One X Red Camo Controller",
                "The Xbox One S Controller is an ergonomic game pad designed for Xbox One consoles and Windows PCs. It features Bluetooth connectivity, textured grips, and responsive buttons for a comfortable and precise gaming experience. Customizable settings through the Xbox Accessories app enhance personalization for gamers.",
                xboxOneXRedCamoUrl,
                xboxOneXRedCamoUrl,
                xboxOneXRedCamoUrl,
                xboxOneXRedCamoUrl,
                59.99,
                7.99,
                100,
                "Xbox",
                releaseDate,
                "ACTIVE"
        );

        myDB = new DatabaseHelper(getContext());
        myDB.insertProduct(xboxOneSBlack);
        myDB.insertProduct(xboxOneSWhite);
        myDB.insertProduct(xboxOneXWhite);
        myDB.insertProduct(xboxOneXBlack);
        myDB.insertProduct(xboxOneXBlueCamo);
        myDB.insertProduct(xboxOneXGreyCamo);
        myDB.insertProduct(xboxOneXRedCamo);

//        myDB.deleteAllProducts();
        loadProducts();

        return view;
    }

    private void showSortMenu(View v) {
        PopupMenu popup = new PopupMenu(getContext(), v);
        popup.getMenuInflater().inflate(R.menu.menu_sort, popup.getMenu());
        popup.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.sort_by_price_descending) {
                Toast.makeText(getContext(), "Highest to lowest", Toast.LENGTH_SHORT).show();
                sortStatus = "DESC";
            } else if (item.getItemId() == R.id.sort_by_price_ascending) {
                Toast.makeText(getContext(), "Lowest to highest", Toast.LENGTH_SHORT).show();
                sortStatus = "ASC";
            } else if (item.getItemId() == R.id.sort_by_all) {
                Toast.makeText(getContext(), "All", Toast.LENGTH_SHORT).show();
                sortStatus = "ALL";
            } else {
                return false;
            }
            loadProducts();
            return true;
        });
        popup.show();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void loadProducts() {
        List<Product> productList = myDB.getActiveProductsBySort(sortStatus);
        if (productAdapter == null) {
            productAdapter = new ProductAdapter(getContext(), productList);
            productRV.setAdapter(productAdapter);
        } else {
            productAdapter.updateProductList(productList);
            productAdapter.notifyDataSetChanged();
        }
    }
}
