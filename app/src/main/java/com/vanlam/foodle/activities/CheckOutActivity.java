package com.vanlam.foodle.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.vanlam.foodle.R;
import com.vanlam.foodle.adapters.CheckoutItemAdapter;
import com.vanlam.foodle.models.Cart;

import java.util.ArrayList;
import java.util.List;

public class CheckOutActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_CHANGE_USER_INFORMATION = 1;
    public static final int REQUEST_CODE_PICK_VOUCHER = 2;
    private ImageView imgBack;
    private TextView tvChangeLocation;
    private MaterialButton btnOrder;

    private LinearLayout layoutMoreVoucher;
    private RecyclerView rcvCheckout;
    private CheckoutItemAdapter checkoutAdapter;
    private List<Cart> listItemCheckout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        mapping();

        listItemCheckout = new ArrayList<>();
        if (getIntent() != null) {
            listItemCheckout = getIntent().getParcelableArrayListExtra("listItemsCheckout");
            loadItemsCheckout();
        }

        SpannableString content = new SpannableString("Thay đổi");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        tvChangeLocation.setText(content);
        tvChangeLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CheckOutActivity.this, UserInformationActivity.class);
                startActivityForResult(intent, REQUEST_CODE_CHANGE_USER_INFORMATION);
            }
        });


        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CheckOutActivity.this, OrderSuccessActivity.class));
            }
        });

        layoutMoreVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CheckOutActivity.this, ManageVoucherActivity.class);
                startActivityForResult(intent, REQUEST_CODE_PICK_VOUCHER);
            }
        });
    }
    private void mapping() {
        imgBack = findViewById(R.id.image_back);
        tvChangeLocation = findViewById(R.id.tv_change_location);
        btnOrder = findViewById(R.id.btn_order);
        layoutMoreVoucher = findViewById(R.id.layout_more_voucher);
        rcvCheckout = (RecyclerView) findViewById(R.id.recyclerView_checkout);
    }

    private void loadItemsCheckout() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rcvCheckout.setLayoutManager(linearLayoutManager);

        checkoutAdapter = new CheckoutItemAdapter(listItemCheckout);
        rcvCheckout.setAdapter(checkoutAdapter);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}