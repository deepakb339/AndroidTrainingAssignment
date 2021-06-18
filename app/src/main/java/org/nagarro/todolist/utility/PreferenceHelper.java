package org.nagarro.todolist.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferenceHelper {
    private static SharedPreferences pref;
    private static PreferenceHelper prefHelper;

    public PreferenceHelper(Context context) {
        pref = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void initialize(Context appContext) {
        if (appContext == null) {
            throw new NullPointerException("Provided application context is null");
        }
        if (prefHelper == null) {
            synchronized (PreferenceHelper.class) {
                if (prefHelper == null) {
                    prefHelper = new PreferenceHelper(appContext);
                }
            }
        }
    }

    public static PreferenceHelper getInstance() {
        if (prefHelper == null) {
            throw new IllegalStateException(
                    "SharedPrefsManager is not initialized, call initialize(applicationContext) " +
                            "static method first");
        }
        return prefHelper;
    }

    public Boolean getBoolean(String key) {
        return pref.getBoolean(key, false);
    }

    public void setBoolean(String key, Boolean isBoolean) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(key, isBoolean);
        editor.apply();

    }


}