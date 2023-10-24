package com.vanlam.foodle.listeners;

import android.view.View;

import com.vanlam.foodle.models.Food;

public interface FoodItemListener {
    public void onClick(View view, Food foodItem, int position);
}
