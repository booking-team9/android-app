package com.example.bookingappteam9.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;

import com.example.bookingappteam9.R;
import com.example.bookingappteam9.databinding.ActivityHomeScreenBinding;
import com.example.bookingappteam9.fragments.BlankFragment;
import com.example.bookingappteam9.fragments.FragmentTransition;
import com.example.bookingappteam9.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.concurrent.atomic.AtomicInteger;

public class HomeScreen extends AppCompatActivity {


    private ActivityHomeScreenBinding binding;
    private BottomNavigationView navigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        FragmentTransition.to(BlankFragment.newInstance("Home fragment", "Ovo je home!"), HomeScreen.this, false, R.id.navigationView);
        AtomicInteger currentItem = new AtomicInteger(R.id.home);

        navigation = binding.bottomNavigation;
        navigation.setOnItemSelectedListener(item -> {

             switch (item.getItemId()){
                 case R.id.home:
                     if (currentItem.get() == R.id.home)
                         return true;
                     currentItem.set(R.id.home);
                     FragmentTransition.to(BlankFragment.newInstance("Home fragment", "Ovo je home!"), HomeScreen.this, false, R.id.navigationView);
                     return true;
                 case R.id.profile:
                     if (currentItem.get() == R.id.profile)
                         return true;
                     currentItem.set(R.id.profile);
                     FragmentTransition.to(ProfileFragment.newInstance("Profile fragment", "Profile"), HomeScreen.this, false, R.id.navigationView);
                     return true;
             }
            return false;
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // menu.clear();
        // koristimo ako je nasa arhitekrura takva da imamo jednu aktivnost
        // i vise fragmentaa gde svaki od njih ima svoj menu unutar toolbar-a

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.bottom_navigation_menu, menu);
        return true;
    }
}