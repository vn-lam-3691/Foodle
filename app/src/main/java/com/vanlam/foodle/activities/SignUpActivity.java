package com.vanlam.foodle.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vanlam.foodle.R;
import com.vanlam.foodle.models.User;

public class SignUpActivity extends AppCompatActivity {
    private TextView txt_signIn;
    private EditText etPhoneNumber, etUsername, etPassword;
    private MaterialButton btnSignUp;
    private FirebaseDatabase database;
    private DatabaseReference tbUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mapping();
        database = FirebaseDatabase.getInstance();
        tbUser = database.getReference("Users");
        txt_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpAccount();
            }
        });
    }

    private void mapping() {
        txt_signIn = findViewById(R.id.txt_signInNow);
        etPhoneNumber = findViewById(R.id.input_phoneNumber);
        etUsername = findViewById(R.id.input_userName);
        etPassword = findViewById(R.id.input_password);
        btnSignUp = findViewById(R.id.btn_signUp);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void signUpAccount() {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        String phoneNumber = etPhoneNumber.getText().toString();

        // Validate input data
        if (username.equals("") && password.equals("") && phoneNumber.equals("")) {
            Toast.makeText(this, "Vui lòng nhập thông tin đăng ký", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (username.equals("")) {
            Toast.makeText(this, "Nhập tên của bạn", Toast.LENGTH_SHORT).show();
            return;
        }

        if (phoneNumber.equals("")) {
            Toast.makeText(this, "Nhập số điện thoại", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (phoneNumber.length() != 10) {
            TextView tvLength = findViewById(R.id.tv_phone_length_invalid);
            tvLength.setVisibility(View.VISIBLE);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    tvLength.setVisibility(View.GONE);
                }
            }, 3000);
            return;
        }

        if (password.equals("")) {
            Toast.makeText(this, "Nhập mật khẩu", Toast.LENGTH_SHORT).show();
            return;
        }

        tbUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Check tài khoản đã tồn tại trước đó chưa
                if (snapshot.child(phoneNumber).exists()) {
                    TextView tvInvalid = findViewById(R.id.tv_phone_invalid);
                    tvInvalid.setVisibility(View.VISIBLE);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            tvInvalid.setVisibility(View.GONE);
                        }
                    }, 3000);
                }
                else {
                    User user = new User(0,username, phoneNumber, null, password, false);
                    tbUser.child(phoneNumber).setValue(user);

                    Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}