package com.example.bookingappteam9.clients;

import com.example.bookingappteam9.model.AccommodationShort;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Path;

public interface AccommodationService {

    @GET("accommodations/host/{id}")
    Call<ArrayList<AccommodationShort>> getByHostId(@Path("id") Long id);

    @GET("accommodations/unapproved")
    Call<ArrayList<AccommodationShort>> getUnapproved();

    @PATCH("accommodations/{id}/approve")
    Call<AccommodationShort> approveAccommodation(@Path("id") Long id);
    @PATCH("accommodations/{id}/deny")
    Call<AccommodationShort> denyAccommodation(@Path("id") Long id);
}
