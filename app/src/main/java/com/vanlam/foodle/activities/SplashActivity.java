package com.vanlam.foodle.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.vanlam.foodle.R;
import com.vanlam.foodle.adapters.Preferences;

public class SplashActivity extends AppCompatActivity {
    private boolean isLogged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        isLogged = Preferences.getDataLogin(SplashActivity.this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Kiểm tra trạng thái đăng nhập tài khoản trước đó
                Intent intent;
                if (isLogged) {
                    intent = new Intent(SplashActivity.this, MainActivity.class);
                }
                else {
                    intent = new Intent(SplashActivity.this, SlideActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }, 1800);
    }
}