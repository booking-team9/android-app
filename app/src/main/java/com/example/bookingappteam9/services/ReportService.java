package com.example.bookingappteam9.services;

import com.example.bookingappteam9.model.AdminReport;
import com.example.bookingappteam9.model.Report;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ReportService {
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("reports/all")
    Call<ArrayList<AdminReport>> getAll();
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @POST("reports")
    Call<Report> saveNewReport(@Body Report report);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @DELETE("reports/{id}")
    Call<Void> deleteReport(@Path("id") Long id);
}
