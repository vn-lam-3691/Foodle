package com.vanlam.foodleserver.adapters;

import android.content.Context;
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
import com.makeramen.roundedimageview.RoundedImageView;
import com.vanlam.foodleserver.R;
import com.vanlam.foodleserver.models.Food;

import java.text.DecimalFormat;

public class FoodItemAdapter extends FirebaseRecyclerAdapter<Food, FoodItemAdapter.FoodItemViewHolder> {
    private Context mContext;

    public FoodItemAdapter(@NonNull FirebaseRecyclerOptions<Food> options, Context context) {
        super(options);
        this.mContext = context;
    }

    @NonNull
    @Override
    public FoodItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_container_food, parent, false);
        return new FoodItemViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull FoodItemViewHolder holder, int position, @NonNull Food model) {
        String foodId = getRef(position).getKey();

        holder.getTvFoodName().setText(model.getName());
        holder.getTvFoodDesc().setText(model.getDescription());
        DecimalFormat df = new DecimalFormat("#,###.##");
        holder.getTvFoodPrice().setText(df.format(model.getPrice()) + "đ");

        Glide.with(holder.itemView).load(model.getImageUrl()).into(holder.getImageFood());
    }

    public static class FoodItemViewHolder extends RecyclerView.ViewHolder {
        private TextView tvFoodName, tvFoodDesc, tvFoodPrice;
        private RoundedImageView imageFood;
        private ImageView imgEdit, imgDelete;

        public FoodItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFoodName = itemView.findViewById(R.id.food_name);
            tvFoodDesc = itemView.findViewById(R.id.food_description);
            tvFoodPrice = itemView.findViewById(R.id.food_price);
            imageFood = itemView.findViewById(R.id.food_image);
            imgEdit = itemView.findViewById(R.id.image_edit);
        }

        public TextView getTvFoodName() {
            return tvFoodName;
        }

        public void setTvFoodName(TextView tvFoodName) {
            this.tvFoodName = tvFoodName;
        }

        public TextView getTvFoodDesc() {
            return tvFoodDesc;
        }

        public void setTvFoodDesc(TextView tvFoodDesc) {
            this.tvFoodDesc = tvFoodDesc;
        }

        public TextView getTvFoodPrice() {
            return tvFoodPrice;
        }

        public void setTvFoodPrice(TextView tvFoodPrice) {
            this.tvFoodPrice = tvFoodPrice;
        }

        public RoundedImageView getImageFood() {
            return imageFood;
        }

        public void setImageFood(RoundedImageView imageFood) {
            this.imageFood = imageFood;
        }

        public ImageView getImgEdit() {
            return imgEdit;
        }

        public void setImgEdit(ImageView imgEdit) {
            this.imgEdit = imgEdit;
        }

        public ImageView getImgDelete() {
            return imgDelete;
        }

        public void setImgDelete(ImageView imgDelete) {
            this.imgDelete = imgDelete;
        }
    }
}
