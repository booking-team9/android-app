package com.example.bookingappteam9.utils;


import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.bookingappteam9.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class MessageService extends FirebaseMessagingService {


    private String[] permissions = {Manifest.permission.POST_NOTIFICATIONS};
    private NotificationManagerCompat notificationManagerCompat;
    private String topic;

    public MessageService() {
    }
    @Override
    public void onCreate() {
        notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());

        super.onCreate();

    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d("NOTIFICATION", "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d("NOTIFICATION", "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        remoteMessage.getData();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "STAYAT")
                .setSmallIcon(R.mipmap.ic_launcher_foreground)
                .setContentTitle("Stay At")
                .setContentText(remoteMessage.getData().get("message"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity)getApplicationContext(), permissions, 101);
        }
        else {
            notificationManagerCompat.notify(new Random().nextInt(), builder.build());
        }


        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}