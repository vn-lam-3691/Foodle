package com.vanlam.foodle.adapters;

import android.annotation.SuppressLint;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.vanlam.foodle.R;
import com.vanlam.foodle.models.Food;

import java.text.DecimalFormat;
import java.util.List;

public class FoodItemAdapter extends RecyclerView.Adapter<FoodItemAdapter.FoodItemViewHolder> {
    private List<Food> foodList;

    public FoodItemAdapter(List<Food> foodList) {
        this.foodList = foodList;
    }

    @NonNull
    @Override
    public FoodItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_container_food_item, parent, false);
        return new FoodItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodItemViewHolder holder, int position) {
        Food item = foodList.get(position);
        holder.getFoodImage().setImageResource(item.getImagePath());
        holder.getFoodName().setText(item.getName());
        DecimalFormat df = new DecimalFormat("#,###.##");
        holder.getFoodPrice().setText(df.format(item.getPrice()) + "đ");
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    static class FoodItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView foodImage, foodFavorite;
        private TextView foodName, foodPrice;
        private MaterialButton btnChoose;

        public FoodItemViewHolder(@NonNull View itemView) {
            super(itemView);
            foodImage = (ImageView) itemView.findViewById(R.id.image_food);
            foodName = (TextView) itemView.findViewById(R.id.name_food);
            foodPrice = (TextView) itemView.findViewById(R.id.price_food);
            foodFavorite = (ImageView) itemView.findViewById(R.id.image_favorite);
            btnChoose = (MaterialButton) itemView.findViewById(R.id.btn_choose_food);
        }

        public ImageView getFoodImage() {
            return foodImage;
        }

        public void setFoodImage(ImageView foodImage) {
            this.foodImage = foodImage;
        }

        public TextView getFoodName() {
            return foodName;
        }

        public void setFoodName(TextView foodName) {
            this.foodName = foodName;
        }

        public TextView getFoodPrice() {
            return foodPrice;
        }

        public void setFoodPrice(TextView foodPrice) {
            this.foodPrice = foodPrice;
        }
    }
}
