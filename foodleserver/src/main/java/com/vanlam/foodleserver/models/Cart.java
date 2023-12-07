package com.vanlam.foodleserver.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Cart implements Serializable, Parcelable {
    private int cardId;
    private String foodId;
    private String foodName;
    private String imageUrlFood;
    private int quantity;
    private String size;
    private double foodPrice;

    public Cart() {
    }

    public Cart(String foodId, String foodName, String imageUrlFood, int quantity, String size, double foodPrice) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.imageUrlFood = imageUrlFood;
        this.quantity = quantity;
        this.size = size;
        this.foodPrice = foodPrice;
    }

    public Cart(int cardId, String foodId, String foodName, String imageUrlFood, int quantity, String size, double foodPrice) {
        this.cardId = cardId;
        this.foodId = foodId;
        this.foodName = foodName;
        this.imageUrlFood = imageUrlFood;
        this.quantity = quantity;
        this.size = size;
        this.foodPrice = foodPrice;
    }

    protected Cart(Parcel in) {
        cardId = in.readInt();
        foodId = in.readString();
        foodName = in.readString();
        imageUrlFood = in.readString();
        quantity = in.readInt();
        size = in.readString();
        foodPrice = in.readDouble();
    }

    public static final Creator<Cart> CREATOR = new Creator<Cart>() {
        @Override
        public Cart createFromParcel(Parcel in) {
            return new Cart(in);
        }

        @Override
        public Cart[] newArray(int size) {
            return new Cart[size];
        }
    };

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getImageUrlFood() {
        return imageUrlFood;
    }

    public void setImageUrlFood(String imageUrlFood) {
        this.imageUrlFood = imageUrlFood;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public double getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(double foodPrice) {
        this.foodPrice = foodPrice;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "foodId='" + foodId + '\'' +
                ", foodName='" + foodName + '\'' +
                ", imageUrlFood='" + imageUrlFood + '\'' +
                ", quantity=" + quantity +
                ", size='" + size + '\'' +
                ", foodPrice=" + foodPrice +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(cardId);
        parcel.writeString(foodId);
        parcel.writeString(foodName);
        parcel.writeString(imageUrlFood);
        parcel.writeInt(quantity);
        parcel.writeString(size);
        parcel.writeDouble(foodPrice);
    }
}