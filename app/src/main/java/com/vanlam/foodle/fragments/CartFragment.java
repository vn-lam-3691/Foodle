package com.vanlam.foodle.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

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
import com.vanlam.foodle.adapters.Preferences;
import com.vanlam.foodle.database.DatabaseHandler;
import com.vanlam.foodle.listeners.OnCartItemSelectedListener;
import com.vanlam.foodle.models.Cart;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment implements OnCartItemSelectedListener {
    private List<Cart> listItemCart;
    private RecyclerView rcvCartList;
    private CartItemAdapter adapter;
    private TextView tvTotalMoney, txtNotice, tvDelete;
    private MaterialButton btnCheckout;
    private View rootView;
    private DatabaseHandler db;

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

        db = new DatabaseHandler(getActivity().getApplicationContext());
        tvDelete = view.findViewById(R.id.tv_delete_item);
        txtNotice = view.findViewById(R.id.txt_cart_notice);
        tvTotalMoney = view.findViewById(R.id.tv_totalMoney);
        btnCheckout = view.findViewById(R.id.btn_checkout);
        rcvCartList = (RecyclerView) view.findViewById(R.id.recyclerView_car_list);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        rcvCartList.setLayoutManager(linearLayoutManager);
        loadItemCart();

        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkoutCart();
            }
        });

        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteCartItem();
            }
        });
    }
    private void checkoutCart() {
        Intent intent = new Intent(rootView.getContext(), CheckOutActivity.class);
        List<Integer> positionItemsCheckout = adapter.getListSelectedCart();
        ArrayList<Cart> itemsCheckout = new ArrayList<>();

        if (positionItemsCheckout.size() != 0) {
            for (int pos : positionItemsCheckout) {
                Cart item = adapter.getCartAtPosition(pos);
                itemsCheckout.add(item);
            }
            intent.putParcelableArrayListExtra("listItemsCheckout", itemsCheckout);
            startActivity(intent);
        }
        else {
            Toast.makeText(rootView.getContext(), "Chọn món cần thanh toán", Toast.LENGTH_SHORT).show();
        }
    }

    // Thực hiện xóa các item đã được chọn trong Cart
    private void deleteCartItem() {
        List<Integer> postList = adapter.getListSelectedCart();
        if (postList.size() != 0) {
            for (int position : postList) {
                Cart cart = adapter.getCartAtPosition(position);
                db.deleteCartItem(cart.getCardId());
                listItemCart.remove(position);
                adapter.notifyItemRemoved(position);
            }
            postList.clear();

            if (listItemCart.size() == 0) {
                txtNotice.setVisibility(View.VISIBLE);
            }
        }
        tvTotalMoney.setText("0đ");
        return;
    }

    private void loadItemCart() {
        db.openDatabase(Preferences.getDataUser(getActivity().getApplicationContext()).getPhoneNumber());
        listItemCart = db.getCarts();
        if (listItemCart.size() == 0) {
            txtNotice.setVisibility(View.VISIBLE);
        }
        else {
            txtNotice.setVisibility(View.GONE);
            adapter = new CartItemAdapter(listItemCart, this);
            rcvCartList.setAdapter(adapter);
        }
    }

    // Tính tổng tiền trong giỏ hàng
    public void calculatorTotalCart() {
        double totalPrice = adapter.calculatorTotalSelectedPrice();
        DecimalFormat df = new DecimalFormat("#,###.##");
        tvTotalMoney.setText(df.format(totalPrice) + "đ");
    }

    // Override lại phương thức của interface gọi tới từ CartItemAdapter
    @Override
    public void onItemSelectedChanged() {
        calculatorTotalCart();
    }
}