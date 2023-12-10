package com.vanlam.foodleserver.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.makeramen.roundedimageview.RoundedImageView;
import com.vanlam.foodleserver.R;
import com.vanlam.foodleserver.listeners.OnClickVoucherListener;
import com.vanlam.foodleserver.models.Voucher;

public class VoucherItemAdapter extends FirebaseRecyclerAdapter<Voucher, VoucherItemAdapter.VoucherViewHolder> {
    private Context mContext;
    private OnClickVoucherListener voucherListener;

    public VoucherItemAdapter(@NonNull FirebaseRecyclerOptions<Voucher> options, Context context, OnClickVoucherListener listener) {
        super(options);
        this.mContext = context;
        this.voucherListener = listener;
    }

    @NonNull
    @Override
    public VoucherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_container_voucher, parent, false);
        return new VoucherViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull VoucherViewHolder holder, int position, @NonNull Voucher model) {
        String voucherId = getRef(position).getKey();

        holder.getTvVoucherName().setText(model.getName());
        holder.getTvVoucherExpiry().setText("HSD:  " + model.getExpiry());

        double discountPer = model.getDiscount() * 100d;
        holder.getTvVoucherDiscount().setText("Giảm giá:  " + String.valueOf(discountPer) + "%");
        holder.getTvVoucherType().setText("Loại voucher:  " + model.getType());

        Glide.with(holder.itemView).load(model.getImageUrl()).into(holder.getImageVoucher());

        holder.getLayoutContainer().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                voucherListener.onClick(voucherId, model);
            }
        });
    }

    public static class VoucherViewHolder extends RecyclerView.ViewHolder {
        private TextView tvVoucherName, tvVoucherExpiry, tvVoucherDiscount, tvVoucherType;
        private RoundedImageView imageVoucher;
        private ConstraintLayout layoutContainer;

        public VoucherViewHolder(@NonNull View itemView) {
            super(itemView);
            tvVoucherName = itemView.findViewById(R.id.tv_voucher_name);
            tvVoucherExpiry = itemView.findViewById(R.id.tv_voucher_expiry);
            tvVoucherDiscount = itemView.findViewById(R.id.tv_voucher_discount);
            tvVoucherType = itemView.findViewById(R.id.tv_voucher_type);
            imageVoucher = itemView.findViewById(R.id.img_voucher);
            layoutContainer = itemView.findViewById(R.id.layout_container_voucher);
        }

        public TextView getTvVoucherName() {
            return tvVoucherName;
        }

        public void setTvVoucherName(TextView tvVoucherName) {
            this.tvVoucherName = tvVoucherName;
        }

        public TextView getTvVoucherExpiry() {
            return tvVoucherExpiry;
        }

        public void setTvVoucherExpiry(TextView tvVoucherExpiry) {
            this.tvVoucherExpiry = tvVoucherExpiry;
        }

        public TextView getTvVoucherDiscount() {
            return tvVoucherDiscount;
        }

        public void setTvVoucherDiscount(TextView tvVoucherDiscount) {
            this.tvVoucherDiscount = tvVoucherDiscount;
        }

        public TextView getTvVoucherType() {
            return tvVoucherType;
        }

        public void setTvVoucherType(TextView tvVoucherType) {
            this.tvVoucherType = tvVoucherType;
        }

        public RoundedImageView getImageVoucher() {
            return imageVoucher;
        }

        public void setImageVoucher(RoundedImageView imageVoucher) {
            this.imageVoucher = imageVoucher;
        }

        public ConstraintLayout getLayoutContainer() {
            return layoutContainer;
        }

        public void setLayoutContainer(ConstraintLayout layoutContainer) {
            this.layoutContainer = layoutContainer;
        }
    }
}
