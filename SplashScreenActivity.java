package com.example.netmoboptimisation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final int SPLASS_SCREEN_TIMEOUT = 3000;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // Rediger vers la page principal "MainActivity" apres 3 secondes.
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Demarrer une page
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();

            }
        },SPLASS_SCREEN_TIMEOUT);
    }
}