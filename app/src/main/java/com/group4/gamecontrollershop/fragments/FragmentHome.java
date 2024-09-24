package com.group4.gamecontrollershop.fragments;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    private DatabaseHelper myDB;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        productRV = view.findViewById(R.id.productRV);
        productRV.setLayoutManager(new GridLayoutManager(getContext(), 2));
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date releaseDate = null;
        try {
            releaseDate = dateFormat.parse("2023-08-15");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Uri xboxOneSWhiteUri = Uri.parse("android.resource://" + getContext().getPackageName() + "/" + R.drawable.xbox1s_white);
        String xboxOneSWhiteUrl = xboxOneSWhiteUri.toString();

        Uri xboxOneSBlackUri = Uri.parse("android.resource://" + getContext().getPackageName() + "/" + R.drawable.xbox1s_black);
        String xboxOneSBlackUrl = xboxOneSBlackUri.toString();

        Uri xboxOneXWhiteUri = Uri.parse("android.resource://" + getContext().getPackageName() + "/" + R.drawable.xbox1x_white);
        String xboxOneXWhiteUrl = xboxOneXWhiteUri.toString();

        Uri xboxOneXBlackUri = Uri.parse("android.resource://" + getContext().getPackageName() + "/" + R.drawable.xbox1x_black);
        String xboxOneXBackUrl = xboxOneXBlackUri.toString();

        Uri xboxOneXGreyCamoUri = Uri.parse("android.resource://" + getContext().getPackageName() + "/" + R.drawable.xbox1x_grey_camo);
        String xboxOneXGreyCamoUrl = xboxOneXGreyCamoUri.toString();

        Uri xboxOneXBlueCamoUri = Uri.parse("android.resource://" + getContext().getPackageName() + "/" + R.drawable.xbox1x_blue_camo);
        String xboxOneXBlueCamoUrl = xboxOneXBlueCamoUri.toString();

        Product xboxOneSWhite = new Product(
                "Xbox One S White Controller",
                "The Xbox One S Controller is an ergonomic game pad designed for Xbox One consoles and Windows PCs. It features Bluetooth connectivity, textured grips, and responsive buttons for a comfortable and precise gaming experience. Customizable settings through the Xbox Accessories app enhance personalization for gamers.",
                xboxOneSWhiteUrl,
                59.99,
                49.99,
                100,
                "Xbox",
                releaseDate,
                "ACTIVE"
        );

        Product xboxOneSBlack = new Product(
                "Xbox One S Black Controller",
                "The Xbox One S Controller is an ergonomic game pad designed for Xbox One consoles and Windows PCs. It features Bluetooth connectivity, textured grips, and responsive buttons for a comfortable and precise gaming experience. Customizable settings through the Xbox Accessories app enhance personalization for gamers.",
                xboxOneSBlackUrl,
                59.99,
                49.99,
                100,
                "Xbox",
                releaseDate,
                "ACTIVE"
        );

        Product xboxOneXWhite = new Product(
                "Xbox One X White Controller",
                "The Xbox One S Controller is an ergonomic game pad designed for Xbox One consoles and Windows PCs. It features Bluetooth connectivity, textured grips, and responsive buttons for a comfortable and precise gaming experience. Customizable settings through the Xbox Accessories app enhance personalization for gamers.",
                xboxOneXWhiteUrl,
                59.99,
                49.99,
                100,
                "Xbox",
                releaseDate,
                "ACTIVE"
        );

        Product xboxOneXBlack = new Product(
                "Xbox One X Black Controller",
                "The Xbox One S Controller is an ergonomic game pad designed for Xbox One consoles and Windows PCs. It features Bluetooth connectivity, textured grips, and responsive buttons for a comfortable and precise gaming experience. Customizable settings through the Xbox Accessories app enhance personalization for gamers.",
                xboxOneXBackUrl,
                59.99,
                49.99,
                100,
                "Xbox",
                releaseDate,
                "ACTIVE"
        );

        Product xboxOneXGreyCamo = new Product(
                "Xbox One X Grey Camo Controller",
                "The Xbox One S Controller is an ergonomic game pad designed for Xbox One consoles and Windows PCs. It features Bluetooth connectivity, textured grips, and responsive buttons for a comfortable and precise gaming experience. Customizable settings through the Xbox Accessories app enhance personalization for gamers.",
                xboxOneXGreyCamoUrl,
                59.99,
                49.99,
                100,
                "Xbox",
                releaseDate,
                "ACTIVE"
        );

        Product xboxOneXBlueCamo = new Product(
                "Xbox One X Blue Camo Controller",
                "The Xbox One S Controller is an ergonomic game pad designed for Xbox One consoles and Windows PCs. It features Bluetooth connectivity, textured grips, and responsive buttons for a comfortable and precise gaming experience. Customizable settings through the Xbox Accessories app enhance personalization for gamers.",
                xboxOneXBlueCamoUrl,
                59.99,
                49.99,
                100,
                "Xbox",
                releaseDate,
                "ACTIVE"
        );

        myDB = new DatabaseHelper(getContext());
//        myDB.insertProduct(xboxOneSWhite);
//        myDB.insertProduct(xboxOneSBlack);
//        myDB.insertProduct(xboxOneXWhite);
//        myDB.insertProduct(xboxOneXBlack);
//        myDB.insertProduct(xboxOneXBlueCamo);
//        myDB.insertProduct(xboxOneXGreyCamo);

//        myDB.deleteAllProducts();
        List<Product> productList = myDB.getActiveProducts();

        productAdapter = new ProductAdapter(getContext(), productList);
        productRV.setAdapter(productAdapter);

        return view;
    }
}
