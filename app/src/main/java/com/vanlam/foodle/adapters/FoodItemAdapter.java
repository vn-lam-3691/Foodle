package com.vanlam.foodle.adapters;

import static com.vanlam.foodle.fragments.HomeFragment.progressLoad;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.vanlam.foodle.R;
import com.vanlam.foodle.activities.FoodDetailActivity;
import com.vanlam.foodle.database.DatabaseHandler;
import com.vanlam.foodle.listeners.FoodItemListener;
import com.vanlam.foodle.models.Cart;
import com.vanlam.foodle.models.Food;

import java.text.DecimalFormat;
import java.util.List;

public class FoodItemAdapter extends FirebaseRecyclerAdapter<Food, FoodItemAdapter.FoodItemViewHolder> {

    public FoodItemAdapter(@NonNull FirebaseRecyclerOptions<Food> options) {
        super(options);
    }

    @NonNull
    @Override
    public FoodItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_container_food_item, parent, false);
        return new FoodItemViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull FoodItemViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull Food model) {
        String idFood = getRef(position).getKey();
        holder.getFoodName().setText(model.getName());
        DecimalFormat df = new DecimalFormat("#,###.##");
        holder.getFoodPrice().setText(df.format(model.getPrice()) + "đ");

        Glide.with(holder.itemView).load(model.getImageUrl()).into(holder.getFoodImage());

        holder.getLayoutFoodItem().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), FoodDetailActivity.class);
                intent.putExtra("idFood", idFood);
                view.getContext().startActivity(intent);
            }
        });

        holder.getBtnChoose().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cart cart = new Cart(idFood, model.getName(), model.getImageUrl(), 1, "S", model.getPrice());
                DatabaseHandler db = new DatabaseHandler(view.getContext());
                db.openDatabase(Preferences.getDataUser(view.getContext()).getPhoneNumber());
                db.addToCart(cart);

                Toast.makeText(view.getContext(), "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static class FoodItemViewHolder extends RecyclerView.ViewHolder {
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

        public MaterialButton getBtnChoose() {
            return btnChoose;
        }

        public void setBtnChoose(MaterialButton btnChoose) {
            this.btnChoose = btnChoose;
        }
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();
        if (progressLoad != null) {
            progressLoad.setVisibility(View.GONE);
        }
    }
}
