package com.example.mayank.Domain;

public class ReviewDomain {
    private String name;
    private String Description;
    private String PicUrl;
    private double rating;
    private int ItemId;

    public String getName() {
        return name;
    }

    public void setName(String Name1) {
        name = Name1;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPicUrl() {
        return PicUrl;
    }

    public void setPicUrl(String picUrl) {
        PicUrl = picUrl;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getItemId() {
        return ItemId;
    }

    public void setItemId(int itemId) {
        ItemId = itemId;
    }

    public ReviewDomain() {

    }
}
