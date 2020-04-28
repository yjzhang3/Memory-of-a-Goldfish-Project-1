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

    // Used to return back to the previous activity
    getSupportActionBar().setTitle("MainMenu");
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//    Button multiplayer = (Button)findViewById(R.id.multi_setting);
//        multiplayer.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            Intent startIntent = new Intent(getApplicationContext(),MultiplayerSetting.class);
//            //show how to pass information to another activity
//            startActivity(startIntent);
//
//        }
//    });
    
    // Button leads to another activity where the user can choose their preferred level
    Button difficultyLevel = (Button)findViewById(R.id.difficultyLevel);
        difficultyLevel.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent startIntent = new Intent(getApplicationContext(), DifficultyLevel.class);
            startActivity(startIntent);
        }
    });
    
    // Button leads to another activity where the user can choose to turn sounds on or off
    Button soundSettings = (Button)findViewById(R.id.settings);
        soundSettings.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent startIntent = new Intent(getApplicationContext(),SoundSettings.class);
            startActivity(startIntent);
        }
    });
}
}



