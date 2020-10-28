package com.example.freightviewer.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.freightviewer.Dashboard.DashboardActivity;
import com.example.freightviewer.R;

public class MainLoginActivity extends AppCompatActivity implements View.OnClickListener{
    Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login2);

        btn_login = findViewById(R.id.btn_login);

        btn_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btn_login) {
            startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
        }
    }
}