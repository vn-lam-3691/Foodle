package com.vanlam.foodle.models;

import java.io.Serializable;

public class Cart implements Serializable {
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
}
