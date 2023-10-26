package com.vanlam.foodle.models;

import java.io.Serializable;

public class User implements Serializable {
    private int id, avatarPath;
    private String name, phoneNumber, address;
    private boolean isDefault;

    public User(int id, int avatarPath, String name, String phoneNumber, String address, boolean isDefault) {
        this.id = id;
        this.avatarPath = avatarPath;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.isDefault = isDefault;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAvatarPath(int avatarPath) {
        this.avatarPath = avatarPath;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public int getId() {
        return id;
    }

    public int getAvatarPath() {
        return avatarPath;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public boolean isDefault() {
        return isDefault;
    }
}
