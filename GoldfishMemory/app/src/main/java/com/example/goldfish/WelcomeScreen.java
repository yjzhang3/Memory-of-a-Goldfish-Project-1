package com.example.goldfish;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomeScreen extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 2000; //trying to find way to use load button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent startIntent = new Intent(WelcomeScreen.this, MainActivityHomeMenu.class);
                startActivity(startIntent);
                finish();

            }
        }, SPLASH_TIME_OUT);
    }


}

