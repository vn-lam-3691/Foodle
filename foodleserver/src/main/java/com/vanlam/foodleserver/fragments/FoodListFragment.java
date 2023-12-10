package com.vanlam.foodleserver.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.vanlam.foodleserver.R;
import com.vanlam.foodleserver.adapters.FoodItemAdapter;
import com.vanlam.foodleserver.models.Food;
import com.vanlam.foodleserver.utils.CustomLinearLayoutManager;

public class FoodListFragment extends Fragment {
    private RecyclerView rcvFoodList;
    private DatabaseReference reference;
    private FirebaseRecyclerAdapter<Food, FoodItemAdapter.FoodItemViewHolder> adapter;
    private FirebaseRecyclerOptions<Food> options;
    private View rootView;
    private String categoryId;

    public static FoodListFragment newInstance(String cateId) {
        FoodListFragment fragment = new FoodListFragment();
        Bundle args = new Bundle();
        args.putString("categoryId", cateId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_food_list, container, false);
        reference = FirebaseDatabase.getInstance().getReference("Product");

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            categoryId = getArguments().getString("categoryId", "01");
        }

        rcvFoodList = rootView.findViewById(R.id.recyclerView_foods_list);
        CustomLinearLayoutManager layoutManager = new CustomLinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rcvFoodList.setLayoutManager(layoutManager);

        // Xử lý dựa vào id danh mục để hiển thị sản phẩm
        loadContentPage(categoryId);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    private void loadContentPage(String categoryId) {
        Query query = reference.orderByChild("idCategory").equalTo(categoryId);
        options = new FirebaseRecyclerOptions.Builder<Food>()
                .setQuery(query, Food.class)
                .build();
        adapter = new FoodItemAdapter(options, getContext());
        rcvFoodList.setAdapter(adapter);
    }
}