package com.example.bookingappteam9.services;

import com.example.bookingappteam9.model.AdminReport;
import com.example.bookingappteam9.model.Notification;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface NotificationsService {

    @GET("notifications/user/{id}")
    Call<ArrayList<Notification>> getByUserId(@Path("id") Long id);

    @DELETE("notifications/{id}")
    Call<Void> dismissNotification(@Path("id") Long id);
}
