package org.nagarro.todolist.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.switchmaterial.SwitchMaterial;

import org.nagarro.todolist.R;
import org.nagarro.todolist.databinding.ActivityMainBinding;
import org.nagarro.todolist.presenter.MainActivityContract;
import org.nagarro.todolist.presenter.MainActivityPresenter;

public class MainActivity extends AppCompatActivity implements MainActivityContract.View {

    private AppBarConfiguration mAppBarConfiguration;
    MainActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        presenter = new MainActivityPresenter(this,this);
        presenter.applyTheme();
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarMain.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_todolist, R.id.nav_about)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        onNavigationMenuButtonClicked(navigationView);
    }

    private void onNavigationMenuButtonClicked(NavigationView navigationView) {
        MenuItem menuItem = navigationView.getMenu().findItem(R.id.darkmode_btn);
        SwitchMaterial darkThemeSwitch = menuItem.getActionView().findViewById(R.id.drawer_switch);
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            darkThemeSwitch.setChecked(true);
        }
        darkThemeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> presenter.applyDarkTheme(isChecked));
        MenuItem exitBtn = navigationView.getMenu().findItem(R.id.exit_btn);
        presenter.exitApp(exitBtn);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.darkmode) {
            presenter.applyDarkTheme(true);
            return true;
        }
        else if(id == R.id.lightmode) {
            presenter.applyDarkTheme(false);
            return true;
        }
        else
            return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onError() {

    }
}