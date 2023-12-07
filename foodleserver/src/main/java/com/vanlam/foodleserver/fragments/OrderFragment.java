package com.vanlam.foodleserver.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.vanlam.foodleserver.R;
import com.vanlam.foodleserver.adapters.OrderStatusPagerAdapter;
import com.vanlam.foodleserver.utils.Preferences;

public class OrderFragment extends Fragment {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private OrderStatusPagerAdapter adapter;
    private TextView tvUserName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_order, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPager = view.findViewById(R.id.viewPager_container);
        tabLayout = view.findViewById(R.id.tabLayout_status);

        tvUserName = view.findViewById(R.id.tv_user_name);
        tvUserName.setText("Xin chào, " + Preferences.getDataUser(getContext()).getName());

        createTabFragment();
    }

    private void createTabFragment() {
        adapter = new OrderStatusPagerAdapter(getChildFragmentManager(), tabLayout);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}