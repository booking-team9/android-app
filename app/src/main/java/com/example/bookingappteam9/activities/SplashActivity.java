package com.example.bookingappteam9.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bookingappteam9.R;
import com.example.bookingappteam9.utils.PrefUtils;

import java.time.Instant;
import java.util.Timer;
import java.util.TimerTask;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {
    private TextView internetInfo;
    private Button connect;
    @Override
    protected void onResume() {
        super.onResume();
        connect.setVisibility(View.GONE);
        internetInfo.setVisibility(View.GONE);
        if (checkInternetConnection()){
            long SPLASH_TIMEOUT = 2000;
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    if (!PrefUtils.getUserInfo(getApplicationContext()).getEmail().isEmpty()){
                        Long tokenExp = PrefUtils.getUserInfo(getApplicationContext()).getTokenExpiration();
                        if (Instant.ofEpochSecond(tokenExp).isAfter(Instant.now())){
                            Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                            intent.putExtra("role", PrefUtils.getUserInfo(getApplicationContext()).getRole().toString());
                            startActivity(intent);
                            finish();
                            return;
                        }
                    }

                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, SPLASH_TIMEOUT);
        }else{
            Intent wiFiintent = new Intent(Settings.ACTION_WIFI_SETTINGS);

            internetInfo.setVisibility(View.VISIBLE);
            connect.setVisibility(View.VISIBLE);
            connect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(wiFiintent);
                }
            });


        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
         internetInfo = findViewById(R.id.connection_internet_failed);
         connect = findViewById(R.id.connect_to_internet);

        ImageView logoImage = findViewById(R.id.logoImage);
        Animation slideAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        logoImage.startAnimation(slideAnimation);

        if (checkInternetConnection()){
            long SPLASH_TIMEOUT = 2000;
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    if (!PrefUtils.getUserInfo(getApplicationContext()).getEmail().isEmpty()){
                        Long tokenExp = PrefUtils.getUserInfo(getApplicationContext()).getTokenExpiration();
                        if (Instant.ofEpochSecond(tokenExp).isAfter(Instant.now())){
                            Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                            intent.putExtra("role", PrefUtils.getUserInfo(getApplicationContext()).getRole().toString());
                            startActivity(intent);
                            finish();
                            return;
                        }
                    }

                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, SPLASH_TIMEOUT);
        }else{
            Intent wiFiintent = new Intent(Settings.ACTION_WIFI_SETTINGS);
            TextView internetInfo = findViewById(R.id.connection_internet_failed);
            Button connect = findViewById(R.id.connect_to_internet);
            internetInfo.setVisibility(View.VISIBLE);
            connect.setVisibility(View.VISIBLE);
            connect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(wiFiintent);
                }
            });


        }


    }

    private boolean checkInternetConnection(){
        boolean connected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
            return connected;
        } catch (Exception e) {
            Log.e("Connectivity Exception", e.getMessage());
        }
        return false;
    }
}






