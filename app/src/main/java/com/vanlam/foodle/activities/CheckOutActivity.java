package com.vanlam.foodle.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

public class CheckOutActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_CHANGE_USER_INFORMATION = 1;
    public static final int REQUEST_CODE_PICK_VOUCHER = 2;
    private ImageView imgBack;
    private TextView tvChangeLocation;
    private MaterialButton btnOrder;
    private LinearLayout layoutMoreVoucher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        imgBack = findViewById(R.id.image_back);
        tvChangeLocation = findViewById(R.id.tv_change_location);
        btnOrder = findViewById(R.id.btn_order);
        layoutMoreVoucher = findViewById(R.id.layout_more_voucher);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}