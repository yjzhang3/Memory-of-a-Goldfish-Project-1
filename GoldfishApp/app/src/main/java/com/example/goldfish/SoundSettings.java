package com.example.goldfish;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

public class SoundSettings extends AppCompatActivity {

    private ToggleButton musicToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_settings);
        
        // Used to return back to previous activity
        getSupportActionBar().setTitle("Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        musicToggle = (ToggleButton) findViewById(R.id.musicToggle);
        musicToggle.setChecked(loadData());

        musicToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    // The toggle is enabled, music plays!
                    if (MainActivityHomeMenu.mediaPlayer == null) {
                        MainActivityHomeMenu.mediaPlayer = MediaPlayer.create(SoundSettings.this, R.raw.bensoundsummer);
                        // On default, the create the mediaplayer
                    }
                    MainActivityHomeMenu.mediaPlayer.setLooping(true);
                    MainActivityHomeMenu.mediaPlayer.start();
                }
                else {
                    // The toggle is disabled, music stops
                    MainActivityHomeMenu.mediaPlayer.stop();
                    MainActivityHomeMenu.mediaPlayer.release();
                    MainActivityHomeMenu.mediaPlayer = null;
                    // Release to stop holding on to mediaPlayer when this activity is not interacting with the user
                }
                saveData(isChecked);
            }
        });

    }
    // Data is saved in shared preferences as a boolean value. This value is used to check if music should be playing 
    // on app startup
    public void saveData(boolean isOnOff){
        SharedPreferences sharedPreferences = getSharedPreferences("musicToggle", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("musicToggle",isOnOff);
        editor.apply();
        // Toast to indicate the changes were saved
        Toast.makeText(this,"Game Setting saved!",Toast.LENGTH_SHORT).show();
    }
    
    // Data is loaded from last checked setting 
    public boolean loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("musicToggle", MODE_PRIVATE);
        return sharedPreferences.getBoolean("musicToggle", true);
    }

}

