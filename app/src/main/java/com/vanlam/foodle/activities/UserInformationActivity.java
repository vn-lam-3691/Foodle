package com.vanlam.foodle.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.vanlam.foodle.R;
import com.vanlam.foodle.adapters.Preferences;
import com.vanlam.foodle.adapters.UserInfoAdapter;
import com.vanlam.foodle.database.DatabaseHandler;
import com.vanlam.foodle.listeners.UserInfoListener;
import com.vanlam.foodle.models.User;

import java.util.ArrayList;
import java.util.List;


    public class UserInformationActivity extends AppCompatActivity implements UserInfoListener {
        private ImageView imageBack, imageCreate;
        private List<User> listInfoUser;
        private RecyclerView rcvInformationList;
        private UserInfoAdapter adapter;
        private DatabaseHandler database;
        private AlertDialog dialogCreate;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_user_information);

            imageBack = findViewById(R.id.image_back);
            imageCreate = findViewById(R.id.image_create_new);
            rcvInformationList = (RecyclerView) findViewById(R.id.recyclerView_info_list);


            database = new DatabaseHandler(this);


            listInfoUser = new ArrayList<>();
            loadUserInfomation();

            imageBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.putExtra("isChanged", false);
                    // Gửi về lại intent chứa các thông tin User mới chọn được thay đổi
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });

            imageCreate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showCreateInfoDialog();
                }
            });
        }

        private void showCreateInfoDialog() {
            if (dialogCreate == null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UserInformationActivity.this);
                View itemView = LayoutInflater.from(this).inflate(R.layout.layout_create_address_info, (ViewGroup) findViewById(R.id.layout_create_address_container));
                builder.setView(itemView);
                dialogCreate = builder.create();

                if (dialogCreate.getWindow() != null) {
                    dialogCreate.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                }

                itemView.findViewById(R.id.tv_create_new).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        EditText inputName, inputPhone, inputAddress;
                        inputName = itemView.findViewById(R.id.input_user_info_name);
                        inputPhone = itemView.findViewById(R.id.input_user_info_phone);
                        inputAddress = itemView.findViewById(R.id.input_user_info_address);
                        String userInfoName = inputName.getText().toString();
                        String userInfoPhone = inputPhone.getText().toString();
                        String userInfoAddress = inputAddress.getText().toString();

                        User user = new User(0, userInfoName, userInfoPhone, userInfoAddress, false);
                        listInfoUser.add(user);
                        database.openDatabase(Preferences.getDataUser(UserInformationActivity.this).getPhoneNumber());
                        database.createNewInfoAddress(user);
                        loadUserInfomation();
                        adapter.notifyDataSetChanged();

                        dialogCreate.dismiss();
                    }
                });

                itemView.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogCreate.dismiss();
                    }
                });
            }

            dialogCreate.show();
        }

        private void loadUserInfomation() {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(UserInformationActivity.this, LinearLayoutManager.VERTICAL, false);
            rcvInformationList.setLayoutManager(linearLayoutManager);
            database.openDatabase(Preferences.getDataUser(this).getPhoneNumber());

            listInfoUser = database.getUserInfomations();
            adapter = new UserInfoAdapter(listInfoUser, this);
            rcvInformationList.setAdapter(adapter);
        }

        @Override
        public void onClick(User userInfo) {
            Intent intent = new Intent();
            intent.putExtra("dataUser", userInfo);
            setResult(RESULT_OK, intent);
            finish();
        }
    }