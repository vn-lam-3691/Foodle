package com.vanlam.foodle.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.vanlam.foodle.R;
import com.vanlam.foodle.adapters.UserInfoAdapter;
import com.vanlam.foodle.models.User;

import java.util.ArrayList;
import java.util.List;

public class UserInformationActivity extends AppCompatActivity {

    private ImageView imageBack;
    private List<User> listInfoUser;
    private RecyclerView rcvInformationList;
    private UserInfoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);

        imageBack = findViewById(R.id.image_back);

        listInfoUser = new ArrayList<>();
        listInfoUser.add(new User(0, "Trương Văn Lâm", "0384586xxx", "Hoàng Diệu, Bình Hiên, Hải Châu, Đà Nẵng", true));
        listInfoUser.add(new User(0, "Trần Công Quang Phú", "0384586xxx", "Pham Nhu Tang, Thanh Khe, Da Nang", false));
        listInfoUser.add(new User(0, "Phan Le Van Minh", "0384586xxx", "Tran Cao Van, Thanh Khe, Da Nang", false));
        listInfoUser.add(new User(0, "Le Phuoc Duc", "0384586xxx", "Đinh Tiên Hoàng, Hải Châu, Đà Nẵng", false));
        listInfoUser.add(new User(0, "Phạm Thanh Trúc", "0384586xxx", "Dien Bien Phu, Thanh Khe, Da Nang", false));

        rcvInformationList = (RecyclerView) findViewById(R.id.recyclerView_info_list);
        adapter = new UserInfoAdapter(listInfoUser);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(UserInformationActivity.this, LinearLayoutManager.VERTICAL, false);
        rcvInformationList.setLayoutManager(linearLayoutManager);
        rcvInformationList.setAdapter(adapter);

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
    }
}