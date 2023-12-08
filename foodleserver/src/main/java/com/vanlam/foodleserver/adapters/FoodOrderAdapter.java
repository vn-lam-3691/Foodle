package com.vanlam.foodleserver.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.vanlam.foodleserver.R;
import com.vanlam.foodleserver.models.Cart;

import java.text.DecimalFormat;
import java.util.List;

public class FoodOrderAdapter extends RecyclerView.Adapter<FoodOrderAdapter.FoodOrderViewHolder> {
    private List<Cart> cartList;

    public FoodOrderAdapter(List<Cart> cartList) {
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public FoodOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food_order, parent, false);
        return new FoodOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodOrderViewHolder holder, int position) {
        Cart item = cartList.get(position);

        holder.getTvFoodName().setText(item.getFoodName());
        holder.getTvFoodSize().setText("Size:  " + item.getSize());
        holder.getTvFoodQuantity().setText(item.getQuantity() + " x ");

        DecimalFormat df = new DecimalFormat("#,###.##");
        holder.getTvFoodPrice().setText(df.format(item.getFoodPrice()) + "đ");

        Glide.with(holder.itemView).load(item.getImageUrlFood()).into(holder.getImageFood());
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    static class FoodOrderViewHolder extends RecyclerView.ViewHolder {
        private RoundedImageView imageFood;
        private TextView tvFoodName, tvFoodSize, tvFoodQuantity, tvFoodPrice;

        public FoodOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageFood = itemView.findViewById(R.id.image_food_item);
            tvFoodName = itemView.findViewById(R.id.tv_food_item_name);
            tvFoodSize = itemView.findViewById(R.id.tv_food_size);
            tvFoodQuantity = itemView.findViewById(R.id.tv_quantity);
            tvFoodPrice = itemView.findViewById(R.id.tv_food_price);
        }

        public RoundedImageView getImageFood() {
            return imageFood;
        }

        public void setImageFood(RoundedImageView imageFood) {
            this.imageFood = imageFood;
        }

        public TextView getTvFoodName() {
            return tvFoodName;
        }

        public void setTvFoodName(TextView tvFoodName) {
            this.tvFoodName = tvFoodName;
        }

        public TextView getTvFoodSize() {
            return tvFoodSize;
        }

        public void setTvFoodSize(TextView tvFoodSize) {
            this.tvFoodSize = tvFoodSize;
        }

        public TextView getTvFoodQuantity() {
            return tvFoodQuantity;
        }

        public void setTvFoodQuantity(TextView tvFoodQuantity) {
            this.tvFoodQuantity = tvFoodQuantity;
        }

        public TextView getTvFoodPrice() {
            return tvFoodPrice;
        }

        public void setTvFoodPrice(TextView tvFoodPrice) {
            this.tvFoodPrice = tvFoodPrice;
        }
    }
}
