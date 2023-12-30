package com.example.bookingappteam9.services;

import com.example.bookingappteam9.model.Accommodation;
import com.example.bookingappteam9.model.AccommodationShort;
import com.example.bookingappteam9.model.HostAccommodation;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Path;

public interface AccommodationService {

    @GET("accommodations/host/{id}")
    Call<ArrayList<HostAccommodation>> getByHostId(@Path("id") Long id);

    @GET("accommodations/all")
    Call<ArrayList<Accommodation>> getAll();

    @GET("accommodations/all/short")
    Call<ArrayList<AccommodationShort>> getAllShort();

    @GET("accommodations/unapproved")
    Call<ArrayList<HostAccommodation>> getUnapproved();

    @PATCH("accommodations/{id}/approve")
    Call<HostAccommodation> approveAccommodation(@Path("id") Long id);
    @PATCH("accommodations/{id}/deny")
    Call<HostAccommodation> denyAccommodation(@Path("id") Long id);
}
