package com.example.bookingappteam9.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.bookingappteam9.R;
import com.example.bookingappteam9.clients.ClientUtils;
import com.example.bookingappteam9.utils.PrefUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmedScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmed_screen);
        //getSupportActionBar().hide();
        // ATTENTION: This was auto-generated to handle app links.
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();
        String query = appLinkData.getEncodedQuery();
        LinearLayout success = findViewById(R.id.success_layout);
        LinearLayout failure = findViewById(R.id.failure_layout);
        Button signIn = findViewById(R.id.loginConfirm);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfirmedScreen.this, LoginScreen.class);
                startActivity(intent);
            }
        });
        if (query != null){
            String token = query.substring(query.indexOf('=')+1);
            Call<String> call = ClientUtils.accountService.registerConfirm(token);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.code() == 200){
                        Log.d("Confirm","Meesage recieved");
                        success.setVisibility(View.VISIBLE);

                    }else{
                        Log.d("Login","Meesage recieved: "+response.code());
                        failure.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(ConfirmedScreen.this, "Server error!", Toast.LENGTH_SHORT).show();
                    Log.d("Confirm", t.getMessage() != null?t.getMessage():"error");
                }
            });
        }

    }
}