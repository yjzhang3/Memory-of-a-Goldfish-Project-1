package com.example.goldfish;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Settings extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_settings);

    getSupportActionBar().setTitle("MainMenu");
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    Button multiplayer = (Button)findViewById(R.id.multi_setting);
        multiplayer.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent startIntent = new Intent(getApplicationContext(),MultiplayerSetting.class);
            //show how to pass information to another activity
            startActivity(startIntent);

        }
    });

    Button difficultyLevel = (Button)findViewById(R.id.difficultyLevel);
        difficultyLevel.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent startIntent = new Intent(getApplicationContext(), DifficultyLevel.class);
            // show how to pass information to another activity
            startActivity(startIntent);
        }
    });

    Button soundSettings = (Button)findViewById(R.id.settings);
        soundSettings.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent startIntent = new Intent(getApplicationContext(),SoundSettings.class);
            // show how to pass information to another activity
            startActivity(startIntent);
        }
    });
}
}



