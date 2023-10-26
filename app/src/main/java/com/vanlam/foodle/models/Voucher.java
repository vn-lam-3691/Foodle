package com.vanlam.foodle.models;

import java.io.Serializable;
import java.util.Date;

public class Voucher implements Serializable {
    private int imagePath, type;
    private String title, description;
    private Date expiry;

    public Voucher() {
    }

    public Voucher(int imagePath, String title, String description) {
        this.imagePath = imagePath;
        this.title = title;
        this.description = description;
    }

    public Voucher(int imagePath, String title, String description, Date expiry, int type) {
        this.imagePath = imagePath;
        this.title = title;
        this.description = description;
        this.expiry = expiry;
        this.type = type;
    }

    public int getImagePath() {
        return imagePath;
    }

    public void setImagePath(int imagePath) {
        this.imagePath = imagePath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getExpiry() {
        return expiry;
    }

    public void setExpiry(Date expiry) {
        this.expiry = expiry;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
