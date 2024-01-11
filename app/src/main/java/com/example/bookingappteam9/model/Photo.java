package com.example.bookingappteam9.model;

import android.graphics.Bitmap;
import android.net.Uri;

public class Photo {
    private Uri uri;
    private String name;
    private Bitmap image;

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Photo(){

    }

    public Photo(Uri uri, String name, Bitmap image) {
        this.uri = uri;
        this.name = name;
        this.image = image;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
