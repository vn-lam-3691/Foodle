package com.vanlam.foodle.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.vanlam.foodle.R;
import com.vanlam.foodle.activities.SearchFoodActivity;
import com.vanlam.foodle.adapters.FoodListPagerAdapter;

public class FoodListFragment extends Fragment {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private FoodListPagerAdapter adapter;
    private LinearLayout layoutSearch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_food_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPager = view.findViewById(R.id.viewPager_container);
        tabLayout = view.findViewById(R.id.tabLayout_category);
        createTabFragment();

        layoutSearch = view.findViewById(R.id.layout_input_search);
        layoutSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), SearchFoodActivity.class));
            }
        });
    }

    private void createTabFragment() {
        adapter = new FoodListPagerAdapter(getChildFragmentManager(), tabLayout);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}