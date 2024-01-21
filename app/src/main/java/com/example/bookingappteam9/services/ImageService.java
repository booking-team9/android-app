package com.example.bookingappteam9.services;

import com.example.bookingappteam9.model.NewAccommodation;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ImageService {
    @GET("files/{name}")
    Call<byte[]> getPhoto(@Path("name") String name);

    @Multipart
    @POST("upload")
    Call<ResponseBody> uploadPhoto(@Part MultipartBody.Part[] file);

}
