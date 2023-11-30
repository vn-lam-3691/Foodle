package com.vanlam.foodle.activities;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vanlam.foodle.R;
import com.vanlam.foodle.adapters.VoucherVerticalAdapter;
import com.vanlam.foodle.listeners.OnVoucherListener;
import com.vanlam.foodle.models.Voucher;
import com.vanlam.foodle.utils.CustomLinearLayoutManager;


    public class ManageVoucherActivity extends AppCompatActivity implements OnVoucherListener {
        private RecyclerView rcvVouchers;
        private FirebaseRecyclerOptions options;
        private FirebaseRecyclerAdapter<Voucher, VoucherVerticalAdapter.VoucherVertViewHolder> adapterVoucher;
        private ImageView imgBack;
        private DatabaseReference reference;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_manage_voucher);

            reference = FirebaseDatabase.getInstance().getReference("Vouchers");
            imgBack = findViewById(R.id.image_back);
            rcvVouchers = findViewById(R.id.recyclerView_vouchers);


            CustomLinearLayoutManager linearLayoutManager = new CustomLinearLayoutManager(ManageVoucherActivity.this, LinearLayoutManager.VERTICAL, false);
            rcvVouchers.setLayoutManager(linearLayoutManager);
            loadVouchers();

            imgBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
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
                    .setQuery(reference.orderByChild("type"), Voucher.class)
                    .build();
            adapterVoucher = new VoucherVerticalAdapter(options, this);
            rcvVouchers.setAdapter(adapterVoucher);
        }

        @Override
        public void onClick(Voucher voucher) {
            Intent intent = new Intent();
            intent.putExtra("dataVoucher", voucher);
            setResult(RESULT_OK, intent);
            finish();
        }
    }