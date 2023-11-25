package com.vanlam.foodle.adapters;

import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.vanlam.foodle.R;
import com.vanlam.foodle.models.Voucher;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class VoucherVerticalAdapter extends RecyclerView.Adapter<VoucherVerticalAdapter.VoucherVertViewHolder> {
    private List<Voucher> listVouchers;

    public VoucherVerticalAdapter(List<Voucher> listVouchers) {
        this.listVouchers = listVouchers;
    }

    @NonNull
    @Override
    public VoucherVertViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_container_vouchers, parent, false);
        return new VoucherVertViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VoucherVertViewHolder holder, int position) {
        Voucher item = listVouchers.get(position);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        holder.getTvVoucherExpiry().setText("HSD:  " + df.format(item.getExpiry()));

        SpannableString content = new SpannableString("Điều kiện áp dụng");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        holder.getTvConditional().setText(content);
    }

    @Override
    public int getItemCount() {
        return listVouchers.size();
    }

    static class VoucherVertViewHolder extends RecyclerView.ViewHolder {
        private TextView tvVoucherName, tvVoucherExpiry, tvConditional;
        private RoundedImageView imgVoucher;
        private CheckBox cbChoose;

        public VoucherVertViewHolder(@NonNull View itemView) {
            super(itemView);
            tvVoucherName = itemView.findViewById(R.id.tv_voucher_name);
            tvVoucherExpiry = itemView.findViewById(R.id.tv_voucher_expiry);
            imgVoucher = itemView.findViewById(R.id.img_voucher);
            cbChoose = itemView.findViewById(R.id.cb_choose_voucher);
            tvConditional = itemView.findViewById(R.id.tv_conditional);
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

        public CheckBox getCbChoose() {
            return cbChoose;
        }

        public void setCbChoose(CheckBox cbChoose) {
            this.cbChoose = cbChoose;
        }

        public TextView getTvConditional() {
            return tvConditional;
        }

        public void setTvConditional(TextView tvConditional) {
            this.tvConditional = tvConditional;
        }
    }
}
