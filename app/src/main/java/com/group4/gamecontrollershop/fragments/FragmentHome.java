package com.group4.gamecontrollershop.fragments;

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

        myDB = new DatabaseHelper(getContext());
        List<Product> productList = myDB.getActiveProducts();

        productAdapter = new ProductAdapter(getContext(), productList);
        productRV.setAdapter(productAdapter);

        return view;
    }
}
