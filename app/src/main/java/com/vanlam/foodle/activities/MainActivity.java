package com.vanlam.foodle.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.vanlam.foodle.R;
import com.vanlam.foodle.fragments.FoodListFragment;
import com.vanlam.foodle.fragments.HomeFragment;
import com.vanlam.foodle.fragments.CartFragment;

public class MainActivity extends AppCompatActivity {
    private HomeFragment homeFragment;
    private FoodListFragment foodListFragment;
    private CartFragment cartFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        homeFragment = new HomeFragment();
        foodListFragment = new FoodListFragment();
        cartFragment = new CartFragment();
        setFragment(homeFragment);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_item_home:
                        setFragment(homeFragment);
                        break;
                    case R.id.menu_item_foodList:
                        setFragment(foodListFragment);
                        break;
                    case R.id.menu_item_cart:
                        setFragment(cartFragment);
                        break;
                    case R.id.menu_item_account:
                        Toast.makeText(MainActivity.this, "My account screen is show", Toast.LENGTH_SHORT).show();
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