package com.example.mayank.Domain;

public class SliderItems {
    private String url;

    // Required default constructor for Firebase
    public SliderItems() {
        // Default constructor required for Firebase
    }

    public SliderItems(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
