package com.example.bookingappteam9.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.bookingappteam9.model.Role;

public class PrefUtils {

    public static final String PREFERENCE_NAME = "APP_PREFS";
    public static final String PREFS_LOGIN_USERNAME_KEY = "__USERNAME__";
    public static final String PREFS_LOGIN_JWT_TOKEN = "__TOKEN__";
    public static final String PREFS_LOGIN_ROLE = "__ROLE__";
    public static final String PREFS_LOGIN_USER_ID = "__ID__";
    public static final String PREFS_LOGIN_TOKEN_EXP = "__EXP__";



    /**
     * Called to save supplied value in shared preferences against given key.
     *
     * @param context Context of caller activity
     * @param key     Key of value to save against
     * @param value   Value to save
     */


    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public static void saveLoginInfo(Context context, String email, String token, String role, Long id, Long tokenExp) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREFS_LOGIN_USERNAME_KEY, email);
        editor.putString(PREFS_LOGIN_JWT_TOKEN, token);
        editor.putString(PREFS_LOGIN_ROLE, role);
        editor.putLong(PREFS_LOGIN_USER_ID, id);
        editor.putLong(PREFS_LOGIN_TOKEN_EXP, tokenExp);
        editor.apply();
    }

    public static UserInfo getUserInfo(Context context){
        SharedPreferences preferences = getSharedPreferences(context);
        String email = preferences.getString(PREFS_LOGIN_USERNAME_KEY, "");
        String token = preferences.getString(PREFS_LOGIN_JWT_TOKEN, "");
        String role = preferences.getString(PREFS_LOGIN_ROLE, "Guest");
        Long id = preferences.getLong(PREFS_LOGIN_USER_ID, 0L);
        Long tokenExp = preferences.getLong(PREFS_LOGIN_TOKEN_EXP, 0L);
        return new UserInfo(email, token, role, id, tokenExp);
    }

    public static void clearUserInfo(Context context){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.remove(PREFS_LOGIN_USERNAME_KEY);
        editor.remove(PREFS_LOGIN_ROLE);
        editor.remove(PREFS_LOGIN_USER_ID);
        editor.remove(PREFS_LOGIN_JWT_TOKEN);
        editor.remove(PREFS_LOGIN_TOKEN_EXP);
        editor.apply();
    }

    public static void saveToPrefs(Context context, String prefs_name, String key, String value) {
        SharedPreferences prefs = context.getSharedPreferences(prefs_name, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static class UserInfo {
        private String email;
        private String token;
        private Role role;
        private Long id;
        private Long tokenExpiration;

        UserInfo(String email, String token, String role, Long id, Long tokenExpiration) {
            this.email = email;
            this.id = id;
            this.token = token;
            this.role = Role.valueOf(role);
            this.tokenExpiration = tokenExpiration;
        }

        public String getEmail() {
            return email;
        }

        public String getToken() {
            return token;
        }

        public Role getRole() {
            return role;
        }

        public Long getId() {
            return id;
        }
        public Long getTokenExpiration(){return tokenExpiration;}
    }

    /**
     * Called to retrieve required value from shared preferences, identified by given key.
     * Default value will be returned of no value found or error occurred.
     *
     * @param context      Context of caller activity
     * @param key          Key to find value against
     * @param defaultValue Value to return if no data found against given key
     * @return Return the value found against given key, default if not found or any error occurs
     */
    public static String getFromPrefs(Context context, String prefs_name, String key, String defaultValue) {
        SharedPreferences sharedPrefs = context.getSharedPreferences(prefs_name, Context.MODE_PRIVATE);
        try {
            return sharedPrefs.getString(key, defaultValue);
        } catch (Exception e) {
            e.printStackTrace();
            return defaultValue;
        }
    }
}