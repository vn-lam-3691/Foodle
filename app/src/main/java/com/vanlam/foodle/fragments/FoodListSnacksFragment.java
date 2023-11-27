package com.vanlam.foodle.fragments;

import android.content.Context;
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

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vanlam.foodle.R;
import com.vanlam.foodle.adapters.FoodItemAdapter;
import com.vanlam.foodle.models.Food;

public class FoodListSnacksFragment extends Fragment {
    private RecyclerView rcvFoodListSnacks;
    private FirebaseRecyclerAdapter<Food, FoodItemAdapter.FoodItemViewHolder> foodItemAdapter;
    private FirebaseRecyclerOptions<Food> options;
    private FragmentManager fragmentManager;
    private Context mContext;
    private View rootView;
    private DatabaseReference reference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_food_list_snacks, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getActivity().getApplicationContext();

        rcvFoodListSnacks = (RecyclerView) rootView.findViewById(R.id.recyclerView_foodList_snacks);
        fragmentManager = getActivity().getSupportFragmentManager();
        reference = FirebaseDatabase.getInstance().getReference();

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rcvFoodListSnacks.setLayoutManager(staggeredGridLayoutManager);

        options = new FirebaseRecyclerOptions.Builder<Food>()
                .setQuery(reference.child("Product").orderByChild("idCategory").equalTo("0" + String.valueOf(5)), Food.class)
                .build();
        foodItemAdapter = new FoodItemAdapter(options);
        rcvFoodListSnacks.setAdapter(foodItemAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        foodItemAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        foodItemAdapter.stopListening();
    }
}