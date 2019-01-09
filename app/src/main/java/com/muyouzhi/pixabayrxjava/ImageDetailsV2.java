package com.muyouzhi.pixabayrxjava;

import android.os.Parcel;
import android.os.Parcelable;

public class ImageDetailsV2 implements Parcelable {
    public static String PARCELABLE_KEY = "image_parcelable_key";

    private String previewUrl;
    private String user;
    private String imageUrl;

    public static final Creator CREATOR = new Creator() {
        public ImageDetailsV2 createFromParcel(Parcel in) {
            return new ImageDetailsV2(in);
        }

        public ImageDetailsV2[] newArray(int size) {
            return new ImageDetailsV2[size];
        }
    };

    public ImageDetailsV2(Parcel in) {
        previewUrl = in.readString();
        user = in.readString();
        imageUrl = in.readString();
    }

    public ImageDetailsV2(String previewUrl, String user, String imageUrl) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(previewUrl);
        dest.writeString(user);
        dest.writeString(imageUrl);
    }
}
