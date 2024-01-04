package com.example.bookingappteam9.services;

import com.example.bookingappteam9.model.Reservation;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ReservationService {
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("reservations/requests/host/{id}")
    Call<ArrayList<Reservation>> getRequestsByHostId(@Path("id") Long id);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("reservations/requests/guest/{id}")
    Call<ArrayList<Reservation>> getRequestsByGuestId(@Path("id") Long id);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("reservations/decided/host/{id}")
    Call<ArrayList<Reservation>> getDecidedReservationsByHostId(@Path("id") Long id);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("reservations/decided/guest/{id}")
    Call<ArrayList<Reservation>> getDecidedReservationsByGuestId(@Path("id") Long id);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @PUT("reservations/{id}/approve")
    Call<Void> approveReservation(@Path("id") Long id);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @PUT("reservations/{id}/deny")
    Call<Void> denyReservation(@Path("id") Long id);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @PUT("reservations/{id}/cancel")
    Call<Void> cancelReservation(@Path("id") Long id);
}
