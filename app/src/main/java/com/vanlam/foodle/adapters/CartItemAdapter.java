package com.vanlam.foodle.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.vanlam.foodle.R;
import com.vanlam.foodle.models.Cart;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.CartItemViewHolder> {
    private List<Cart> listItemCart;

    public CartItemAdapter(List<Cart> listItemCart) {
        this.listItemCart = listItemCart;
    }

    @NonNull
    @Override
    public CartItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_container_cart, parent, false);
        return new CartItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemViewHolder holder, int position) {
        Cart item = listItemCart.get(position);
        int quantity = item.getQuantity();
        Glide.with(holder.itemView).load(item.getImageUrlFood()).into(holder.getImageCartItem());
        holder.getNameCartItem().setText(item.getFoodName());
        DecimalFormat df = new DecimalFormat("#,###.##");
        holder.getPriceCartItem().setText(df.format(item.getFoodPrice() * quantity) + "đ");
        holder.getQtyCartItem().setText(String.valueOf(quantity));
        holder.getSpinnerSizeItem().setSelection(changeToSizeValue(item.getSize()));
    }

    private int changeToSizeValue(String size) {
        int intSize = 0;
        switch (size) {
            case "S": intSize = 0; break;
            case "M": intSize = 1; break;
            case "L": intSize = 2; break;
        }
        return intSize;
    }

    @Override
    public int getItemCount() {
        return listItemCart.size();
    }

    static class CartItemViewHolder extends RecyclerView.ViewHolder {
        private RoundedImageView imageCartItem;
        private TextView nameCartItem, priceCartItem, qtyCartItem;
        private CheckBox cboCartItem;
        private Spinner spinnerSizeItem;
        private ImageView imgIncrease, imgDecrease;
        private List<String> listSize;

        public CartItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imageCartItem = itemView.findViewById(R.id.image_cartItem);
            nameCartItem = itemView.findViewById(R.id.tv_name_cartItem);
            priceCartItem = itemView.findViewById(R.id.tv_item_price);
            qtyCartItem = itemView.findViewById(R.id.quantity_cartItem);
            cboCartItem = itemView.findViewById(R.id.checkbox_cartItem);
            imgIncrease = itemView.findViewById(R.id.image_increase);
            imgDecrease = itemView.findViewById(R.id.image_decrease);
            spinnerSizeItem = itemView.findViewById(R.id.spinner_size);

            // Set danh sách các option mặc định cho size của food
            listSize = new ArrayList<>();
            listSize.add("S");
            listSize.add("M");
            listSize.add("L");
            ArrayAdapter spinnerSizeAdapter = new ArrayAdapter(itemView.getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listSize);
            spinnerSizeItem.setAdapter(spinnerSizeAdapter);
        }

        public RoundedImageView getImageCartItem() {
            return imageCartItem;
        }

        public void setImageCartItem(RoundedImageView imageCartItem) {
            this.imageCartItem = imageCartItem;
        }

        public TextView getNameCartItem() {
            return nameCartItem;
        }

        public void setNameCartItem(TextView nameCartItem) {
            this.nameCartItem = nameCartItem;
        }

        public TextView getPriceCartItem() {
            return priceCartItem;
        }

        public void setPriceCartItem(TextView priceCartItem) {
            this.priceCartItem = priceCartItem;
        }

        public CheckBox getCboCartItem() {
            return cboCartItem;
        }

        public void setCboCartItem(CheckBox cboCartItem) {
            this.cboCartItem = cboCartItem;
        }

        public Spinner getSpinnerSizeItem() {
            return spinnerSizeItem;
        }

        public void setSpinnerSizeItem(Spinner spinnerSizeItem) {
            this.spinnerSizeItem = spinnerSizeItem;
        }

        public TextView getQtyCartItem() {
            return qtyCartItem;
        }

        public void setQtyCartItem(TextView qtyCartItem) {
            this.qtyCartItem = qtyCartItem;
        }

        public ImageView getImgIncrease() {
            return imgIncrease;
        }

        public void setImgIncrease(ImageView imgIncrease) {
            this.imgIncrease = imgIncrease;
        }

        public ImageView getImgDecrease() {
            return imgDecrease;
        }

        public void setImgDecrease(ImageView imgDecrease) {
            this.imgDecrease = imgDecrease;
        }
    }
}
