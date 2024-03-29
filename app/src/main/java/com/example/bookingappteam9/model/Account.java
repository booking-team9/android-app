package com.example.bookingappteam9.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Account implements Serializable {
    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("isBlocked")
    @Expose
    private boolean isBlocked;

    @SerializedName("role")
    @Expose
    private Role role;

    @SerializedName("isActivated")
    @Expose
    private boolean isActivated;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getBlocked() {
        return isBlocked;
    }

    public void setBlocked(Boolean blocked) {
        isBlocked = blocked;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Boolean getActivated() {
        return isActivated;
    }

    public void setActivated(Boolean activated) {
        isActivated = activated;
    }
}
