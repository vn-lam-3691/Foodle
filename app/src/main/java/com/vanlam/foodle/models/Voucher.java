package com.vanlam.foodle.models;

import java.io.Serializable;
import java.util.Date;

public class Voucher implements Serializable {
    private String imageUrl, type;
    private String name, description;
    private String expiry;
    private double discount;

    public Voucher() {
    }

    public Voucher(String imageUrl, String name, String description) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.description = description;
    }

    public Voucher(String imageUrl, String type, String name, String description, String expiry, double discount) {
        this.imageUrl = imageUrl;
        this.type = type;
        this.name = name;
        this.description = description;
        this.expiry = expiry;
        this.discount = discount;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}
