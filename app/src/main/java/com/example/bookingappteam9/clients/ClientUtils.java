package com.example.bookingappteam9.clients;

import androidx.annotation.NonNull;

import com.example.bookingappteam9.BuildConfig;
import com.example.bookingappteam9.utils.PrefUtils;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientUtils {
    public static final String SERVICE_API_PATH = "http://" + BuildConfig.IP_ADDR + ":8080/api/";

    public static OkHttpClient test() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        AuthInterceptor authInterceptor = new AuthInterceptor();

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .addInterceptor(authInterceptor)
                .addInterceptor(interceptor).build();

        return client;
    }


    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(SERVICE_API_PATH)
            .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
            .client(test())
            .build();

    public static AccountService accountService = retrofit.create(AccountService.class);
    public static HostService hostService = retrofit.create(HostService.class);
    public static GuestService guestService = retrofit.create(GuestService.class);
    public static AccommodationService accommodationService = retrofit.create(AccommodationService.class);
    public static ImageService imageService = retrofit.create(ImageService.class);


}
