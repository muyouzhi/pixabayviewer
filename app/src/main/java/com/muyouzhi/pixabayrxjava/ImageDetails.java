package com.muyouzhi.pixabayrxjava;

public class ImageDetails {
    public static String PARCELABLE_KEY = "image_parcelable_key";

    private String previewUrl;
    private String user;
    private String imageUrl;


    public ImageDetails(String previewUrl, String user, String imageUrl) {
        this.previewUrl = previewUrl;
        this.user= user;
        this.imageUrl = imageUrl;
    }

    public String getPreviewUrl(){
        return previewUrl;
    }

    public String getUser(){
        return user;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
