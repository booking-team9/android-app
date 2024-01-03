package com.example.bookingappteam9.services;

import com.example.bookingappteam9.model.Account;
import com.example.bookingappteam9.model.Login;
import com.example.bookingappteam9.model.PasswordChange;
import com.example.bookingappteam9.model.Register;
import com.example.bookingappteam9.model.Token;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AccountService {
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("accounts/all")
    Call<ArrayList<Account>> getAll();

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json",
    })
    @GET("accounts/{id}")
    Call<Account> getById(@Path("id") Long id);
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json",
    })
    @GET("account/{email}")
    Call<Account> getByEmail(@Path("email") String email);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @POST("passwordChange")
    Call<ResponseBody> changePassword(@Body PasswordChange passwordChange);


    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json",
            "Skip-auth:true"
    })
    @POST("login")
    Call<Token> login(@Body Login login);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json",
            "Skip-auth:true"
    })
    @POST("register")
    Call<Token> register(@Body Register register);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json",
            "Skip-auth:true"
    })
    @GET("register/confirm")
    Call<String> registerConfirm(@Query("token")String token);


/*

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @POST("accounts/")
    Call<Product> add(@Body Product product);
*/

/*    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @DELETE("product/{id}")
    Call<ResponseBody> deleteById(@Path("id") Long id);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @PUT("product/")
    Call<Product> edit(@Body Product product);*/
}
