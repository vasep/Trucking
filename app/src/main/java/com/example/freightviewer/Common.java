package com.example.freightviewer;

import android.content.Context;
import android.location.Location;
import android.preference.PreferenceManager;

import java.text.DateFormat;
import java.util.Date;

public class Common {
    public static final String KEY_REQUESTING_LOCATION_UPDATES = "LocationUpdatesEnable";

    public static String getLocationText(Location location){
        return location == null ? "Unknown Location" : new StringBuilder()
                .append(location.getLatitude())
                .append("/")
                .append(location.getLongitude())
                .toString();
    }

    public static CharSequence getLocationTitle(LocationBackgroundService myBackgroundService) {
        return String.format("Location Update : "+ DateFormat.getTimeInstance().format(new Date()));
    }

    public static void setRequestingLocationUpdates(Context context, boolean value) {
        PreferenceManager.
                getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(KEY_REQUESTING_LOCATION_UPDATES, value)
                .apply();
    }

    public static boolean requestingLocationUpdates(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(KEY_REQUESTING_LOCATION_UPDATES,false);
    }
}
