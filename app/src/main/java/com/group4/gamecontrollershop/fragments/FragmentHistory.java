package com.group4.gamecontrollershop.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.group4.gamecontrollershop.R;
import com.group4.gamecontrollershop.adapter.HistoryAdapter;
import com.group4.gamecontrollershop.database_helper.DatabaseHelper;
import com.group4.gamecontrollershop.model.Order;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FragmentHistory extends Fragment {

    private RecyclerView recyclerView;
    private HistoryAdapter historyAdapter;
    private List<Order> orderList;
    private DatabaseHelper myDB;

    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        recyclerView = view.findViewById(R.id.recycleView);
        myDB = new DatabaseHelper(getContext());

        Date orderDate = null;
        try {
            orderDate = dateFormat.parse("2023-08-15");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int userId = 1; // TODO: Get user ID from current user
        orderList = myDB.getAllOrders(userId);

        if (orderList.isEmpty()) {
            final String SUCCESS_STATUS = "success";
            final String FAILURE_STATUS = "failure";

            myDB.insertOrder(userId, 1500000d, orderDate, SUCCESS_STATUS);
            myDB.insertOrder(userId, 1500000d, orderDate, SUCCESS_STATUS);
            myDB.insertOrder(userId, 1500000d, orderDate, SUCCESS_STATUS);
            myDB.insertOrder(userId, 1500000d, orderDate, FAILURE_STATUS);
            myDB.insertOrder(userId, 1500000d, orderDate, SUCCESS_STATUS);

            orderList = myDB.getAllOrders(userId);
        }

        historyAdapter = new HistoryAdapter(orderList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(historyAdapter);

        return view;
    }
}
