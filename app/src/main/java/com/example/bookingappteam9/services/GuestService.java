package com.example.bookingappteam9.services;

import com.example.bookingappteam9.model.Guest;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface GuestService {
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("guests/all")
    Call<ArrayList<Guest>> getAll();

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json",
    })
    @GET("guests/{id}")
    Call<Guest> getById(@Path("id") Long id);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @PUT("guests/update")
    Call<Guest> edit(@Body Guest guest);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @DELETE("guests/{id}")
    Call<ResponseBody> deleteGuest(@Path("id") Long id);
}
