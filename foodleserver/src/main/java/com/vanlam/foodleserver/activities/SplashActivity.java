package com.vanlam.foodleserver.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.vanlam.foodleserver.R;
import com.vanlam.foodleserver.utils.Preferences;

public class SplashActivity extends AppCompatActivity {
    private boolean isLogged = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
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
                    intent = new Intent(SplashActivity.this, SignInActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }, 1800);
    }
}