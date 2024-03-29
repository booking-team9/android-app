package com.example.bookingappteam9.clients;

import com.example.bookingappteam9.BuildConfig;
import com.example.bookingappteam9.services.AccommodationService;
import com.example.bookingappteam9.services.AccountService;
import com.example.bookingappteam9.services.GuestService;
import com.example.bookingappteam9.services.HostService;
import com.example.bookingappteam9.services.ImageService;
import com.example.bookingappteam9.services.NotificationsService;
import com.example.bookingappteam9.services.ReportService;
import com.example.bookingappteam9.services.ReservationService;
import com.example.bookingappteam9.services.ReviewService;
import com.example.bookingappteam9.utils.LocalDateSerializer;
import com.example.bookingappteam9.utils.LocalDateTimeDeserializer;
import com.example.bookingappteam9.utils.LocalDateTimeSerializer;
import com.google.gson.GsonBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientUtils {
    public static final String SERVICE_API_PATH = "http://" + BuildConfig.IP_ADDR + ":8080/api/";
    public static final String SERVICE_PATH_RAW = "http://" + BuildConfig.IP_ADDR + ":8080/";

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
            .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer()).registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer()).registerTypeAdapter(LocalDate.class, new LocalDateSerializer()).setLenient().create()))
            .client(test())
            .build();

    public static Retrofit rawRetrofit = new Retrofit.Builder()
            .baseUrl(SERVICE_PATH_RAW)
            .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer()).registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer()).registerTypeAdapter(LocalDate.class, new LocalDateSerializer()).setLenient().create()))
            .client(test())
            .build();
    public static String getPhotoPath(String photo){
        return SERVICE_PATH_RAW + "files/"+photo;
    }
    public static AccountService accountService = retrofit.create(AccountService.class);
    public static HostService hostService = retrofit.create(HostService.class);
    public static GuestService guestService = retrofit.create(GuestService.class);
    public static AccommodationService accommodationService = retrofit.create(AccommodationService.class);
    public static ImageService imageService = rawRetrofit.create(ImageService.class);
    public static ReservationService reservationService = retrofit.create(ReservationService.class);
    public static ReviewService reviewService = retrofit.create(ReviewService.class);
    public static ReportService reportService = retrofit.create(ReportService.class);
    public static NotificationsService notificationsService = retrofit.create(NotificationsService.class);


}
