package com.vanlam.foodle.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.vanlam.foodle.R;

public class OrderSuccessActivity extends AppCompatActivity {
    private MaterialButton btnReturnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_success);

        btnReturnHome = findViewById(R.id.btn_return_home);
        btnReturnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OrderSuccessActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}