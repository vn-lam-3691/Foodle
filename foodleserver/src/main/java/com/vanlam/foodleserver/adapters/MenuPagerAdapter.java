package com.vanlam.foodleserver.adapters;

import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.vanlam.foodleserver.fragments.FoodListFragment;

public class MenuPagerAdapter extends FragmentStatePagerAdapter {
    private String[] categoriesId;

    public MenuPagerAdapter(@NonNull FragmentManager fm, String[] cateId) {
        super(fm);
        this.categoriesId = cateId;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 1:
                return FoodListFragment.newInstance(categoriesId[1]);
            case 2:
                return FoodListFragment.newInstance(categoriesId[2]);
            case 3:
                return FoodListFragment.newInstance(categoriesId[3]);
            case 4:
                return FoodListFragment.newInstance(categoriesId[4]);
            default:
                return FoodListFragment.newInstance(categoriesId[0]);
        }
    }

    @Override
    public int getCount() {
        return categoriesId.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 1:
                return "Cà phê pha máy";
            case 2:
                return "Trà xanh";
            case 3:
                return "Trà sữa";
            case 4:
                return "Ăn nhẹ";
            default:
                return "Cà phê truyền thống";
        }
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
