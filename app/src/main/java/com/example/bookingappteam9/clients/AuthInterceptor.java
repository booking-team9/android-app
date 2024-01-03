package com.example.bookingappteam9.clients;

import android.content.Intent;

import androidx.annotation.NonNull;

import com.example.BookingApplication;
import com.example.bookingappteam9.activities.SplashActivity;
import com.example.bookingappteam9.utils.PrefUtils;

import java.io.IOException;
import java.time.Instant;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (chain.request().header("Skip-auth") != null){
            return chain.proceed(chain.request());
        }
        if (Instant.ofEpochSecond(PrefUtils.getUserInfo(BookingApplication.getAppContext()).getTokenExpiration()).isBefore(Instant.now())){
            // token expired
            PrefUtils.clearUserInfo(BookingApplication.getAppContext());
            Intent intent = new Intent(BookingApplication.getAppContext(), SplashActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            BookingApplication.getAppContext().startActivity(intent);

        }
        String token = PrefUtils.getUserInfo(BookingApplication.getAppContext()).getToken();

        Request request = newRequestWithAccessToken(chain.request(), token);
        return chain.proceed(request);
    }

    @NonNull
    private Request newRequestWithAccessToken(@NonNull Request request, @NonNull String accessToken) {
        return request.newBuilder()
                .header("Authorization", "Bearer " + accessToken)
                .build();
    }
}
