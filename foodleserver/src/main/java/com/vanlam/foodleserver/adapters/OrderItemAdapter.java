package com.vanlam.foodleserver.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.button.MaterialButton;
import com.vanlam.foodleserver.R;
import com.vanlam.foodleserver.models.Cart;
import com.vanlam.foodleserver.models.Order;

import java.text.DecimalFormat;
import java.util.List;

public class OrderItemAdapter extends FirebaseRecyclerAdapter<Order, OrderItemAdapter.OrderItemViewHolder> {
    public static Context mContext;

    public OrderItemAdapter(@NonNull FirebaseRecyclerOptions<Order> options, Context context) {
        super(options);
        this.mContext = context;
    }

    @NonNull
    @Override
    public OrderItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_container_order, parent, false);
        return new OrderItemViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull OrderItemViewHolder holder, int position, @NonNull Order model) {
        String orderId = getRef(position).getKey();
        holder.getTvOrderId().setText("#" + orderId);
        holder.getTvOrderTime().setText(model.getOrderTime());
        holder.getTvOrderClientName().setText(model.getReceiverName());

        DecimalFormat df = new DecimalFormat("#,###.##");
        holder.getTvTotalPayment().setText(df.format(model.getTotalPayment()) + "đ");

        // 1 - Chờ xác nhận ; 2 - Đã hủy đơn hàng ; 3 - Đã xác nhận ; 4 - Đang giao hàng ; 5 - Giao hàng thành công
        String status = "";
        switch (model.getOrderStatus()) {
            case "1":
                status = "Chờ xác nhận";
                break;
            case "2":
                status = "Đã hủy đơn";
                break;
            case "3":
                status = "Đã xác nhận";
                break;
            case "4":
                status = "Đang giao hàng";
                break;
            case "5":
                status = "Đơn hàng thành công";
                break;
        }
        holder.getTvOrderStatus().setText(status);

        // Set List sản phẩm cho ListView để hiển thị lên
        holder.setDataListOrder(model.getOrderList());
    }

    public static class OrderItemViewHolder extends RecyclerView.ViewHolder {
        private TextView tvOrderId, tvOrderTime, tvOrderClientName, tvOrderStatus, tvTotalPayment;
        private ListView listOrders;
        private MaterialButton btnDetail;

        public OrderItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderId = itemView.findViewById(R.id.tv_order_id);
            tvOrderTime = itemView.findViewById(R.id.tv_order_time);
            tvOrderClientName = itemView.findViewById(R.id.tv_order_clientName);
            tvOrderStatus = itemView.findViewById(R.id.tv_order_status);
            tvTotalPayment = itemView.findViewById(R.id.tv_order_payment);
            btnDetail = itemView.findViewById(R.id.btn_detail_order);
            listOrders = itemView.findViewById(R.id.listView_order_items);
        }

        public void setDataListOrder(List<Cart> listOrder) {
            this.listOrders.setDivider(null);
            this.listOrders.setDividerHeight(0);
            FoodOrderAdapter adapter = new FoodOrderAdapter(listOrder);
            this.listOrders.setAdapter(adapter);
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

        public TextView getTvOrderClientName() {
            return tvOrderClientName;
        }

        public void setTvOrderClientName(TextView tvOrderClientName) {
            this.tvOrderClientName = tvOrderClientName;
        }

        public TextView getTvOrderStatus() {
            return tvOrderStatus;
        }

        public void setTvOrderStatus(TextView tvOrderStatus) {
            this.tvOrderStatus = tvOrderStatus;
        }

        public TextView getTvTotalPayment() {
            return tvTotalPayment;
        }

        public void setTvTotalPayment(TextView tvTotalPayment) {
            this.tvTotalPayment = tvTotalPayment;
        }

        public ListView getListOrders() {
            return listOrders;
        }

        public void setListOrders(ListView listOrders) {
            this.listOrders = listOrders;
        }

        public MaterialButton getBtnDetail() {
            return btnDetail;
        }

        public void setBtnDetail(MaterialButton btnDetail) {
            this.btnDetail = btnDetail;
        }
    }

    public static class FoodOrderAdapter extends BaseAdapter {
        private List<Cart> list;

        public FoodOrderAdapter(List<Cart> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_list_food, viewGroup, false);
            }

            final Cart itemCart = list.get(position);

            TextView tvQuantity, tvName;
            tvQuantity = view.findViewById(R.id.tv_quantity);
            tvName = view.findViewById(R.id.tv_food_name);

            tvQuantity.setText(String.valueOf(itemCart.getQuantity()) + " x ");
            tvName.setText(itemCart.getFoodName());

            return view;
        }
    }
}
