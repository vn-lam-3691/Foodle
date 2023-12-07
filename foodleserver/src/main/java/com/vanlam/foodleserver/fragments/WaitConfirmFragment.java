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

public class WaitConfirmFragment extends Fragment {
    private DatabaseReference reference;
    private RecyclerView rcvWaitConfirm;
    private FirebaseRecyclerAdapter<Order, OrderItemAdapter.OrderItemViewHolder> adapter;
    private FirebaseRecyclerOptions<Order> options;
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView =  inflater.inflate(R.layout.fragment_wait_confirm, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        reference = FirebaseDatabase.getInstance().getReference("Orders");

        rcvWaitConfirm = rootView.findViewById(R.id.recyclerView_wait_confirm);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rcvWaitConfirm.setLayoutManager(layoutManager);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);

        loadListOrder();
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

    private void loadListOrder() {
        options = new FirebaseRecyclerOptions.Builder<Order>()
                .setQuery(reference.orderByChild("orderStatus").equalTo("1"), Order.class)
                .build();
        adapter = new OrderItemAdapter(options, getContext());
        rcvWaitConfirm.setAdapter(adapter);
    }
}