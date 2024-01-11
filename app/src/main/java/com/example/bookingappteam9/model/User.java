package com.example.bookingappteam9.model;

import androidx.annotation.NonNull;

public class User {
    private Long accountId;
    private String firstName;
    private String lastName;
    private String email;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User(Long accountId, String firstName, String lastName, String email) {
        this.accountId = accountId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
    public User(Long accountId){
        this.accountId = accountId;
    }

    @NonNull
    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
