package com.example.bookingappteam9.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Token implements Serializable {
    @SerializedName("token")
    private String token;

    public String getToken(){
        return token;
    }
}
