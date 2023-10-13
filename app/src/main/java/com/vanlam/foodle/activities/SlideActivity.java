package com.vanlam.foodle.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.button.MaterialButton;
import com.vanlam.foodle.R;
import com.vanlam.foodle.adapters.ViewPagerAdapter;
import com.vanlam.foodle.models.ViewPagerModel;

import java.util.ArrayList;
import java.util.List;

public class SlideActivity extends AppCompatActivity {
    private List<ViewPagerModel> listItems;
    private ViewPager2 viewPager;
    private LinearLayout layoutOnboardingIndicator;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide);

        viewPager = findViewById(R.id.view_pager);
        layoutOnboardingIndicator = (LinearLayout) findViewById(R.id.layout_onboarding);

        ViewPagerModel model1 = new ViewPagerModel(R.drawable.viewpager_1, "Tất cả món ngon trong tầm tay", "Đừng rời khỏi nhà, rời khỏi chỗ, chỉ chọn món ăn ngon yêu thích của bạn với một cú nhấn");
        ViewPagerModel model2 = new ViewPagerModel(R.drawable.viewpager_2, "Nhận ưu đãi miễn phí giao hàng", "Chỉ cần đặt hàng chúng tôi sẽ làm việc còn lại với chi phí ưu đãi");

        listItems = new ArrayList<>();
        listItems.add(model1);
        listItems.add(model2);

        adapter = new ViewPagerAdapter(listItems);
        viewPager.setAdapter(adapter);
        viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);

        setupOnboardingIndicator();
        setupCurrentOnboardingIndicator(0);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setupCurrentOnboardingIndicator(position);
            }
        });

        MaterialButton btnNext = findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewPager.getCurrentItem() + 1 < adapter.getItemCount()) {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                }
                else {
                    Intent intent = new Intent(SlideActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void setupOnboardingIndicator() {
        ImageView[] indicators = new ImageView[adapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8, 0, 8, 0);
        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.onboarding_indicator_inactive));
            indicators[i].setLayoutParams(layoutParams);
            layoutOnboardingIndicator.addView(indicators[i]);
        }
    }

    private void setupCurrentOnboardingIndicator(int index) {
        int childCount = layoutOnboardingIndicator.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView img = (ImageView) layoutOnboardingIndicator.getChildAt(i);
            if (i == index) {
                img.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(), R.drawable.onboarding_indicator_active)
                );
            }
            else {
                img.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(), R.drawable.onboarding_indicator_inactive)
                );
            }
        }
    }
}