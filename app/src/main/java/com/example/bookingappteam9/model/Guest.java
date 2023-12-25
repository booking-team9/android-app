package com.example.bookingappteam9.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Guest extends Account{
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("address")
    @Expose
    private Address address;
    @SerializedName("timesCanceled")
    @Expose
    private int timesCanceled;


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
    public int getTimesCanceled() {
        return timesCanceled;
    }

    public void setTimesCanceled(int timesCanceled) {
        this.timesCanceled = timesCanceled;
    }
}
