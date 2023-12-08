package com.vanlam.foodleserver.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vanlam.foodleserver.R;
import com.vanlam.foodleserver.adapters.FoodOrderAdapter;
import com.vanlam.foodleserver.models.Cart;
import com.vanlam.foodleserver.models.Order;
import com.vanlam.foodleserver.utils.CustomLinearLayoutManager;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrderDetailActivity extends AppCompatActivity {
    private DatabaseReference reference;
    private TextView tvReceiverName, tvReceiverPhone, tvReceiverAddr, tvOrderNote, tvTotalPayment, tvOrderId, tvOrderTime;
    private MaterialButton btnConfirm, btnCancel, btnComplete;
    private ConstraintLayout layoutButton;
    private NestedScrollView layoutBody;
    private RecyclerView rcvOrderList;
    private ImageView imgBack;
    private String orderId, orderStatus;
    private List<Cart> itemsOrder;
    private FoodOrderAdapter adapter;
    private Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        mapping();
        reference = FirebaseDatabase.getInstance().getReference("Orders");

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        itemsOrder = new ArrayList<>();
        if (getIntent() != null) {
            orderId = getIntent().getStringExtra("orderId");
            order = (Order) getIntent().getSerializableExtra("orderData");
            itemsOrder = order.getOrderList();
            orderStatus = order.getOrderStatus();

            showInformationOrder();
        }

        // Kiểm tra tình trạng đơn hàng để hiển thị giao diện chi tiết thích hợp
        switch (orderStatus) {
            case "1":
                loadInterfaceConfirm();
                break;
            case "3":
                loadInterfaceConfirmed();
                break;
            case "2":
            case "4":
            case "5":
                loadInterfaceDeliveryAndSuccess();
                break;
        }
    }

    public void mapping() {
        imgBack = findViewById(R.id.image_back);
        tvReceiverName = findViewById(R.id.tv_receiver_name);
        tvReceiverPhone = findViewById(R.id.tv_receiver_phone);
        tvReceiverAddr = findViewById(R.id.tv_receiver_address);
        tvTotalPayment = findViewById(R.id.tv_total_payment);
        tvOrderId = findViewById(R.id.tv_order_id);
        tvOrderTime = findViewById(R.id.tv_order_time);
        rcvOrderList = findViewById(R.id.recyclerView_orders);
        tvOrderNote = findViewById(R.id.tv_order_note);
        btnConfirm = findViewById(R.id.btn_confirm);
        btnCancel = findViewById(R.id.btn_cancel);
        btnComplete = findViewById(R.id.btn_completed);
        layoutButton = findViewById(R.id.layout_bottom);
        layoutBody = findViewById(R.id.layout_body_content);
    }

    private void showInformationOrder() {
        tvReceiverName.setText(order.getReceiverName());
        tvReceiverPhone.setText(order.getReceiverPhoneNumber());
        tvReceiverAddr.setText(order.getReceiverAddress());
        tvOrderId.setText("#" + orderId);
        tvOrderTime.setText(order.getOrderTime());

        if (order.getOrderNote().equals("")) {
            tvOrderNote.setText("Không");
        } else {
            tvOrderNote.setText(order.getOrderNote());
        }

        DecimalFormat df = new DecimalFormat("#,###.##");
        tvTotalPayment.setText(df.format(order.getTotalPayment()) + "đ");

        CustomLinearLayoutManager layoutManager = new CustomLinearLayoutManager(OrderDetailActivity.this, LinearLayoutManager.VERTICAL, false);
        rcvOrderList.setLayoutManager(layoutManager);
        adapter = new FoodOrderAdapter(itemsOrder);
        rcvOrderList.setAdapter(adapter);
    }

    public void loadInterfaceConfirm() {
        // Hiển thị ra 2 button để xử lý xác nhận đơn hàng
        btnConfirm.setVisibility(View.VISIBLE);
        btnCancel.setVisibility(View.VISIBLE);
        btnComplete.setVisibility(View.GONE);

        // Xác nhận đơn hàng để chuyển đơn sang trạng thái Đã xác nhận
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference.child(orderId).child("orderStatus").setValue("3")
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(OrderDetailActivity.this, "Xác nhận thành công", Toast.LENGTH_SHORT).show();
                                onBackPressed();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("Foodle", e.toString());
                            }
                        });
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference.child(orderId).child("orderStatus").setValue("2")
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(OrderDetailActivity.this, "Đã hủy đơn", Toast.LENGTH_SHORT).show();
                                onBackPressed();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("Foodle", e.toString());
                            }
                        });
            }
        });
    }

    // Hiển thị UI chi tiết cho đơn ở trạng thái Đã xác nhận và Chuẩn bị hàng hoàn tất
    public void loadInterfaceConfirmed() {
        // Hiển thị button tương ứng
        btnComplete.setVisibility(View.VISIBLE);
        btnConfirm.setVisibility(View.GONE);
        btnCancel.setVisibility(View.GONE);

        // Chuyển đơn hàng sang trạng thái Đang giao hàng
        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference.child(orderId).child("orderStatus").setValue("4")
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(OrderDetailActivity.this, "Tài xế sẽ xuất phát ngay!", Toast.LENGTH_SHORT).show();
                                onBackPressed();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("Foodle", e.toString());
                            }
                        });
            }
        });
    }

    // Hiển thị UI cho đơn ở trạng thái Đang giao hàng và Thành công
    public void loadInterfaceDeliveryAndSuccess() {
        layoutButton.setVisibility(View.GONE);  // Ẩn phần layout chứa các button

        CoordinatorLayout.LayoutParams layoutParams = new CoordinatorLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, // Chiều rộng của NestedScrollView
                ViewGroup.LayoutParams.MATCH_PARENT  // Chiều cao của NestedScrollView
        );
        int marginTopValue = 40;
        int marginTopInPixels = (int) (marginTopValue * getResources().getDisplayMetrics().density);
        int marginBottomValue = 12;
        int marginBottomInPixels = (int) (marginBottomValue * getResources().getDisplayMetrics().density);
        layoutParams.setMargins(0, marginTopInPixels, 0, marginBottomInPixels);
        layoutBody.setLayoutParams(layoutParams);
    }
}