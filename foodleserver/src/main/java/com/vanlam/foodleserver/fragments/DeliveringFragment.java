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

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vanlam.foodleserver.R;
import com.vanlam.foodleserver.adapters.OrderItemAdapter;
import com.vanlam.foodleserver.models.Order;
import com.vanlam.foodleserver.utils.CustomLinearLayoutManager;

public class DeliveringFragment extends Fragment {

    private DatabaseReference reference;
    private RecyclerView rcvDelivering;
    private FirebaseRecyclerAdapter<Order, OrderItemAdapter.OrderItemViewHolder> adapter;
    private FirebaseRecyclerOptions<Order> options;
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_delivering, container, false);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        reference = FirebaseDatabase.getInstance().getReference("Orders");

        rcvDelivering = rootView.findViewById(R.id.recyclerView_delivering);
        CustomLinearLayoutManager layoutManager = new CustomLinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rcvDelivering.setLayoutManager(layoutManager);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);

        loadData();
    }

    private void loadData() {
        options = new FirebaseRecyclerOptions.Builder<Order>()
                .setQuery(reference.orderByChild("orderStatus").equalTo("4"), Order.class)
                .build();
        adapter = new OrderItemAdapter(options, getContext());
        rcvDelivering.setAdapter(adapter);
    }
}