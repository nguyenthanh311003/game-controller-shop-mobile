package com.group4.gamecontrollershop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group4.gamecontrollershop.R;
import com.group4.gamecontrollershop.model.Brand;

import java.util.List;

public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.BrandViewHolder>{
    private Context context;
    private List<Brand> brandList;

    public BrandAdapter(Context context, List<Brand> brandList) {
        this.context = context;
        this.brandList = brandList;
    }

    public void updateBrandList(List<Brand> newBrandList) {
        this.brandList = newBrandList;
    }

    @NonNull
    @Override
    public BrandViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_brand, parent, false);
        return new BrandViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BrandViewHolder holder, int position) {
        Brand brand = brandList.get(position);
        holder.brandName.setText(brand.getName());
    }

    @Override
    public int getItemCount() {
        return brandList.size();
    }

    public static class BrandViewHolder extends RecyclerView.ViewHolder {
        public TextView brandName;

        public BrandViewHolder(@NonNull View itemView) {
            super(itemView);
            brandName = itemView.findViewById(R.id.brandName);
        }
    }
}
