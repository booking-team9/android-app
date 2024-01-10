package com.example.bookingappteam9.activities;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import com.example.bookingappteam9.R;
import com.example.bookingappteam9.databinding.ActivityHomeBinding;
import com.example.bookingappteam9.model.Role;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;
    private BottomNavigationView navView;
    private boolean mKeyboardVisible;

    public BottomNavigationView getNavView(){
        return this.navView;
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //getSupportActionBar().hide();

        navView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
       /* AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_profile, R.id.navigation_host_properties)
                .build();*/
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
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
    @Override
    protected void onResume() {
        super.onResume();
        binding.getRoot().getViewTreeObserver()
                .addOnGlobalLayoutListener(mLayoutKeyboardVisibilityListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        binding.getRoot().getViewTreeObserver()
                .removeOnGlobalLayoutListener(mLayoutKeyboardVisibilityListener);
    }

    private final ViewTreeObserver.OnGlobalLayoutListener mLayoutKeyboardVisibilityListener =
            () -> {
                final Rect rectangle = new Rect();
                final View contentView = binding.getRoot();
                contentView.getWindowVisibleDisplayFrame(rectangle);
                int screenHeight = contentView.getRootView().getHeight();

                // r.bottom is the position above soft keypad or device button.
                // If keypad is shown, the rectangle.bottom is smaller than that before.
                int keypadHeight = screenHeight - rectangle.bottom;
                // 0.15 ratio is perhaps enough to determine keypad height.
                boolean isKeyboardNowVisible = keypadHeight > screenHeight * 0.15;

                if (mKeyboardVisible != isKeyboardNowVisible) {
                    if (isKeyboardNowVisible) {
                        onKeyboardShown();
                    } else {
                        onKeyboardHidden();
                    }
                }

                mKeyboardVisible = isKeyboardNowVisible;
            };

    private void onKeyboardShown() {
        navView.setVisibility(View.INVISIBLE);
    }

    private void onKeyboardHidden() {
        navView.setVisibility(View.VISIBLE);
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