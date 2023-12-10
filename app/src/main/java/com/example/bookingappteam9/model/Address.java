package com.example.bookingappteam9.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Address {
    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("street")
    @Expose
    private String street;
    @SerializedName("number")
    @Expose
    private String number;
    @SerializedName("city")
    @Expose
    private String city;

    public Address(String street, String number, String city) {
        this.street = street;
        this.number = number;
        this.city = city;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
