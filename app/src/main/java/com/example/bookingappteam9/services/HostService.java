package com.example.bookingappteam9.services;

import com.example.bookingappteam9.model.Host;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface HostService {
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("hosts/all")
    Call<ArrayList<Host>> getAll();

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json",
    })
    @GET("hosts/{id}")
    Call<Host> getById(@Path("id") Long id);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @PUT("hosts/update")
    Call<Host> edit(@Body Host host);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @DELETE("hosts/{id}")
    Call<ResponseBody> deleteHost(@Path("id") Long id);

}
