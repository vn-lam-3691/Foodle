package com.vanlam.foodle.models;

import java.io.Serializable;

public class Cart implements Serializable {
    private int imagePath, quantity;
    private String name, size;
    private double price;
    private boolean isSelected;

    public Cart() {
    }

    public Cart(int imagePath, int quantity, String name, String size, double price, boolean isSelected) {
        this.imagePath = imagePath;
        this.quantity = quantity;
        this.name = name;
        this.size = size;
        this.price = price;
        this.isSelected = isSelected;
    }

    public int getImagePath() {
        return imagePath;
    }

    public void setImagePath(int imagePath) {
        this.imagePath = imagePath;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
