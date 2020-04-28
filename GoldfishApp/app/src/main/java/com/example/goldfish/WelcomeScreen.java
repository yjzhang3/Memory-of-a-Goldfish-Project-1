package com.example.goldfish;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomeScreen extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 2000; // Delay the amount of time the screen shows for in ms
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent startIntent = new Intent(WelcomeScreen.this, MainActivityHomeMenu.class);
                // Once welcome screen finishes, it will go on to main activity
                startActivity(startIntent);
                finish();

            }
        }, SPLASH_TIME_OUT);
    }


}

