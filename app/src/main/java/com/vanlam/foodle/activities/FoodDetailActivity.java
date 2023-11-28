package com.vanlam.foodle.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.makeramen.roundedimageview.RoundedImageView;
import com.vanlam.foodle.R;
import com.vanlam.foodle.adapters.Preferences;
import com.vanlam.foodle.database.DatabaseHandler;
import com.vanlam.foodle.models.Cart;
import com.vanlam.foodle.models.Food;
import com.vanlam.foodle.models.User;

import java.text.DecimalFormat;

public class FoodDetailActivity extends AppCompatActivity {
    private DecimalFormat df = new DecimalFormat("#,###.##");
    private ImageView imgDecreaseQty, imgIncreaseQty, imgBack;
    private RoundedImageView imgFood;
    private TextView txtQuantity, txtTotalMoney, tvFoodName, tvFoodDesc;
    private MaterialButton btnAddToCart;
    private RadioGroup rdgSize;
    private int quantity = 1;
    private double totalMoney = 0, defaultPrice;
    private String size;
    private String idFood = "";
    private DatabaseReference reference;
    private Food currFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        mapping();
        reference = FirebaseDatabase.getInstance().getReference("Product");

        if (getIntent() != null) {
            idFood = getIntent().getStringExtra("idFood");
            if (!idFood.isEmpty()) {
                loadDetailFood(idFood);
            }
        }

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        imgDecreaseQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quantity > 1) {
                    quantity = quantity - 1;
                    txtQuantity.setText(String.valueOf(quantity));
                    totalMoney = defaultPrice * quantity;
                    txtTotalMoney.setText(df.format(totalMoney) + "đ");
                }
            }
        });

        imgIncreaseQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantity = quantity + 1;
                txtQuantity.setText(String.valueOf(quantity));
                totalMoney = defaultPrice * quantity;
                txtTotalMoney.setText(df.format(totalMoney) + "đ");
            }
        });

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFoodToCart();
            }
        });
    }

    private void mapping() {
        txtQuantity = (TextView) findViewById(R.id.txt_quantity);
        txtTotalMoney = (TextView) findViewById(R.id.txt_totalMoney);
        imgDecreaseQty = (ImageView) findViewById(R.id.image_decrease);
        imgIncreaseQty = (ImageView) findViewById(R.id.image_increase);
        btnAddToCart = (MaterialButton) findViewById(R.id.btn_add_cart);
        rdgSize = (RadioGroup) findViewById(R.id.rg_size);
        imgBack = (ImageView) findViewById(R.id.image_back);
        imgFood = (RoundedImageView) findViewById(R.id.image_food);
        tvFoodName = (TextView) findViewById(R.id.txt_foodName);
        tvFoodDesc = (TextView) findViewById(R.id.food_description);
    }

    private void loadDetailFood(String foodId) {
        reference.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    currFood = snapshot.getValue(Food.class);

                    // Set data for view
                    Glide.with(FoodDetailActivity.this).load(currFood.getImageUrl()).into(imgFood);
                    tvFoodName.setText(currFood.getName());
                    tvFoodDesc.setText(currFood.getDescription());
                    txtTotalMoney.setText(df.format(currFood.getPrice()) + "đ");

                    defaultPrice = Double.valueOf(currFood.getPrice());
                    totalMoney = Double.valueOf(currFood.getPrice());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }

    private void addFoodToCart() {
        DatabaseHandler db = new DatabaseHandler(this);
        User currentUser = Preferences.getDataUser(this);
        db.openDatabase(currentUser.getPhoneNumber());
        // Lấy ra size của sản phẩm
        int sizeCheckedId = rdgSize.getCheckedRadioButtonId();
        RadioButton radioSize = findViewById(sizeCheckedId);
        size = radioSize.getText().toString();

        Cart cart = new Cart(idFood, currFood.getName(), currFood.getImageUrl(), quantity, size, defaultPrice);
        db.addToCart(cart);
        Toast.makeText(this, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
    }
}