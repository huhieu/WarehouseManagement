package com.example.warehousemanagement;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.window.SplashScreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(() -> {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }, 2000);
    }
}