package com.vanlam.foodle.models;

public class ViewPagerModel {
    private int imgPath;
    private String title, description;

    public ViewPagerModel() {
    }

    public ViewPagerModel(int imgPath, String title, String description) {
        this.imgPath = imgPath;
        this.title = title;
        this.description = description;
    }

    public int getImgPath() {
        return imgPath;
    }

    public void setImgPath(int imgPath) {
        this.imgPath = imgPath;
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
}
