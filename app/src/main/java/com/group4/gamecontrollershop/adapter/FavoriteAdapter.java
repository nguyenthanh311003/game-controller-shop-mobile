package com.group4.gamecontrollershop.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.group4.gamecontrollershop.R;
import com.group4.gamecontrollershop.model.Favorite;
import com.group4.gamecontrollershop.model.Order;

import java.text.SimpleDateFormat;
import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {

    private List<Favorite> favoriteList;

    public FavoriteAdapter(List<Favorite> favoriteList) {
        this.favoriteList = favoriteList;
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_item, parent, false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        Favorite favorite = favoriteList.get(position);
        holder.bind(favorite);
    }

    @Override
    public int getItemCount() {
        return favoriteList.size();
    }

    class FavoriteViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImage, heartFill;
        private TextView productName, productOldPrice, productPrice;

        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
            heartFill = itemView.findViewById(R.id.ivHeart);
            productName = itemView.findViewById(R.id.productName);
            productOldPrice = itemView.findViewById(R.id.productOldPrice);
            productPrice = itemView.findViewById(R.id.productPrice);

            heartFill.setOnClickListener(v -> {
                int position = getAdapterPosition();
                favoriteList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, favoriteList.size());
            });
        }

        @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
        public void bind(Favorite favorite) {
            productName.setText(favorite.getProduct().getName());
            productOldPrice.setText("$" + favorite.getProduct().getOldPrice());
            productPrice.setText("$" + favorite.getProduct().getNewPrice());
            Glide.with(itemView.getContext())
                    .load(favorite.getProduct().getImgUrl())
                    .into(productImage);

        }
    }
}
