package com.example.bookingappteam9.activities;

import static android.app.PendingIntent.getActivity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import com.example.bookingappteam9.BuildConfig;
import com.example.bookingappteam9.R;
import com.example.bookingappteam9.databinding.ActivityHomeBinding;
import com.example.bookingappteam9.model.Role;
import com.example.bookingappteam9.utils.MessageService;
import com.example.bookingappteam9.utils.PrefUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.messaging.FirebaseMessaging;


public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;
    private BottomNavigationView navView;
    private boolean mKeyboardVisible;

    public BottomNavigationView getNavView(){
        return this.navView;
    }
    public static final String WEBSOCKET_ENDPOINT = "ws://" + BuildConfig.IP_ADDR + ":8080/" + "socket";

    // Declare the launcher at the top of your Activity/Fragment:
    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // FCM SDK (and your app) can post notifications.
                } else {
                    // TODO: Inform user that that your app will not show notifications.
                }
            });

    private void askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                    PackageManager.PERMISSION_GRANTED) {
                // FCM SDK (and your app) can post notifications.
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                // TODO: display an educational UI explaining to the user the features that will be enabled
                //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
                //       If the user selects "No thanks," allow the user to continue without notifications.
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
            }
        }
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
        PrefUtils.UserInfo info = PrefUtils.getUserInfo(getApplicationContext());
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
        askNotificationPermission();
        createNotificationChannel();
        startService(new Intent(this, MessageService.class));
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is not in the Support Library.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "STAYAT";
            String description = "STAYAT notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("STAYAT", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this.
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

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