package com.example;

import android.app.Application;
import android.content.Context;

public class BookingApplication extends Application {
    private static Context context;

    public void onCreate() {
        super.onCreate();
        BookingApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return BookingApplication.context;
    }
}
