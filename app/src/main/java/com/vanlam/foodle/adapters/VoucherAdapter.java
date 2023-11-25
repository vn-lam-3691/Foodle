package com.vanlam.foodle.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.vanlam.foodle.R;
import com.vanlam.foodle.models.Voucher;

import java.util.List;

public class VoucherAdapter extends FirebaseRecyclerAdapter<Voucher, VoucherAdapter.VoucherViewHolder> {

    public VoucherAdapter(@NonNull FirebaseRecyclerOptions<Voucher> options) {
        super(options);
    }

    @NonNull
    @Override
    public VoucherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_container_voucher_hsc, parent, false);
        return new VoucherViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull VoucherViewHolder holder, int position, @NonNull Voucher model) {
        holder.getTitleVoucher().setText(model.getName());

        Glide.with(holder.itemView).load(model.getImageUrl()).into(holder.getImageVoucher());
    }

    public static class VoucherViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageVoucher;
        private TextView titleVoucher;

        public VoucherViewHolder(@NonNull View itemView) {
            super(itemView);
            imageVoucher = (ImageView) itemView.findViewById(R.id.image_voucher);
            titleVoucher = (TextView) itemView.findViewById(R.id.title_voucher);
        }

        public ImageView getImageVoucher() {
            return imageVoucher;
        }

        public void setImageVoucher(ImageView imageVoucher) {
            this.imageVoucher = imageVoucher;
        }

        public TextView getTitleVoucher() {
            return titleVoucher;
        }

        public void setTitleVoucher(TextView titleVoucher) {
            this.titleVoucher = titleVoucher;
        }
    }
}
