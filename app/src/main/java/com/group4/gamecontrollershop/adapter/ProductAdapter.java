package com.group4.gamecontrollershop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group4.gamecontrollershop.R;
import com.group4.gamecontrollershop.model.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder>{
    private List<Product> productList;
    private Context context;

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_layout, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.productName.setText(product.getName());
        holder.productPrice.setText("$" + String.format("%.2f", product.getNewPrice()));
        holder.productOldPrice.setText("$" + String.format("%.2f", product.getOldPrice()));

        /*// Assuming the image is a URL. If it's a resource ID, you can use holder.productImage.setImageResource(product.getImage());
        Glide.with(context)
                .load(product.getImage())
                .into(holder.productImage);*/
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {

        public ImageView productImage;
        public TextView productName;
        public TextView productPrice;
        public TextView productOldPrice;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.product_price);
            productOldPrice = itemView.findViewById(R.id.product_old_price);
        }
    }
}
