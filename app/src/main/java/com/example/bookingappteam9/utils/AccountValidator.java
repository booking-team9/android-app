package com.example.bookingappteam9.utils;

public class AccountValidator {
    public Boolean ValidateUserName(String username){
        if (username.equals("user")){
            return true;
        }
        return false;
    }

    public Boolean ValidatePassword(String password){
        if (password.equals("123")){
            return true;
        }
        return false;
    }
}
