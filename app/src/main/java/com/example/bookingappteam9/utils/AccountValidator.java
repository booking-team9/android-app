package com.example.bookingappteam9.utils;

import android.database.Observable;
import android.util.Log;

import com.example.bookingappteam9.clients.AccountService;
import com.example.bookingappteam9.clients.ClientUtils;
import com.example.bookingappteam9.model.Account;
import com.example.bookingappteam9.model.Login;
import com.example.bookingappteam9.model.Token;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountValidator {
    private String loggedUser;

    public Boolean validateCredentials(String username, String password){

        return false;
    }
}
