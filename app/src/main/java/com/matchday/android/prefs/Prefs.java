package com.matchday.android.prefs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.matchday.android.ui.MainActivity;

/**
 * Created by marina on 26.08.16.
 */
public class Prefs {

    private static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences("match_day_openning", Context.MODE_PRIVATE);
    }

    public static void setBooleanProperty(Context context, String key, Boolean value) {
        //Get Shared Preferences
        SharedPreferences preferences = getPreferences(context);

        // Set onboarding_complete to true
        preferences.edit()
                .putBoolean(key, value).apply();
    }

    public static Boolean getBooleanProperty(Context context, String key) {
        return getPreferences(context).getBoolean(key, false);
    }
}
