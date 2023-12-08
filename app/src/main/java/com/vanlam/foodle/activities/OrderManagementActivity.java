package com.vanlam.foodle.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vanlam.foodle.R;
import com.vanlam.foodle.adapters.OrderHistoryAdapter;
import com.vanlam.foodle.adapters.Preferences;
import com.vanlam.foodle.listeners.HandleOrder;
import com.vanlam.foodle.models.Order;

public class OrderManagementActivity extends AppCompatActivity implements HandleOrder {
    private ImageView imgBack;
    private RecyclerView rcvOrderHistory;
    private FirebaseRecyclerOptions options;
    private FirebaseRecyclerAdapter<Order, OrderHistoryAdapter.OrderHistoryViewHolder> adapter;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_management);

        reference = FirebaseDatabase.getInstance().getReference("Orders");

        imgBack = findViewById(R.id.image_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        rcvOrderHistory = findViewById(R.id.recyclerView_order_history);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        rcvOrderHistory.setLayoutManager(layoutManager);
        showOrderHistory();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    private void showOrderHistory() {
        options = new FirebaseRecyclerOptions.Builder<Order>()
                .setQuery(reference.orderByChild("userId").equalTo(Preferences.getDataUser(this).getPhoneNumber()), Order.class)
                .build();
        adapter = new OrderHistoryAdapter(options, OrderManagementActivity.this, this);
        rcvOrderHistory.setAdapter(adapter);
    }

    @Override
    public void cancelOrder(String orderId) {
        reference.child(orderId).child("orderStatus").setValue("2");
        Toast.makeText(this, "Hủy đơn hàng thành công", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void receivedOrder(String orderId) {
        reference.child(orderId).child("orderStatus").setValue("5");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
        Toast.makeText(this, "Cảm ơn bạn đã mua hàng!", Toast.LENGTH_SHORT).show();
    }
}