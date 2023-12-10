package com.example.bookingappteam9.clients;

import com.example.bookingappteam9.model.Account;
import com.example.bookingappteam9.model.Login;
import com.example.bookingappteam9.model.Token;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

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
    @POST("login")
    Call<Token> login(@Body Login login);


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
