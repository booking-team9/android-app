package com.example.bookingappteam9.services;

import com.example.bookingappteam9.model.Login;
import com.example.bookingappteam9.model.Review;
import com.example.bookingappteam9.model.Token;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ReviewService {

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @POST("reviews")
    Call<Review> saveNewReview(@Body Review review);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("reviews/findhost/{hostId}/{authorId}")
    Call<Review> findHostReview(@Path("hostId") Long hostId, @Path("authorId") Long authorId);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("reviews/findacc/{accId}/{authorId}")
    Call<Review> findAccommodationReview(@Path("accId") Long accId, @Path("authorId") Long authorId);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @DELETE("reviews/{id}")
    Call<Void> deleteReview(@Path("id") Long reviewId);
}
