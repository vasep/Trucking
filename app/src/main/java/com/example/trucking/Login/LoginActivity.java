package com.example.trucking.Login;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.example.trucking.Dashboard.DashboardTabActivity;
import com.example.trucking.HttpRequests.RetroClient;
import com.example.trucking.Model.User;
import com.example.trucking.R;
import com.example.trucking.Utils.Constants;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn_login;
    TextInputEditText emailText, passwordText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        btn_login = findViewById(R.id.btn_login);
        emailText = findViewById(R.id.enter_email);
        passwordText = findViewById(R.id.enter_password);

        btn_login.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == btn_login) {
            loginUser();
        }
    }

    private void loginUser() {
        String userEmail = emailText.getText().toString();
        String userPassword = passwordText.getText().toString();

        if (TextUtils.isEmpty(String.valueOf(userEmail))) {
            emailText.setError("Enter email field..");
            Toast.makeText(getApplicationContext(), "Please write your email...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(String.valueOf(userPassword))) {
            passwordText.setError("Enter password field.");
            Toast.makeText(getApplicationContext(), "Please write your password...", Toast.LENGTH_SHORT).show();
        } else {
            getUserData(userEmail, userPassword);
        }
    }

    private void getUserData(String userEmail, String userPassword) {
        User user = new User(userEmail, userPassword);
        Call<User> call = RetroClient.getApiService().generateTokenDriver(user);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {
                    Log.d("RetrofitA", "" + response.code());
                    if (response.code() == 500) {
                        emailText.setError("");
                        Toast.makeText(LoginActivity.this, "Wrong username or password.", Toast.LENGTH_SHORT).show();
                    }
                    return;
                }
                User driver = response.body();

                Log.d("RetrofitA", "" + driver.getToken());
                Constants.userToken = driver.getToken();
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);

                pref.edit().putString("userToken", driver.getToken()).apply(); // Storing string

                startActivity(new Intent(getApplicationContext(), DashboardTabActivity.class));
                LoginActivity.this.finish();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(LoginActivity.this,"Log In Failed",Toast.LENGTH_LONG).show();
            }
        });
    }
}