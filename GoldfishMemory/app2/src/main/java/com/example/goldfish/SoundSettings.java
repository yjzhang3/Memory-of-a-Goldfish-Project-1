package com.example.goldfish;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

public class SoundSettings extends AppCompatActivity {

    private ToggleButton musicToggle;
    private ToggleButton audioToggle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_settings);

        getSupportActionBar().setTitle("Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        musicToggle = (ToggleButton) findViewById(R.id.musicToggle);
        audioToggle = (ToggleButton) findViewById(R.id.audioToggle);

        musicToggle.setChecked(loadData());
        musicToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    // the toggle is enabled
                    // music plays :)
                    //MediaPlayer bensoundsummer = MediaPlayer.create(SoundSettings.this,R.raw.bensoundsummer);
                    //bensoundsummer.start();
                    // works on this page?
                }
                else{
                    // the toggle is disabled
                    //MediaPlayer bensoundsummer = MediaPlayer.create(SoundSettings.this,R.raw.bensoundsummer);
                    //bensoundsummer.stop();
                }
                saveData(isChecked);
            }
        });

        audioToggle.setChecked(loadData());
        audioToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    // the toggle is enabled
                    // audio on!
                }
                else{
                    // the toggle is disabled
                }
                saveData(isChecked);
            }
        });

    }
    public void saveData(boolean isOnOff){
        SharedPreferences sharedPreferences = getSharedPreferences("musicToggle", MODE_PRIVATE);
        sharedPreferences.edit().putBoolean("musicToggle",isOnOff).apply();
    }

    public boolean loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("musicToggle", MODE_PRIVATE);
        return sharedPreferences.getBoolean("musicToggle", true);
    }

}

