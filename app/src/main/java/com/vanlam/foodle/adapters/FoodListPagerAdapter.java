package com.vanlam.foodle.adapters;

import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.google.android.material.tabs.TabLayout;
import com.vanlam.foodle.fragments.FoodListCfTraditionalFragment;

import com.vanlam.foodle.fragments.FoodListAllFragment;
import com.vanlam.foodle.fragments.FoodListCoffeeMachineFragment;
import com.vanlam.foodle.fragments.FoodListMilkTeaFragment;
import com.vanlam.foodle.fragments.FoodListSnacksFragment;
import com.vanlam.foodle.fragments.FoodListTeaFragment;

// TODO: Adapter cầu nối giữa 2 thành phần ViewPager và TabLayout sử dụng để hiển thị lên FoodListFragment
public class FoodListPagerAdapter extends FragmentStatePagerAdapter {
    private final String[] PAGE_TITLES = new String[] {
            "Tất cả",
            "Cà phê truyền thống",
            "Cà phê pha máy",
            "Trà xanh",
            "Trà sữa",
            "Ăn nhẹ"
    };
    private final Fragment[] PAGES = new Fragment[] {
            new FoodListAllFragment(),
            new FoodListCfTraditionalFragment(),
            new FoodListCoffeeMachineFragment(),
            new FoodListTeaFragment(),
            new FoodListMilkTeaFragment(),
            new FoodListSnacksFragment()
    };
    private TabLayout tabLayout;

    public FoodListPagerAdapter(@NonNull FragmentManager fm, TabLayout mTabLayout) {
        super(fm);
        this.tabLayout = mTabLayout;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return PAGES[position];
    }

    @Override
    public int getCount() {
        return PAGES.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return PAGE_TITLES[position];
    }

    @Override
    public void restoreState(@Nullable Parcelable state, @Nullable ClassLoader loader) {
        try {
            super.restoreState(state, loader);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
