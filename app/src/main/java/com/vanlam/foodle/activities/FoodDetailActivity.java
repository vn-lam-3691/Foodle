package com.vanlam.foodle.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.vanlam.foodle.R;

import java.text.DecimalFormat;

public class FoodDetailActivity extends AppCompatActivity {
    private DecimalFormat df = new DecimalFormat("#,###.##");
    private ImageView imgDecreaseQty, imgIncreaseQty;
    private TextView txtQuantity, txtTotalMoney;
    private MaterialButton btnAddToCart;
    private RadioGroup rdgSize;
    private int quantity = 1;
    private double totalMoney = 0, defaultPrice = 55000d;
    private String size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        txtQuantity = (TextView) findViewById(R.id.txt_quantity);
        txtTotalMoney = (TextView) findViewById(R.id.txt_totalMoney);
        imgDecreaseQty = (ImageView) findViewById(R.id.image_decrease);
        imgIncreaseQty = (ImageView) findViewById(R.id.image_increase);
        btnAddToCart = (MaterialButton) findViewById(R.id.btn_add_cart);
        rdgSize = (RadioGroup) findViewById(R.id.rg_size);
        ImageView imgBack = (ImageView) findViewById(R.id.image_back);
        totalMoney = defaultPrice;

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
                // Lấy ra size của sản phẩm
                int sizeCheckedId = rdgSize.getCheckedRadioButtonId();
                RadioButton radioSize = findViewById(sizeCheckedId);
                size = radioSize.getText().toString();

                Toast.makeText(FoodDetailActivity.this, "Order " + quantity + " size " + size + " total price " + totalMoney, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}