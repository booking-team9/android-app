package com.example.bookingappteam9.clients;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ImageService {
    @GET("files/{name}")
    Call<byte[]> getPhoto(@Path("name") String name);
}
