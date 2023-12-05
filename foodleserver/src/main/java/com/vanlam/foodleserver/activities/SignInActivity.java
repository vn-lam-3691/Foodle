package com.vanlam.foodleserver.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.vanlam.foodleserver.R;
import com.vanlam.foodleserver.models.User;
import com.vanlam.foodleserver.utils.Preferences;

public class SignInActivity extends AppCompatActivity {
    private MaterialButton btnSignIn;
    private EditText etPhoneNumber, etPassword;
    private CheckBox cbRemember;
    private FirebaseDatabase database;
    private DatabaseReference tbUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        btnSignIn = findViewById(R.id.btn_signIn);
        etPhoneNumber = findViewById(R.id.input_phoneNumber);
        etPassword = findViewById(R.id.input_password);
        cbRemember = findViewById(R.id.cb_rememberMe);

        database = FirebaseDatabase.getInstance();
        tbUser = database.getReference("Users");

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInAccount();
            }
        });
    }

    private void signInAccount() {
        // Validate input data
        if (etPhoneNumber.getText().toString().equals("") && etPassword.getText().toString().equals("")) {
            Toast.makeText(this, "Vui lòng nhập số điện thoại và mật khẩu", Toast.LENGTH_SHORT).show();
            return;
        }

        if (etPhoneNumber.getText().toString().equals("")) {
            Toast.makeText(this, "Nhập số điện thoại", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (etPhoneNumber.getText().toString().length() < 10 || etPhoneNumber.getText().toString().length() > 10) {
            TextView tvPhoneInvalid = findViewById(R.id.tv_phone_invalid);
            tvPhoneInvalid.setVisibility(View.VISIBLE);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    tvPhoneInvalid.setVisibility(View.GONE);
                }
            }, 3000);
            return;
        }

        if (etPassword.getText().toString().equals("")) {
            Toast.makeText(this, "Nhập mật khẩu", Toast.LENGTH_SHORT).show();
            return;
        }

        // Handle Sign In with Firebase DB
        tbUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    User user = snapshot.child(etPhoneNumber.getText().toString()).getValue(User.class);
                    if (user != null && user.getRole().equals("partner")) {
                        if (user.getPassword().equals(etPassword.getText().toString())) {
                            if (cbRemember.isChecked()) {
                                // Lưu lại trạng thái tùy chọn là Remember me
                                Preferences.setDataLogin(SignInActivity.this, true);
                            }
                            else {
                                // Không lưu lại trạng thái tùy chọn là Remember me
                                Preferences.setDataLogin(SignInActivity.this, false);
                            }

                            // Lưu lại thông tin tài khoản của User dưới dạng Json
                            Preferences.setDataUser(parseUserJson(user), SignInActivity.this);

                            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            TextView tvPassWrong = findViewById(R.id.tv_password_wrong);
                            tvPassWrong.setVisibility(View.VISIBLE);
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    tvPassWrong.setVisibility(View.GONE);
                                }
                            }, 3000);
                        }
                    }
                    else {
                        Toast.makeText(SignInActivity.this, "Tài khoản không tồn tại", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(SignInActivity.this, "Tài khoản không tồn tại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SignInActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String parseUserJson(User user) {
        Gson gson = new Gson();
        return gson.toJson(user);
    }
}