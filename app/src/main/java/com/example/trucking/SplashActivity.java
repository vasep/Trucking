package com.example.trucking;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import com.example.trucking.Dashboard.DashboardTabActivity;
import com.example.trucking.Login.LoginActivity;
import com.example.trucking.Utils.Constants;

public class SplashActivity extends Activity {
   private final int SPLASH_DISPLAY_LENGTH = 1000;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /** Duration of wait **/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        textView= findViewById(R.id.text);
        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                Constants.userToken = pref.getString("userToken", "no data found");
                if (!Constants.userToken.equals("no data found")) {
                    startActivity(new Intent(getApplicationContext(), DashboardTabActivity.class));
                } else {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}