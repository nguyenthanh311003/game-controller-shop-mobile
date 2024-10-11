package com.group4.gamecontrollershop.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.group4.gamecontrollershop.R;
import com.group4.gamecontrollershop.model.Product;

import java.util.List;

public class ProductCartAdapter extends RecyclerView.Adapter<ProductCartAdapter.ProductViewHolder> {

    private List<Product> productList;
    private OnItemClickListener listener;

    public ProductCartAdapter(List<Product> productList) {
        this.productList = productList;
    }

    public interface OnItemClickListener {
        void onQuantityChanged();
        void onItemRemoved(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.bind(product);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImage;
        private TextView productName, productPrice, productQuantity;
        private Button btnPlus, btnMinus, btnRemove;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            productQuantity = itemView.findViewById(R.id.productQuantity);
            productImage = itemView.findViewById(R.id.productImage);
            btnPlus = itemView.findViewById(R.id.btnPlus);
            btnMinus = itemView.findViewById(R.id.btnMinus);
            btnRemove = itemView.findViewById(R.id.btnRemove);

            btnPlus.setOnClickListener(v -> {
                int position = getAdapterPosition();
                Product product = productList.get(position);
                product.setQuantity(product.getQuantity() + 1);
                productQuantity.setText(String.valueOf(product.getQuantity()));
                if (listener != null) {
                    listener.onQuantityChanged();
                }
            });

            btnMinus.setOnClickListener(v -> {
                int position = getAdapterPosition();
                Product product = productList.get(position);
                if (product.getQuantity() > 1) {
                    product.setQuantity(product.getQuantity() - 1);
                    productQuantity.setText(String.valueOf(product.getQuantity()));
                    if (listener != null) {
                        listener.onQuantityChanged();
                    }
                }
            });

            btnRemove.setOnClickListener(v -> {
                int position = getAdapterPosition();
                productList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, productList.size());
                if (listener != null) {
                    listener.onItemRemoved(position);
                }
            });
        }

        @SuppressLint({"SetTextI18n", "CheckResult"})
        public void bind(Product product) {
            productName.setText(product.getName());
            productPrice.setText("$" + product.getNewPrice());
            productQuantity.setText(String.valueOf(product.getQuantity()));
            Glide.with(itemView.getContext())
                    .load(product.getImgUrl())
                    .into(productImage);

        }
    }
}
