package com.vanlam.foodle.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.vanlam.foodle.R;

public class CheckOutActivity extends AppCompatActivity {
    private ImageView imgBack;
    private TextView tvChangeLocation;
    private MaterialButton btnOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        imgBack = findViewById(R.id.image_back);
        tvChangeLocation = findViewById(R.id.tv_change_location);
        btnOrder = findViewById(R.id.btn_order);

        SpannableString content = new SpannableString("Thay đổi");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        tvChangeLocation.setText(content);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}