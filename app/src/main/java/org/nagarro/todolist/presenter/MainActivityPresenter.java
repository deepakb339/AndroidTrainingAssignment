package org.nagarro.todolist.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatDelegate;

import org.nagarro.todolist.activity.MainActivity;
import org.nagarro.todolist.utility.PreferenceHelper;

public class MainActivityPresenter implements MainActivityContract.Presenter{
    MainActivityContract.View view;
    Context context;

    public MainActivityPresenter(MainActivityContract.View view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void applyTheme() {
        PreferenceHelper.initialize(context);
        if (!PreferenceHelper.getInstance().getBoolean("theme")) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    @Override
    public void applyDarkTheme(boolean check) {
        if(check)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        PreferenceHelper.getInstance().setBoolean("theme", !check);
        context.startActivity(new Intent(context, MainActivity.class));
        ((Activity)context).finish();
    }

    @Override
    public void exitApp(MenuItem exitBtn) {
        exitBtn.setOnMenuItemClickListener(v -> {
            ((Activity)context).onBackPressed();
            return true;
        });
    }
}
