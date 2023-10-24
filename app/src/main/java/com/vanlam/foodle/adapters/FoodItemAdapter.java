package com.vanlam.foodle.adapters;

import android.annotation.SuppressLint;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.vanlam.foodle.R;
import com.vanlam.foodle.listeners.FoodItemListener;
import com.vanlam.foodle.models.Food;

import java.text.DecimalFormat;
import java.util.List;

public class FoodItemAdapter extends RecyclerView.Adapter<FoodItemAdapter.FoodItemViewHolder> {
    private List<Food> foodList;
    private FoodItemListener foodItemListener;

    public FoodItemAdapter(List<Food> foodList, FoodItemListener listener) {
        this.foodList = foodList;
        this.foodItemListener = listener;
    }

    @NonNull
    @Override
    public FoodItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_container_food_item, parent, false);
        return new FoodItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodItemViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Food item = foodList.get(position);
        holder.getFoodImage().setImageResource(item.getImagePath());
        holder.getFoodName().setText(item.getName());
        DecimalFormat df = new DecimalFormat("#,###.##");
        holder.getFoodPrice().setText(df.format(item.getPrice()) + "đ");

        holder.getLayoutFoodItem().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                foodItemListener.onClick(view, item, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    static class FoodItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView foodImage, foodFavorite;
        private TextView foodName, foodPrice;
        private MaterialButton btnChoose;
        private LinearLayout layoutFoodItem;

        public FoodItemViewHolder(@NonNull View itemView) {
            super(itemView);
            foodImage = (ImageView) itemView.findViewById(R.id.image_food);
            foodName = (TextView) itemView.findViewById(R.id.name_food);
            foodPrice = (TextView) itemView.findViewById(R.id.price_food);
            foodFavorite = (ImageView) itemView.findViewById(R.id.image_favorite);
            btnChoose = (MaterialButton) itemView.findViewById(R.id.btn_choose_food);
            layoutFoodItem = (LinearLayout) itemView.findViewById(R.id.layout_foodItem);
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

        public LinearLayout getLayoutFoodItem() {
            return layoutFoodItem;
        }

        public void setLayoutFoodItem(LinearLayout layoutFoodItem) {
            this.layoutFoodItem = layoutFoodItem;
        }
    }
}
