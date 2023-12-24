package com.example.bookingappteam9.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;

import com.auth0.android.jwt.Claim;
import com.auth0.android.jwt.JWT;
import com.example.bookingappteam9.R;
import com.example.bookingappteam9.clients.ClientUtils;
import com.example.bookingappteam9.databinding.ActivityHomeScreenBinding;
import com.example.bookingappteam9.fragments.FragmentTransition;
import com.example.bookingappteam9.fragments.GuestProfileFragment;
import com.example.bookingappteam9.fragments.HostProfileFragment;
import com.example.bookingappteam9.fragments.accommodations.AccommodationsPageFragment;
import com.example.bookingappteam9.model.Account;
import com.example.bookingappteam9.model.Role;
import com.example.bookingappteam9.utils.PrefUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.concurrent.atomic.AtomicInteger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeScreen extends AppCompatActivity {
    private Account account;
    private ActivityHomeScreenBinding binding;
    private BottomNavigationView navigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authorize();


        binding = ActivityHomeScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        FragmentTransition.to(AccommodationsPageFragment.newInstance(), HomeScreen.this, false, R.id.navigationView);
        AtomicInteger currentItem = new AtomicInteger(R.id.home);

        navigation = binding.bottomNavigation;

        navigation.setOnItemSelectedListener(item -> {

             switch (item.getItemId()){
                 case R.id.home:
                     if (currentItem.get() == R.id.home)
                         return true;
                     currentItem.set(R.id.home);
                     FragmentTransition.to(AccommodationsPageFragment.newInstance(), HomeScreen.this, false, R.id.navigationView);
                     return true;
                 case R.id.profile:
                     if (currentItem.get() == R.id.profile)
                         return true;
                     currentItem.set(R.id.profile);
                     Bundle bundle = new Bundle();
                     bundle.putLong("id", account.getId());
                     if (account.getRole()== Role.Host){
                         HostProfileFragment hostProfileFragment = HostProfileFragment.newInstance(HomeScreen.this, "Host Profile");
                         hostProfileFragment.setArguments(bundle);
                         FragmentTransition.to(hostProfileFragment, HomeScreen.this, false, R.id.navigationView);
                     }
                     else if (account.getRole()==Role.Guest){
                         GuestProfileFragment guestProfileFragment = GuestProfileFragment.newInstance(HomeScreen.this, "Guest Profile");
                         guestProfileFragment.setArguments(bundle);
                         FragmentTransition.to(guestProfileFragment, HomeScreen.this, false, R.id.navigationView);
                     }

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

    @Override
    public void onBackPressed() {
        if (navigation.getSelectedItemId() == R.id.home) {
            super.onBackPressed();
        } else {
            navigation.setSelectedItemId(R.id.home);
        }
    }

    private void authorize(){
        String token = PrefUtils.getFromPrefs(getApplication(), "LoginPrefs", "token", "");
        JWT parsedJWT = new JWT(token);
        Claim emailData = parsedJWT.getClaim("sub");
        String email = emailData.asString();
        Log.d("EMAIL", email);
        Call<Account> call = ClientUtils.accountService.getByEmail(email);
        call.enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if (response.code() == 200){
                    account = response.body();
                    Log.d("ACCOUNT", account.toString());
                }else{
                    Log.d("REZ","Meesage recieved: "+response.code());
                }
            }
            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
            }
        });
    }

}