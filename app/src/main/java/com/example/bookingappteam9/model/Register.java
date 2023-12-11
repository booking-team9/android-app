package com.example.bookingappteam9.model;

public class Register {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private Role role;
    private Address address;

    public Register(String email, String password, String fistName, String lastName, String phone, Role role, Address address) {
        this.email = email;
        this.password = password;
        this.firstName = fistName;
        this.lastName = lastName;
        this.phone = phone;
        this.role = role;
        this.address = address;
    }
}
