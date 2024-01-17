package com.example.bookingappteam9.services;

import com.example.bookingappteam9.model.AdminReport;
import com.example.bookingappteam9.model.Notification;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface NotificationsService {

    @GET("notifications/user/{id}")
    Call<ArrayList<Notification>> getByUserId(@Path("id") Long id);

    @DELETE("notifications/{id}")
    Call<Void> dismissNotification(@Path("id") Long id);

    @GET("notifications/settings/get/{id}")
    Call<ArrayList<String>>getNotificationsSettings(@Path("id") Long id);

    @POST("notifications/settings/{id}")
    Call<Void> setNotificationsSettings(@Path("id") Long id, @Body ArrayList<String> settings);
}
