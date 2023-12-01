package com.vanlam.foodle.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.button.MaterialButton;
import com.vanlam.foodle.R;
import com.vanlam.foodle.listeners.HandleCancelOrder;
import com.vanlam.foodle.models.Cart;
import com.vanlam.foodle.models.Order;

import java.util.List;

public class OrderHistoryAdapter extends FirebaseRecyclerAdapter<Order, OrderHistoryAdapter.OrderHistoryViewHolder> {
    public static Context mContext;
    private HandleCancelOrder cancelOrder;

    public OrderHistoryAdapter(@NonNull FirebaseRecyclerOptions<Order> options, Context context, HandleCancelOrder cancel) {
        super(options);
        this.mContext = context;
        this.cancelOrder = cancel;
    }

    @NonNull
    @Override
    public OrderHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_history, parent, false);
        return new OrderHistoryViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull OrderHistoryViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull Order model) {
        String orderId = getRef(position).getKey();
        holder.getTvOrderId().setText("#" + orderId);
        holder.getTvOrderTime().setText(model.getOrderTime());

        // Set giá trị cho RecyclerView để hiển thị thông tin
        holder.setDataForListItems(model.getOrderList());

        String status = "";
        switch (model.getOrderStatus()) {
            case "1":
                status = "Chờ xác nhận";
                break;
            case "2":
                holder.getBtnCancel().setVisibility(View.GONE);
                status = "Đã hủy đơn";
                break;
            case "3":
                holder.getBtnCancel().setVisibility(View.GONE);
                status = "Giao hàng thành công";
                break;
        }
        holder.getTvOrderStatus().setText(status);

        holder.getBtnCancel().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelOrder.cancelOrder(orderId);
            }
        });
    }

    public static class OrderHistoryViewHolder extends RecyclerView.ViewHolder {
        private TextView tvOrderId, tvOrderTime, tvOrderStatus;
        private RecyclerView rcvOrderItems;
        private MaterialButton btnCancel;

        public OrderHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderId = itemView.findViewById(R.id.tv_order_id);
            tvOrderTime = itemView.findViewById(R.id.tv_order_time);
            tvOrderStatus = itemView.findViewById(R.id.tv_status_order);
            rcvOrderItems = itemView.findViewById(R.id.recyclerView_order_items);
            btnCancel = itemView.findViewById(R.id.btn_cancel_order);
        }

        public void setDataForListItems(List<Cart> listItems) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
            rcvOrderItems.setLayoutManager(layoutManager);
            CheckoutItemAdapter adapter = new CheckoutItemAdapter(listItems);
            rcvOrderItems.setAdapter(adapter);
        }

        public TextView getTvOrderId() {
            return tvOrderId;
        }

        public void setTvOrderId(TextView tvOrderId) {
            this.tvOrderId = tvOrderId;
        }

        public TextView getTvOrderTime() {
            return tvOrderTime;
        }

        public void setTvOrderTime(TextView tvOrderTime) {
            this.tvOrderTime = tvOrderTime;
        }

        public TextView getTvOrderStatus() {
            return tvOrderStatus;
        }

        public void setTvOrderStatus(TextView tvOrderStatus) {
            this.tvOrderStatus = tvOrderStatus;
        }

        public RecyclerView getRcvOrderItems() {
            return rcvOrderItems;
        }

        public void setRcvOrderItems(RecyclerView rcvOrderItems) {
            this.rcvOrderItems = rcvOrderItems;
        }

        public MaterialButton getBtnCancel() {
            return btnCancel;
        }

        public void setBtnCancel(MaterialButton btnCancel) {
            this.btnCancel = btnCancel;
        }
    }
}