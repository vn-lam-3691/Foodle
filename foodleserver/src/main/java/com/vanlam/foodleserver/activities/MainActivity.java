package com.vanlam.foodleserver.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.vanlam.foodleserver.R;
import com.vanlam.foodleserver.fragments.MenuFragment;
import com.vanlam.foodleserver.fragments.MyAccountFragment;
import com.vanlam.foodleserver.fragments.OrderFragment;

public class MainActivity extends AppCompatActivity {
    private OrderFragment orderFragment;
    private MenuFragment menuFragment;
    private MyAccountFragment myAccountFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        orderFragment = new OrderFragment();
        menuFragment = new MenuFragment();
        myAccountFragment = new MyAccountFragment();

        setFragment(orderFragment);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_item_order:
                        setFragment(orderFragment);
                        break;
                    case R.id.menu_item_menu:
                        setFragment(menuFragment);
                        break;
                    case R.id.menu_item_account:
                        setFragment(myAccountFragment);
                        break;
                }
                return true;
            }
        });
    }

    private void setFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.layout_fragment, fragment, null)
                .commit();
    }
}