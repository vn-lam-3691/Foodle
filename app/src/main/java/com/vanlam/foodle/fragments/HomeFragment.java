package com.vanlam.foodle.fragments;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.vanlam.foodle.R;
import com.vanlam.foodle.adapters.FoodItemAdapter;
import com.vanlam.foodle.adapters.FoodSuggestAdapter;
import com.vanlam.foodle.adapters.VoucherAdapter;
import com.vanlam.foodle.models.Food;
import com.vanlam.foodle.models.Voucher;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements View.OnClickListener {
    public static final int REQUEST_CODE_VIEW_FOOD = 1;
    private RecyclerView recyclerViewFoodList, recyclerViewFoodSuggest, recyclerViewVoucher;
    private DatabaseReference tbFood;
    private FirebaseRecyclerAdapter<Food, FoodItemAdapter.FoodItemViewHolder> foodItemAdapter;
    private FirebaseRecyclerAdapter<Food, FoodSuggestAdapter.FoodSuggestViewHolder> foodSuggestAdapter;
    private FirebaseRecyclerOptions<Food> options, optionsSuggest;
    private FirebaseRecyclerAdapter<Voucher, VoucherAdapter.VoucherViewHolder> voucherAdapter;
    private FirebaseRecyclerOptions<Voucher> optionsVoucher;
    private View rootView;
    private LinearLayout itmCate1, itmCate2, itmCate3, itmCate4, itmCate5;
    public static int idCategory = 1;
    private ProgressBar progressLoad;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapping(view);
        tbFood = FirebaseDatabase.getInstance().getReference();

        // Load danh sách vài sản phẩm lên phần Danh mục trong trang Home
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerViewFoodList.setLayoutManager(staggeredGridLayoutManager);
        new LoadListFood().execute();

        // Load vài sản phẩm lên phần Đề xuất
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewFoodSuggest.setLayoutManager(linearLayoutManager1);
//        new LoadListFoodSuggest().execute();

        // Load danh sách voucher
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewVoucher.setLayoutManager(linearLayoutManager2);
//        new LoadListVoucher().execute();
    }

    @Override
    public void onStart() {
        super.onStart();
        foodItemAdapter.startListening();
        foodSuggestAdapter.startListening();
        voucherAdapter.startListening();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        foodItemAdapter.stopListening();
        foodSuggestAdapter.stopListening();
        voucherAdapter.stopListening();
    }

    private void mapping(View view) {
        itmCate1 = view.findViewById(R.id.item_category_1);
        itmCate1.setOnClickListener(this);
        itmCate2 = view.findViewById(R.id.item_category_2);
        itmCate2.setOnClickListener(this);
        itmCate3 = view.findViewById(R.id.item_category_3);
        itmCate3.setOnClickListener(this);
        itmCate4 = view.findViewById(R.id.item_category_4);
        itmCate4.setOnClickListener(this);
        itmCate5 = view.findViewById(R.id.item_category_5);
        itmCate5.setOnClickListener(this);

        recyclerViewFoodList = (RecyclerView) view.findViewById(R.id.recyclerView_hsc_foodList);
        progressLoad = rootView.findViewById(R.id.progress_food_load);

        recyclerViewFoodSuggest = (RecyclerView) view.findViewById(R.id.recyclerView_hsc_list_suggest);

        recyclerViewVoucher = (RecyclerView) view.findViewById(R.id.recyclerView_hsc_vouchers);
    }

    @Override
    public void onClick(View view) {
        int oldPos = idCategory;
        if (view == itmCate1) {
            idCategory = 1;
            setViewItemCategory(itmCate1, oldPos);
        }
        else if (view == itmCate2) {
            idCategory = 2;
            setViewItemCategory(itmCate2, oldPos);
        }
        else if (view == itmCate3) {
            idCategory = 3;
            setViewItemCategory(itmCate3, oldPos);
        }
        else if (view == itmCate4) {
            idCategory = 4;
            setViewItemCategory(itmCate4, oldPos);
        }
        else if (view == itmCate5) {
            idCategory = 5;
            setViewItemCategory(itmCate5, oldPos);
        }
        Query query = tbFood.child("Product").orderByChild("idCategory").equalTo("0" + String.valueOf(idCategory)).limitToLast(6);
        new LoadListFoodBaseCate().execute(query);
    }

    private void setViewItemCategory(View itemView, int oldPos) {
        TextView tvCategory;
        itemView.setBackgroundResource(R.drawable.background_item_cate_active);
        tvCategory = itemView.findViewWithTag("tv_category");
        tvCategory.setTextColor(Color.WHITE);

        LinearLayout layoutCate = rootView.findViewById(R.id.layout_category);
        View layoutItemCate = layoutCate.getChildAt(oldPos - 1);
        layoutItemCate.setBackgroundResource(R.drawable.baclground_item_cate_inactive);
        tvCategory = layoutItemCate.findViewWithTag("tv_category");
        tvCategory.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.colorTextSecond));
    }

    @SuppressLint("StaticFieldLeak")
    class LoadListFood extends AsyncTask<Void, Void, FirebaseRecyclerAdapter<Food, FoodItemAdapter.FoodItemViewHolder>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressLoad.setVisibility(View.VISIBLE);
        }

        @Override
        protected FirebaseRecyclerAdapter<Food, FoodItemAdapter.FoodItemViewHolder> doInBackground(Void... voids) {
            options = new FirebaseRecyclerOptions.Builder<Food>()
                    .setQuery(tbFood.child("Product").orderByChild("idCategory").equalTo("0" + String.valueOf(idCategory)).limitToLast(6), Food.class)
                    .build();
            foodItemAdapter = new FoodItemAdapter(options);
            return foodItemAdapter;
        }

        @Override
        protected void onPostExecute(FirebaseRecyclerAdapter<Food, FoodItemAdapter.FoodItemViewHolder> foodItemAdapter) {
            super.onPostExecute(foodItemAdapter);
            recyclerViewFoodList.setAdapter(foodItemAdapter);
            progressLoad.setVisibility(View.GONE);
        }
    }

    @SuppressLint("StaticFieldLeak")
    class LoadListFoodBaseCate extends AsyncTask<Query, Void, FirebaseRecyclerAdapter<Food, FoodItemAdapter.FoodItemViewHolder>> {
        protected void onPreExecute() {
            super.onPreExecute();
            progressLoad.setVisibility(View.VISIBLE);
        }

        @Override
        protected FirebaseRecyclerAdapter<Food, FoodItemAdapter.FoodItemViewHolder> doInBackground(Query... query) {
            options = new FirebaseRecyclerOptions.Builder<Food>()
                    .setQuery(query[0], Food.class)
                    .build();
            foodItemAdapter.updateOptions(options);
            return foodItemAdapter;
        }

        @Override
        protected void onPostExecute(FirebaseRecyclerAdapter<Food, FoodItemAdapter.FoodItemViewHolder> newAdapter) {
            super.onPostExecute(newAdapter);
            recyclerViewFoodList.setAdapter(newAdapter);
            progressLoad.setVisibility(View.GONE);
        }
    }

    @SuppressLint("StaticFieldLeak")
    class LoadListFoodSuggest extends AsyncTask<Void, Void, FirebaseRecyclerAdapter<Food, FoodSuggestAdapter.FoodSuggestViewHolder>> {
        double indexStart;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            indexStart = (double) (Math.random() * 42 + 1);
        }

        @Override
        protected FirebaseRecyclerAdapter<Food, FoodSuggestAdapter.FoodSuggestViewHolder> doInBackground(Void... voids) {
            optionsSuggest = new FirebaseRecyclerOptions.Builder<Food>()
                    .setQuery(tbFood.child("Product").orderByChild("price").startAt(indexStart).limitToLast(7), Food.class)
                    .build();
            foodSuggestAdapter = new FoodSuggestAdapter(optionsSuggest);
            return foodSuggestAdapter;
        }

        @Override
        protected void onPostExecute(FirebaseRecyclerAdapter<Food, FoodSuggestAdapter.FoodSuggestViewHolder> foodSuggestAdapter) {
            super.onPostExecute(foodSuggestAdapter);
            recyclerViewFoodSuggest.setAdapter(foodSuggestAdapter);
        }
    }

    @SuppressLint("StaticFieldLeak")
    class LoadListVoucher extends AsyncTask<Void, Void, FirebaseRecyclerAdapter<Voucher, VoucherAdapter.VoucherViewHolder>> {

        @Override
        protected FirebaseRecyclerAdapter<Voucher, VoucherAdapter.VoucherViewHolder> doInBackground(Void... voids) {
            optionsVoucher = new FirebaseRecyclerOptions.Builder<Voucher>()
                    .setQuery(tbFood.child("Vouchers"), Voucher.class)
                    .build();
            voucherAdapter = new VoucherAdapter(optionsVoucher);
            return voucherAdapter;
        }

        @Override
        protected void onPostExecute(FirebaseRecyclerAdapter<Voucher, VoucherAdapter.VoucherViewHolder> voucherAdapter) {
            super.onPostExecute(voucherAdapter);
            recyclerViewVoucher.setAdapter(voucherAdapter);
        }
    }
}