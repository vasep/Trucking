package com.example.trucking.Dashboard;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.example.trucking.Login.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;
import com.example.trucking.R;


public class DashboardTabActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_tab);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Loads Dashboard");
        setSupportActionBar(toolbar);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListerner);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CompletedLoadsFragment()).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Logout").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(DashboardTabActivity.this)
                        .setMessage("Are you sure you want to Log Out?")
                        .setPositiveButton("Log Out", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                                pref.edit().remove("userToken").apply();
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                                finish();
                            }
                        })
                        .setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        });
                alertDialog.create();
                alertDialog.show();

                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListerner =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.completed_menu_item:
                            selectedFragment = new CompletedLoadsFragment();
                            break;
                        case R.id.upcoming_menu_item:
                            selectedFragment = new UpcomingLoadsFragment();
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    return true;
                }
            };

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

    }
}