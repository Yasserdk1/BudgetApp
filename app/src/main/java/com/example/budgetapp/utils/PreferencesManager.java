package com.example.budgetapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import com.example.budgetapp.R;

public class PreferencesManager {

    private static final String PREFS_NAME = "budget_app_prefs";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_REMEMBER_ME = "remember_me";
    private static final String KEY_THEME = "theme";
    private static final String KEY_MONTHLY_BUDGET = "monthly_budget";

    private static PreferencesManager instance;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    private PreferencesManager(Context context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public static synchronized PreferencesManager getInstance(Context context) {
        if (instance == null) {
            instance = new PreferencesManager(context.getApplicationContext());
        }
        return instance;
    }

    public void setTheme(String theme) {
        editor.putString(KEY_THEME, theme).apply();
    }

    public String getTheme() {
        return prefs.getString(KEY_THEME, "light");
    }

    public void applyTheme(AppCompatActivity activity) {
        if (getTheme().equals("dark")) {
            activity.setTheme(R.style.AppTheme_Dark);
        } else {
            activity.setTheme(R.style.AppTheme_Light);
        }
    }

    public void saveLoginInfo(String username, boolean rememberMe) {
        editor.putString(KEY_USERNAME, username)
                .putBoolean(KEY_REMEMBER_ME, rememberMe)
                .apply();
    }

    public String getSavedUsername() {
        return prefs.getString(KEY_USERNAME, "");
    }

    public boolean shouldRememberMe() {
        return prefs.getBoolean(KEY_REMEMBER_ME, false);
    }

    public void setMonthlyBudget(float budget) {
        editor.putFloat(KEY_MONTHLY_BUDGET, budget).apply();
    }

    public float getMonthlyBudget() {
        return prefs.getFloat(KEY_MONTHLY_BUDGET, 1000f);
    }
}