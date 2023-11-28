package com.vanlam.foodle.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.vanlam.foodle.R;
import com.vanlam.foodle.models.Cart;

import java.text.DecimalFormat;
import java.util.List;

public class CheckoutItemAdapter extends RecyclerView.Adapter<CheckoutItemAdapter.CheckoutItemViewHolder> {
    private List<Cart> checkoutList;

    public CheckoutItemAdapter(List<Cart> checkoutList) {
        this.checkoutList = checkoutList;
    }

    @NonNull
    @Override
    public CheckoutItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_container_checkout, parent, false);
        return new CheckoutItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckoutItemViewHolder holder, int position) {
        Cart item = checkoutList.get(position);
        holder.getTvNameCheckout().setText(item.getFoodName());
        holder.getTvSizeCheckout().setText("Size:   " + item.getSize());
        holder.getTvQuantity().setText(item.getQuantity() + " x ");
        DecimalFormat df = new DecimalFormat("#,###.##");
        holder.getTvItemPrice().setText(df.format(item.getFoodPrice()) + "đ");

        Glide.with(holder.itemView).load(item.getImageUrlFood()).into(holder.getImageCheckout());

        double totalPriceOfItem = item.getQuantity() * item.getFoodPrice();
        holder.getTvItemTotalPrice().setText(df.format(totalPriceOfItem) + "đ");
    }

    @Override
    public int getItemCount() {
        return checkoutList.size();
    }

    static class CheckoutItemViewHolder extends RecyclerView.ViewHolder {
        private RoundedImageView imageCheckout;
        private TextView tvNameCheckout, tvSizeCheckout, tvQuantity, tvItemPrice, tvItemTotalPrice;

        public CheckoutItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imageCheckout = (RoundedImageView) itemView.findViewById(R.id.image_checkout_item);
            tvNameCheckout = (TextView) itemView.findViewById(R.id.tv_name_checkout_item);
            tvSizeCheckout = (TextView) itemView.findViewById(R.id.tv_size_checkout_item);
            tvQuantity = (TextView) itemView.findViewById(R.id.tv_quantity);
            tvItemPrice = (TextView) itemView.findViewById(R.id.tv_price_item);
            tvItemTotalPrice = (TextView) itemView.findViewById(R.id.tv_price_total_item);
        }

        public RoundedImageView getImageCheckout() {
            return imageCheckout;
        }

        public void setImageCheckout(RoundedImageView imageCheckout) {
            this.imageCheckout = imageCheckout;
        }

        public TextView getTvNameCheckout() {
            return tvNameCheckout;
        }

        public void setTvNameCheckout(TextView tvNameCheckout) {
            this.tvNameCheckout = tvNameCheckout;
        }

        public TextView getTvSizeCheckout() {
            return tvSizeCheckout;
        }

        public void setTvSizeCheckout(TextView tvSizeCheckout) {
            this.tvSizeCheckout = tvSizeCheckout;
        }

        public TextView getTvQuantity() {
            return tvQuantity;
        }

        public void setTvQuantity(TextView tvQuantity) {
            this.tvQuantity = tvQuantity;
        }

        public TextView getTvItemPrice() {
            return tvItemPrice;
        }

        public void setTvItemPrice(TextView tvItemPrice) {
            this.tvItemPrice = tvItemPrice;
        }

        public TextView getTvItemTotalPrice() {
            return tvItemTotalPrice;
        }

        public void setTvItemTotalPrice(TextView tvItemTotalPrice) {
            this.tvItemTotalPrice = tvItemTotalPrice;
        }
    }
}