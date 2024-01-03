package com.example.bookingappteam9.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.bookingappteam9.R;
import com.example.bookingappteam9.utils.PrefUtils;

import java.time.Instant;
import java.util.Timer;
import java.util.TimerTask;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView logoImage = findViewById(R.id.logoImage);
        Animation slideAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        logoImage.startAnimation(slideAnimation);

        //getSupportActionBar().hide();

        long SPLASH_TIMEOUT = 3000;
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
    }
}






