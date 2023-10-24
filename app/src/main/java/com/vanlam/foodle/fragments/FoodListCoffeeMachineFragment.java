package com.vanlam.foodle.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vanlam.foodle.R;
import com.vanlam.foodle.activities.FoodDetailActivity;
import com.vanlam.foodle.adapters.FoodItemAdapter;
import com.vanlam.foodle.listeners.FoodItemListener;
import com.vanlam.foodle.models.Food;

import java.util.ArrayList;
import java.util.List;

// TODO: Fragment nằm trong ViewPager để hiển thị một tập Product theo danh mục
public class FoodListCoffeeMachineFragment extends Fragment implements FoodItemListener {
    public static final int REQUEST_CODE_VIEW_FOOD = 1;
    private List<Food> foodListCfMachine;
    private RecyclerView rcvFoodListCFM;
    private FoodItemAdapter foodItemAdapter;
    private FragmentManager fragmentManager;
    private Context mContext;
    private View rootView;
    private int foodItemPosition = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_food_list_coffee_machine, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getActivity().getApplicationContext();

        rcvFoodListCFM = (RecyclerView) rootView.findViewById(R.id.recyclerView_foodList_CfMachine);
        fragmentManager = getActivity().getSupportFragmentManager();

        foodListCfMachine = new ArrayList<>();
        foodListCfMachine.add(new Food(R.drawable.img_food_item, "Caramel Macchiato đá", 55000,
                "Caramel Macchiato sẽ mang đến một sự ngạc nhiên thú vị khi vị thơm béo của bọt sữa, sữa tươi, vị đắng thanh thoát của cà phê Espresso hảo hạng và vị ngọt đậm của sốt caramel được gói gọn trong một tách cà phê."));
        foodListCfMachine.add(new Food(R.drawable.img_food_item, "Caramel Macchiato đá", 55000,
                "Caramel Macchiato sẽ mang đến một sự ngạc nhiên thú vị khi vị thơm béo của bọt sữa, sữa tươi, vị đắng thanh thoát của cà phê Espresso hảo hạng và vị ngọt đậm của sốt caramel được gói gọn trong một tách cà phê."));
        foodListCfMachine.add(new Food(R.drawable.img_food_item, "Caramel Macchiato đá", 55000,
                "Caramel Macchiato sẽ mang đến một sự ngạc nhiên thú vị khi vị thơm béo của bọt sữa, sữa tươi, vị đắng thanh thoát của cà phê Espresso hảo hạng và vị ngọt đậm của sốt caramel được gói gọn trong một tách cà phê."));

        foodItemAdapter = new FoodItemAdapter(foodListCfMachine, this);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rcvFoodListCFM.setLayoutManager(staggeredGridLayoutManager);
        rcvFoodListCFM.setAdapter(foodItemAdapter);
    }

    @Override
    public void onClick(View view, Food foodItem, int position) {
        foodItemPosition = position;
        Intent intent = new Intent(getContext(), FoodDetailActivity.class);
        intent.putExtra("food", foodItem);
        startActivityForResult(intent, REQUEST_CODE_VIEW_FOOD);
        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}