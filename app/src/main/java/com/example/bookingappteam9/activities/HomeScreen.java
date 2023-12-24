package com.example.bookingappteam9.activities;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.bookingappteam9.R;
import com.example.bookingappteam9.databinding.ActivityHomeScreenBinding;
import com.example.bookingappteam9.fragments.BlankFragment;
import com.example.bookingappteam9.fragments.EditProfileFragment;
import com.example.bookingappteam9.fragments.FragmentTransition;
import com.example.bookingappteam9.fragments.ProfileFragment;
import com.example.bookingappteam9.fragments.accommodations.AccommodationsPageFragment;
import com.example.bookingappteam9.model.Role;
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

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_profile, R.id.navigation_host_properties)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        Intent intent = getIntent();
        Role role = Role.valueOf(intent.getStringExtra("role"));
        switch (role) {
            case Guest:
                Toast.makeText(getApplicationContext(), role.toString(), Toast.LENGTH_SHORT).show();
                navView.inflateMenu(R.menu.bottom_nav_menu_guest);
                break;
            case Host:
                Toast.makeText(getApplicationContext(), role.toString(), Toast.LENGTH_SHORT).show();
                navView.inflateMenu(R.menu.bottom_nav_menu_host);
                break;
            case Admin:
                Toast.makeText(getApplicationContext(), role.toString(), Toast.LENGTH_SHORT).show();
                navView.inflateMenu(R.menu.bottom_nav_menu_admin);
                break;
            default:
                break;
        }


    }

/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // menu.clear();
        // koristimo ako je nasa arhitekrura takva da imamo jednu aktivnost
        // i vise fragmentaa gde svaki od njih ima svoj menu unutar toolbar-a

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.bottom_nav_menu_host, menu);
        return true;
    }*/

/*    @Override
    public void onBackPressed() {
        if (navigation.getSelectedItemId() == R.id.home) {
            super.onBackPressed();
        } else {
            navigation.setSelectedItemId(R.id.home);
        }
    }*/

}