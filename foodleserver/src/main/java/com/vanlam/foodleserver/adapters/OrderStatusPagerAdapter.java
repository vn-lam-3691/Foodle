package com.vanlam.foodleserver.adapters;

import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.google.android.material.tabs.TabLayout;
import com.vanlam.foodleserver.fragments.CancelledFragment;
import com.vanlam.foodleserver.fragments.ConfirmedFragment;
import com.vanlam.foodleserver.fragments.DeliveringFragment;
import com.vanlam.foodleserver.fragments.SuccessOrderFragment;
import com.vanlam.foodleserver.fragments.WaitConfirmFragment;

public class OrderStatusPagerAdapter extends FragmentStatePagerAdapter {
    private TabLayout tabLayout;
    private final String[] PAGE_TITLES = new String[] {
            "Chờ xác nhận",
            "Đã xác nhận",
            "Đang giao hàng",
            "Đơn thành công",
            "Đã hủy"
    };

    private final Fragment[] PAGES = new Fragment[] {
            new WaitConfirmFragment(),
            new ConfirmedFragment(),
            new DeliveringFragment(),
            new SuccessOrderFragment(),
            new CancelledFragment()
    };

    public OrderStatusPagerAdapter(@NonNull FragmentManager fm, TabLayout tabLayout) {
        super(fm);
        this.tabLayout = tabLayout;
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
