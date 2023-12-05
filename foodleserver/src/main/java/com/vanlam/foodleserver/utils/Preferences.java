package com.vanlam.foodleserver.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.vanlam.foodleserver.models.User;

public class Preferences {
    private static final String DATA_LOGIN = "STATUS_LOGIN";
    private static final String DATA_USER = "USER_DATA";

    private static SharedPreferences getSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setDataLogin(Context context, boolean status) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putBoolean(DATA_LOGIN, status);
        editor.apply();
    }

    public static boolean getDataLogin(Context context) {
        return getSharedPreferences(context).getBoolean(DATA_LOGIN, false);
    }

    public static void setDataUser(String userJson, Context context) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(DATA_USER, userJson);
        editor.apply();
    }

    public static User getDataUser(Context context) {
        String data = getSharedPreferences(context).getString(DATA_USER, "");
        return new Gson().fromJson(data, User.class);
    }

    public static void clearData(Context context) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.remove(DATA_LOGIN);
        editor.apply();
    }
}