package com.vanlam.foodle.adapters;

import android.annotation.SuppressLint;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
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
import com.vanlam.foodle.R;
import com.vanlam.foodle.listeners.OnVoucherListener;
import com.vanlam.foodle.models.Voucher;


public class VoucherVerticalAdapter extends FirebaseRecyclerAdapter<Voucher, VoucherVerticalAdapter.VoucherVertViewHolder> {
    private OnVoucherListener voucherListener;


    public VoucherVerticalAdapter(@NonNull FirebaseRecyclerOptions<Voucher> options, OnVoucherListener listener) {
                super(options);
                this.voucherListener = listener;
            }

            @NonNull
            @Override
            public VoucherVertViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_container_vouchers, parent, false);
                return new VoucherVertViewHolder(view);
            }

            @Override

                protected void onBindViewHolder(@NonNull VoucherVertViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull Voucher model) {
                    holder.getTvVoucherName().setText(model.getName());
                    holder.getTvVoucherExpiry().setText("HSD:  " + model.getExpiry());
                    Glide.with(holder.itemView).load(model.getImageUrl()).into(holder.getImgVoucher());

                    SpannableString content = new SpannableString("Điều kiện áp dụng");
                    content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                    holder.getTvConditional().setText(content);

                    holder.getLayoutVoucher().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            voucherListener.onClick(model);
                        }
                    });
                }


                    public static class VoucherVertViewHolder extends RecyclerView.ViewHolder {
                        private TextView tvVoucherName, tvVoucherExpiry, tvConditional;
                        private RoundedImageView imgVoucher;

                        private ConstraintLayout layoutVoucher;

                        public VoucherVertViewHolder(@NonNull View itemView) {
                            super(itemView);
                            tvVoucherName = itemView.findViewById(R.id.tv_voucher_name);
                            tvVoucherExpiry = itemView.findViewById(R.id.tv_voucher_expiry);
                            imgVoucher = itemView.findViewById(R.id.img_voucher);

                            tvConditional = itemView.findViewById(R.id.tv_conditional);
                            layoutVoucher = itemView.findViewById(R.id.layout_container_voucher);
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
                        public RoundedImageView getImgVoucher() {
                            return imgVoucher;
                        }
                        public void setImgVoucher(RoundedImageView imgVoucher) {
                            this.imgVoucher = imgVoucher;
                        }




                        public TextView getTvConditional() {
                            return tvConditional;
                        }

                        public void setTvConditional(TextView tvConditional) {
                            this.tvConditional = tvConditional;
                        }

                        public ConstraintLayout getLayoutVoucher() {
                            return layoutVoucher;
                        }

                        public void setLayoutVoucher(ConstraintLayout layoutVoucher) {
                            this.layoutVoucher = layoutVoucher;
                        }
                    }
                }