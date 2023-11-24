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
import android.graphics.Color;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vanlam.foodle.models.User;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vanlam.foodle.R;

public class SignInActivity extends AppCompatActivity {
    private MaterialButton btnSignIn;
    private TextView txtSignUp;
    private EditText etPhoneNumber, etPassword;
    private FirebaseDatabase database;
    private DatabaseReference tbUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        btnSignIn = findViewById(R.id.btn_signIn);
        txtSignUp = findViewById(R.id.txt_signUpNow);
        etPhoneNumber = findViewById(R.id.input_phoneNumber);
        etPassword = findViewById(R.id.input_password);

        database = FirebaseDatabase.getInstance();
        tbUser = database.getReference("Users");

        btnSignIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                signInAccount();
            }
        });

        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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
                    if (user != null) {
                        if (user.getPassword().equals(etPassword.getText().toString())) {
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

}