package com.vanlam.foodleserver.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vanlam.foodleserver.R;
import com.vanlam.foodleserver.adapters.VoucherItemAdapter;
import com.vanlam.foodleserver.fragments.CreateNewVoucher;
import com.vanlam.foodleserver.listeners.OnClickVoucherListener;
import com.vanlam.foodleserver.models.Voucher;
import com.vanlam.foodleserver.utils.CustomLinearLayoutManager;

public class ManageVoucherActivity extends AppCompatActivity implements OnClickVoucherListener {
    private RecyclerView rcvVouchers;
    private FirebaseRecyclerOptions options;
    private FirebaseRecyclerAdapter<Voucher, VoucherItemAdapter.VoucherViewHolder> adapterVoucher;
    private ImageView imgBack, imgCreateNew;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_voucher);

        reference = FirebaseDatabase.getInstance().getReference("Vouchers");
        imgBack = findViewById(R.id.image_back);
        imgCreateNew = findViewById(R.id.ic_add_new);
        rcvVouchers = findViewById(R.id.recyclerView_vouchers);

        CustomLinearLayoutManager linearLayoutManager = new CustomLinearLayoutManager(ManageVoucherActivity.this, LinearLayoutManager.VERTICAL, false);
        rcvVouchers.setLayoutManager(linearLayoutManager);
        loadVouchers();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        imgCreateNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateNewVoucher.newInstance("createNew", null, null).show(getSupportFragmentManager(), CreateNewVoucher.TAG);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapterVoucher.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapterVoucher.stopListening();
    }

    private void loadVouchers() {
        options = new FirebaseRecyclerOptions.Builder<Voucher>()
                .setQuery(reference.orderByKey(), Voucher.class)
                .build();
        adapterVoucher = new VoucherItemAdapter(options, this, this);
        rcvVouchers.setAdapter(adapterVoucher);
    }

    @Override
    public void onClick(String id, Voucher voucher) {
        CreateNewVoucher.newInstance("modify", voucher, id).show(getSupportFragmentManager(), CreateNewVoucher.TAG);
    }
}