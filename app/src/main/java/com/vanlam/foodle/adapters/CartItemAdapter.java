package com.vanlam.foodle.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.vanlam.foodle.R;
import com.vanlam.foodle.database.DatabaseHandler;
import com.vanlam.foodle.listeners.OnCartItemSelectedListener;
import com.vanlam.foodle.models.Cart;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.CartItemViewHolder> {
    private List<Cart> listItemCart;
    private List<Integer> selectedItem = new ArrayList<>();
    public Context mContext;
    private OnCartItemSelectedListener itemSelectedListener;
    private DatabaseHandler db;

    public CartItemAdapter(List<Cart> listItemCart, OnCartItemSelectedListener listener) {
        this.listItemCart = listItemCart;
        this.itemSelectedListener = listener;
    }

    @NonNull
    @Override
    public CartItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_container_cart, parent, false);
        mContext = parent.getContext();
        db = new DatabaseHandler(mContext);
        return new CartItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Cart item = listItemCart.get(position);
        final int[] quantity = {item.getQuantity()};
        Glide.with(holder.itemView).load(item.getImageUrlFood()).into(holder.getImageCartItem());
        holder.getNameCartItem().setText(item.getFoodName());
        DecimalFormat df = new DecimalFormat("#,###.##");
        holder.getPriceCartItem().setText(df.format(item.getFoodPrice() * quantity[0]) + "đ");
        holder.getQtyCartItem().setText(String.valueOf(quantity[0]));
        holder.getSpinnerSizeItem().setSelection(changeToSizeValue(item.getSize()));

        db.openDatabase(Preferences.getDataUser(mContext).getPhoneNumber());
        holder.getImgIncrease().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantity[0]++;
                holder.getQtyCartItem().setText(String.valueOf(quantity[0]));
                holder.getPriceCartItem().setText(df.format(item.getFoodPrice() * quantity[0]) + "đ");

                db.updateCartItem(item.getCardId(), String.valueOf(holder.getSpinnerSizeItem().getSelectedItem()), quantity[0]);

                if (holder.getCboCartItem().isChecked()) {
                    itemSelectedListener.onItemSelectedChanged();   // Nếu item đó được check thì thực hiện gọi đến interface lắng nghe click để sync giá tiền
                }
            }
        });

        holder.getImgDecrease().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quantity[0] > 1) {
                    quantity[0]--;
                    holder.getQtyCartItem().setText(String.valueOf(quantity[0]));
                    holder.getPriceCartItem().setText(df.format(item.getFoodPrice() * quantity[0]) + "đ");

                    db.updateCartItem(item.getCardId(), String.valueOf(holder.getSpinnerSizeItem().getSelectedItem()), quantity[0]);

                    if (holder.getCboCartItem().isChecked()) {
                        itemSelectedListener.onItemSelectedChanged();   // Nếu item đó được check thì thực hiện gọi đến interface lắng nghe click để sync giá tiền
                    }
                }
            }
        });

        holder.getSpinnerSizeItem().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                db.updateCartItem(item.getCardId(),
                        String.valueOf(holder.getSpinnerSizeItem().getSelectedItem()),
                        Integer.valueOf(holder.getQtyCartItem().getText().toString()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });

        holder.getCboCartItem().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    selectedItem.add(position);
                    itemSelectedListener.onItemSelectedChanged();       // Thực hiện gọi đến interface lắng nghe click để sync giá tiền
                }
                else {
                    selectedItem.remove(selectedItem.size() - 1);
                    itemSelectedListener.onItemSelectedChanged();       // Thực hiện gọi đến interface lắng nghe click để sync giá tiền
                }
            }
        });
    }

    // Get ra List các position được chọn trong RecyclerView
    public List<Integer> getListSelectedCart() {
        return selectedItem;
    }

    // Get ra Cart với vị trí của item trong RecyclerView
    public Cart getCartAtPosition(int position) {
        db.openDatabase(Preferences.getDataUser(mContext).getPhoneNumber());
        List<Cart> itemsCart = db.getCarts();
        return itemsCart.get(position);
    }

    // Phthuc Tính tổng tiền của các food được chọn
    public double calculatorTotalSelectedPrice() {
        double totalPrice = 0d;
        for (int position : selectedItem) {
            Cart cart = this.getCartAtPosition(position);
            totalPrice += (cart.getFoodPrice() * cart.getQuantity());
        }
        return totalPrice;
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
