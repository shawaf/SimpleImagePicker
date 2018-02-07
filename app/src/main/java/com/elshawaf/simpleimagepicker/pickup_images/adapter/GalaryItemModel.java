package com.elshawaf.simpleimagepicker.pickup_images.adapter;

/**
 * Created by mohamedelshawaf on 9/14/17.
 */

public class GalaryItemModel {

    private String imageUrl;


    public GalaryItemModel() {
    }

    public GalaryItemModel(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
