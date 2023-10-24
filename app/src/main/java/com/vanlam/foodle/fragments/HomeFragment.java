package com.vanlam.foodle.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vanlam.foodle.R;
import com.vanlam.foodle.activities.FoodDetailActivity;
import com.vanlam.foodle.adapters.FoodItemAdapter;
import com.vanlam.foodle.adapters.FoodSuggestAdapter;
import com.vanlam.foodle.adapters.VoucherAdapter;
import com.vanlam.foodle.listeners.FoodItemListener;
import com.vanlam.foodle.models.Food;
import com.vanlam.foodle.models.Voucher;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements FoodItemListener {
    public static final int REQUEST_CODE_VIEW_FOOD = 1;
    private RecyclerView recyclerViewFoodList, recyclerViewFoodSuggest, recyclerViewVoucher;
    private FoodItemAdapter foodItemAdapter;
    private FoodSuggestAdapter foodSuggestAdapter;
    private VoucherAdapter voucherAdapter;
    private List<Food> listFood, listSuggestList;
    private List<Voucher> listVoucher;
    private int foodItemPosition = -1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listFood = new ArrayList<>();
        listFood.add(new Food(R.drawable.img_food_item, "Caramel Macchiato đá", 55000,
                "Caramel Macchiato sẽ mang đến một sự ngạc nhiên thú vị khi vị thơm béo của bọt sữa, sữa tươi, vị đắng thanh thoát của cà phê Espresso hảo hạng và vị ngọt đậm của sốt caramel được gói gọn trong một tách cà phê."));
        listFood.add(new Food(R.drawable.img_food_item, "Caramel Macchiato đá", 55000,
                "Caramel Macchiato sẽ mang đến một sự ngạc nhiên thú vị khi vị thơm béo của bọt sữa, sữa tươi, vị đắng thanh thoát của cà phê Espresso hảo hạng và vị ngọt đậm của sốt caramel được gói gọn trong một tách cà phê."));
        listFood.add(new Food(R.drawable.img_food_item, "Caramel Macchiato đá", 55000,
                "Caramel Macchiato sẽ mang đến một sự ngạc nhiên thú vị khi vị thơm béo của bọt sữa, sữa tươi, vị đắng thanh thoát của cà phê Espresso hảo hạng và vị ngọt đậm của sốt caramel được gói gọn trong một tách cà phê."));
        listFood.add(new Food(R.drawable.img_food_item, "Caramel Macchiato đá", 55000,
                "Caramel Macchiato sẽ mang đến một sự ngạc nhiên thú vị khi vị thơm béo của bọt sữa, sữa tươi, vị đắng thanh thoát của cà phê Espresso hảo hạng và vị ngọt đậm của sốt caramel được gói gọn trong một tách cà phê."));
        listFood.add(new Food(R.drawable.img_food_item, "Caramel Macchiato đá", 55000,
                "Caramel Macchiato sẽ mang đến một sự ngạc nhiên thú vị khi vị thơm béo của bọt sữa, sữa tươi, vị đắng thanh thoát của cà phê Espresso hảo hạng và vị ngọt đậm của sốt caramel được gói gọn trong một tách cà phê."));
        listFood.add(new Food(R.drawable.img_food_item, "Caramel Macchiato đá", 55000,
                "Caramel Macchiato sẽ mang đến một sự ngạc nhiên thú vị khi vị thơm béo của bọt sữa, sữa tươi, vị đắng thanh thoát của cà phê Espresso hảo hạng và vị ngọt đậm của sốt caramel được gói gọn trong một tách cà phê."));
        listFood.add(new Food(R.drawable.img_food_item, "Caramel Macchiato đá", 55000,
                "Caramel Macchiato sẽ mang đến một sự ngạc nhiên thú vị khi vị thơm béo của bọt sữa, sữa tươi, vị đắng thanh thoát của cà phê Espresso hảo hạng và vị ngọt đậm của sốt caramel được gói gọn trong một tách cà phê."));

        listSuggestList = new ArrayList<>();
        listSuggestList.addAll(listFood);

        recyclerViewFoodList = (RecyclerView) view.findViewById(R.id.recyclerView_hsc_foodList);
        foodItemAdapter = new FoodItemAdapter(listFood, this);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerViewFoodList.setLayoutManager(staggeredGridLayoutManager);
        recyclerViewFoodList.setAdapter(foodItemAdapter);

        recyclerViewFoodSuggest = (RecyclerView) view.findViewById(R.id.recyclerView_hsc_list_suggest);
        foodSuggestAdapter = new FoodSuggestAdapter(listSuggestList);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewFoodSuggest.setLayoutManager(linearLayoutManager1);
        recyclerViewFoodSuggest.setAdapter(foodSuggestAdapter);

        listVoucher = new ArrayList<>();
        listVoucher.add(new Voucher(R.drawable.img_voucher, "Mua 1 tặng 1 dành cho bạn mới", "Mua 1 tặng 1 dành cho bạn mới"));
        listVoucher.add(new Voucher(R.drawable.img_voucher, "Mua 1 tặng 1 dành cho bạn mới", "Mua 1 tặng 1 dành cho bạn mới"));
        listVoucher.add(new Voucher(R.drawable.img_voucher, "Mua 1 tặng 1 dành cho bạn mới", "Mua 1 tặng 1 dành cho bạn mới"));
        listVoucher.add(new Voucher(R.drawable.img_voucher, "Mua 1 tặng 1 dành cho bạn mới", "Mua 1 tặng 1 dành cho bạn mới"));
        listVoucher.add(new Voucher(R.drawable.img_voucher, "Mua 1 tặng 1 dành cho bạn mới", "Mua 1 tặng 1 dành cho bạn mới"));

        recyclerViewVoucher = (RecyclerView) view.findViewById(R.id.recyclerView_hsc_vouchers);
        voucherAdapter = new VoucherAdapter(listVoucher);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewVoucher.setLayoutManager(linearLayoutManager2);
        recyclerViewVoucher.setAdapter(voucherAdapter);
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