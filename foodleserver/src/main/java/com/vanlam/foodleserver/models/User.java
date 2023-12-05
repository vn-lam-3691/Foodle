package com.vanlam.foodleserver.models;

import java.io.Serializable;

public class User implements Serializable {
    private int avatarPath;
    private String name, phoneNumber, address;
    private String password;
    private boolean isDefault;
    private String role;

    public User() {
    }

    public User(int avatarPath, String name, String phoneNumber, String address, boolean isDefault) {
        this.avatarPath = avatarPath;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.isDefault = isDefault;
    }

    public User(int avatarPath, String name, String phoneNumber, String address, String password, boolean isDefault, String role) {
        this.avatarPath = avatarPath;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.password = password;
        this.isDefault = isDefault;
        this.role = role;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
