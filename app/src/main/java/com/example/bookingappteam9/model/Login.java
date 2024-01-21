package com.example.bookingappteam9.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Login implements Serializable {
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    public Login(String email, String password){
        this.email = email;
        this.password = password;
    }
}
