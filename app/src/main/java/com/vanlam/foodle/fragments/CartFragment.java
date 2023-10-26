package com.vanlam.foodle.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.vanlam.foodle.activities.CheckOutActivity;

import com.google.android.material.button.MaterialButton;
import com.vanlam.foodle.R;
import com.vanlam.foodle.activities.CheckOutActivity;
import com.vanlam.foodle.adapters.CartItemAdapter;
import com.vanlam.foodle.models.Cart;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {
    private List<Cart> listItemCart;
    private RecyclerView rcvCartList;
    private CartItemAdapter adapter;
    private TextView tvTotalMoney;
    private MaterialButton btnCheckout;
    private View rootView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_cart, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listItemCart = new ArrayList<>();
        listItemCart.add(new Cart(R.drawable.img_food_item, 1, "Caramel Macchiato đá", "S", 55000d, true));
        listItemCart.add(new Cart(R.drawable.img_food_item, 2, "Capuchino nóng", "M", 39000d, false));
        listItemCart.add(new Cart(R.drawable.img_food_item, 1, "Bạc sỉu", "L", 29000d, false));
        listItemCart.add(new Cart(R.drawable.img_food_item, 4, "Latte đá", "M", 49000d, false));

        tvTotalMoney = view.findViewById(R.id.tv_totalMoney);
        btnCheckout = view.findViewById(R.id.btn_checkout);

        rcvCartList = (RecyclerView) view.findViewById(R.id.recyclerView_car_list);
        adapter = new CartItemAdapter(listItemCart);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        rcvCartList.setLayoutManager(linearLayoutManager);
        rcvCartList.setAdapter(adapter);
        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), CheckOutActivity.class));
            }
        });
    }
}