package com.vanlam.foodle.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.button.MaterialButton;
import com.vanlam.foodle.R;
import com.vanlam.foodle.models.Food;

import java.text.DecimalFormat;
import java.util.List;

public class FoodSuggestAdapter extends FirebaseRecyclerAdapter<Food, FoodSuggestAdapter.FoodSuggestViewHolder> {

    public FoodSuggestAdapter(@NonNull FirebaseRecyclerOptions<Food> options) {
        super(options);
    }

    @NonNull
    @Override
    public FoodSuggestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_container_food_suggest, parent, false);
        return new FoodSuggestViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull FoodSuggestViewHolder holder, int position, @NonNull Food model) {
        holder.getFoodName().setText(model.getName());
        DecimalFormat df = new DecimalFormat("#,###.##");
        holder.getFoodPrice().setText(df.format(model.getPrice()) + "đ");

        Glide.with(holder.itemView).load(model.getImageUrl()).into(holder.getFoodImage());
    }

    public static class FoodSuggestViewHolder extends RecyclerView.ViewHolder {
        private ImageView foodImage;
        private TextView foodName, foodPrice;
        private MaterialButton btnChoose;

        public FoodSuggestViewHolder(@NonNull View itemView) {
            super(itemView);
            foodImage = (ImageView) itemView.findViewById(R.id.image_food);
            foodName = (TextView) itemView.findViewById(R.id.name_food);
            foodPrice = (TextView) itemView.findViewById(R.id.price_food);
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

        public MaterialButton getBtnChoose() {
            return btnChoose;
        }

        public void setBtnChoose(MaterialButton btnChoose) {
            this.btnChoose = btnChoose;
        }
    }
}
