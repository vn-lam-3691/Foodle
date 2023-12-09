package com.vanlam.foodleserver.models;

import java.io.Serializable;

public class Food implements Serializable {
    private String imageUrl;
    private String name;
    private String idCategory;
    private double price;
    private String description;

    public Food() {
    }

    public Food(String imageUrl, String name, String idCategory, double price, String description) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.idCategory = idCategory;
        this.price = price;
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imagePath) {
        this.imageUrl = imagePath;
    }

    public String getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(String idCategory) {
        this.idCategory = idCategory;
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

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
