package com.example.villageconnect;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class NotificationHelper {

    private static final String PREF_NAME = "VillageConnectNotifications";
    private static final String KEY_NOTIFICATIONS = "notifications";

    public static void addNotification(Context context, String title, String message) {

        SharedPreferences pref =
                context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        Set<String> oldNotifications =
                pref.getStringSet(KEY_NOTIFICATIONS, new HashSet<>());

        Set<String> notifications = new HashSet<>(oldNotifications);

        notifications.add(title + "||" + message);

        pref.edit()
                .putStringSet(KEY_NOTIFICATIONS, notifications)
                .apply();
    }

    public static ArrayList<String> getNotifications(Context context) {

        SharedPreferences pref =
                context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        Set<String> saved =
                pref.getStringSet(KEY_NOTIFICATIONS, new HashSet<>());

        return new ArrayList<>(saved);
    }

    public static void clearNotifications(Context context) {

        SharedPreferences pref =
                context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        pref.edit()
                .remove(KEY_NOTIFICATIONS)
                .apply();
    }
}