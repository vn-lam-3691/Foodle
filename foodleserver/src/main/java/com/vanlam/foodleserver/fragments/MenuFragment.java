package com.vanlam.foodleserver.fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.vanlam.foodleserver.R;
import com.vanlam.foodleserver.activities.FoodInformationActivity;
import com.vanlam.foodleserver.adapters.MenuPagerAdapter;

public class MenuFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MenuPagerAdapter pagerAdapter;
    private final String[] categoriesId = {"01", "02", "03", "04", "05"};
    private FloatingActionButton fabCreateNew;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tabLayout = view.findViewById(R.id.tabLayout_category);
        viewPager = view.findViewById(R.id.viewPager_container);
        fabCreateNew = view.findViewById(R.id.fab_add);

        fabCreateNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), FoodInformationActivity.class));
            }
        });

        createTabLayout();
    }
    private void createTabLayout() {
        pagerAdapter = new MenuPagerAdapter(getChildFragmentManager(), categoriesId);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}