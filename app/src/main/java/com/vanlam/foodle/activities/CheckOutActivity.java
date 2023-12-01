package com.vanlam.foodle.activities;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vanlam.foodle.R;
import com.vanlam.foodle.adapters.CheckoutItemAdapter;
import com.vanlam.foodle.adapters.Preferences;
import com.vanlam.foodle.database.DatabaseHandler;
import com.vanlam.foodle.models.Cart;
import com.vanlam.foodle.models.Order;
import com.vanlam.foodle.models.User;
import com.vanlam.foodle.models.Voucher;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
public class CheckOutActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_CHANGE_USER_INFORMATION = 1;
    public static final int REQUEST_CODE_PICK_VOUCHER = 2;
    private ImageView imgBack;
    private TextView tvChangeLocation, tvUserName, tvUserPhone, tvUserAddress, tvTotalPrice, tvTotalPayment;
    private MaterialButton btnOrder;
    private LinearLayout layoutMoreVoucher;
    private RecyclerView rcvCheckout;
    private CheckoutItemAdapter checkoutAdapter;
    private List<Cart> listItemCheckout;
    private User infoUser;
    private Voucher infoVoucher;
    private EditText inputOrderNote;
    private DatabaseReference reference;
    private double totalPrice = 0, totalPricePayment = 0;
    private DecimalFormat df = new DecimalFormat("#,###.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        mapping();
        reference = FirebaseDatabase.getInstance().getReference("Orders");

        // Get ra List các food mà user chọn để checkout
        listItemCheckout = new ArrayList<>();
        if (getIntent() != null) {
            listItemCheckout = getIntent().getParcelableArrayListExtra("listItemsCheckout");
            loadItemsCheckout();
        }

        // Tính tổng tiền phải trả ban đầu khi chưa áp dụng voucher
        for (Cart item : listItemCheckout) {
            totalPrice += (item.getFoodPrice() * item.getQuantity());
        }
        tvTotalPrice.setText(df.format(totalPrice) + "đ");
        totalPricePayment = totalPrice + 10000d;
        tvTotalPayment.setText(df.format(totalPricePayment) + "đ");

        SpannableString content = new SpannableString("Thay đổi");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        tvChangeLocation.setText(content);
        tvChangeLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CheckOutActivity.this, UserInformationActivity.class);
                startActivityForResult(intent, REQUEST_CODE_CHANGE_USER_INFORMATION);
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleOrderFood();
            }
        });
        layoutMoreVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CheckOutActivity.this, ManageVoucherActivity.class);
                startActivityForResult(intent, REQUEST_CODE_PICK_VOUCHER);
            }
        });
    }
    private void mapping() {
        imgBack = findViewById(R.id.image_back);
        tvChangeLocation = findViewById(R.id.tv_change_location);
        btnOrder = findViewById(R.id.btn_order);
        layoutMoreVoucher = findViewById(R.id.layout_more_voucher);
        rcvCheckout = (RecyclerView) findViewById(R.id.recyclerView_checkout);
        tvUserName = findViewById(R.id.name_cust);
        tvUserPhone = findViewById(R.id.phone_cust);
        tvUserAddress = findViewById(R.id.address_cust);
        inputOrderNote = findViewById(R.id.input_take_note);
        tvTotalPrice = findViewById(R.id.tv_totalPrice);
        tvTotalPayment = findViewById(R.id.tv_totalMoney);
    }
    private void loadItemsCheckout() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rcvCheckout.setLayoutManager(linearLayoutManager);
        checkoutAdapter = new CheckoutItemAdapter(listItemCheckout);
        rcvCheckout.setAdapter(checkoutAdapter);
    }
    public void setUserInformation() {
        if (infoUser != null) {
            tvUserName.setText(infoUser.getName());
            tvUserPhone.setText(infoUser.getPhoneNumber());
            tvUserAddress.setText(infoUser.getAddress());
        }
    }

    public void setVoucherInfomation() {
        if (infoVoucher != null) {
            TextView tvVoucherName = findViewById(R.id.title_voucher);
            tvVoucherName.setText(infoVoucher.getName());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHANGE_USER_INFORMATION && resultCode == RESULT_OK) {
            infoUser = (User) data.getSerializableExtra("dataUser");
            setUserInformation();
        }

        if (requestCode == REQUEST_CODE_PICK_VOUCHER && resultCode == RESULT_OK) {
            infoVoucher = (Voucher) data.getSerializableExtra("dataVoucher");
            setVoucherInfomation();
            calculatorTotalPaymentAfterVoucher();       // Mỗi lần thay đổi voucher thì phải thực hiện tính lại tổng số tiền phải trả
        }
    }

    private void handleOrderFood() {
        // Validate some info checkout
        if (infoUser == null) {
            Toast.makeText(this, "Chọn / Thay đổi thông tin nhận hàng", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get ra các giá trị để insert lên database
        String receiverName = tvUserName.getText().toString();
        String receiverPhone = tvUserPhone.getText().toString();
        String receiverAddress = tvUserAddress.getText().toString();
        String orderNote = inputOrderNote.getText().toString();

        String formattedTime = "";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDateTime currentTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy");
            formattedTime = currentTime.format(formatter);
        }

        // Tạo order và insert vào database
        Order order = new Order(receiverName, receiverPhone, receiverAddress, listItemCheckout, orderNote, totalPricePayment, formattedTime, Preferences.getDataUser(this).getPhoneNumber());
        reference.child(String.valueOf(System.currentTimeMillis())).setValue(order).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                // Xóa các food đã thanh toán ra khỏi giỏ hàng
                DatabaseHandler db = new DatabaseHandler(CheckOutActivity.this);
                db.openDatabase(Preferences.getDataUser(CheckOutActivity.this).getPhoneNumber());
                for (Cart item : listItemCheckout) {
                    db.deleteCartItem(item.getCardId());
                }

                startActivity(new Intent(CheckOutActivity.this, OrderSuccessActivity.class));
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CheckOutActivity.this, "Đặt hàng thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void calculatorTotalPaymentAfterVoucher() {
        totalPricePayment = totalPrice + 10000d;
        double discountPrice = 0;
        if (infoVoucher != null) {
            discountPrice = infoVoucher.getDiscount() * totalPricePayment;
        }
        TextView tvDiscount = findViewById(R.id.tv_discount);
        tvDiscount.setText("- " + df.format(discountPrice) + "đ");
        totalPricePayment = totalPricePayment - discountPrice;
        tvTotalPayment.setText(df.format(totalPricePayment) + "đ");
    }
}