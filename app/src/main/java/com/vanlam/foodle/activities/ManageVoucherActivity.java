package com.vanlam.foodle.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.vanlam.foodle.R;
import com.vanlam.foodle.adapters.VoucherVerticalAdapter;
import com.vanlam.foodle.models.Voucher;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class ManageVoucherActivity extends AppCompatActivity {
    public static final int ID_TYPE_DELIVERY = 1;
    public static final int ID_TYPE_DISCOUNT = 2;
    private RecyclerView rcvVoucherDelivery, rcvVoucherDiscount;
    private VoucherVerticalAdapter adapterForDelivery, adapterForDiscount;
    private List<Voucher> listVoucher, listVoucherDelivery, listVoucherDiscount;
    private ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_voucher);

        imgBack = findViewById(R.id.image_back);

        try {
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            listVoucher = new ArrayList<>();
        }
        catch (Exception e) { }

        listVoucherDelivery = new ArrayList<>();
        listVoucherDiscount = new ArrayList<>();
        for (Voucher voucher : listVoucher) {
//            if (voucher.getType() == ID_TYPE_DELIVERY) {
//                listVoucherDelivery.add(voucher);
//            }
//            else if (voucher.getType() == ID_TYPE_DISCOUNT) {
//                listVoucherDiscount.add(voucher);
//            }
        }

        rcvVoucherDelivery = findViewById(R.id.recyclerView_vouchers_delivery);
        rcvVoucherDiscount = findViewById(R.id.recyclerView_vouchers_discount);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(ManageVoucherActivity.this, LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(ManageVoucherActivity.this, LinearLayoutManager.VERTICAL, false);
        adapterForDelivery = new VoucherVerticalAdapter(listVoucherDelivery);
        adapterForDiscount = new VoucherVerticalAdapter(listVoucherDiscount);

        rcvVoucherDelivery.setLayoutManager(linearLayoutManager1);
        rcvVoucherDiscount.setLayoutManager(linearLayoutManager2);
        rcvVoucherDelivery.setAdapter(adapterForDelivery);
        rcvVoucherDiscount.setAdapter(adapterForDiscount);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}