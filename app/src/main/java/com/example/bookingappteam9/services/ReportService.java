package com.example.bookingappteam9.services;

import com.example.bookingappteam9.model.Report;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ReportService {
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @POST("reports")
    Call<Report> saveNewReport(@Body Report report);
}
