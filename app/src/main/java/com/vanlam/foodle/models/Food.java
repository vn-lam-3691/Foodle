package com.vanlam.foodle.models;

import java.io.Serializable;

public class Food implements Serializable {
    private int imagePath;
    private String name;
    private double price;
    private String description;

    public Food() {
    }

    public Food(int imagePath, String name, double price, String description) {
        this.imagePath = imagePath;
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public int getImagePath() {
        return imagePath;
    }

    public void setImagePath(int imagePath) {
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public double setPrice(double price) {
        return this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
