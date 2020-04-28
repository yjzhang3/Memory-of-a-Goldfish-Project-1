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

        getSupportActionBar().setTitle("Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        musicToggle = (ToggleButton) findViewById(R.id.musicToggle);
        musicToggle.setChecked(loadData());

        musicToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    //the toggle is enabled, music plays!
                    if (MainActivityHomeMenu.mediaPlayer == null) {
                        MainActivityHomeMenu.mediaPlayer = MediaPlayer.create(SoundSettings.this, R.raw.bensoundsummer);
                    }
                    MainActivityHomeMenu.mediaPlayer.setLooping(true);
                    MainActivityHomeMenu.mediaPlayer.start();
                }
                else{
                    //the toggle is disabled, music stops
                    MainActivityHomeMenu.mediaPlayer.stop();
                    MainActivityHomeMenu.mediaPlayer.release();
                    MainActivityHomeMenu.mediaPlayer = null;
                }
                saveData(isChecked);
            }
        });

    }
    public void saveData(boolean isOnOff){
        SharedPreferences sharedPreferences = getSharedPreferences("musicToggle", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("musicToggle",isOnOff);
        editor.apply();
        Toast.makeText(this,"Game Setting saved!",Toast.LENGTH_SHORT).show();
    }

    public boolean loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("musicToggle", MODE_PRIVATE);
        return sharedPreferences.getBoolean("musicToggle", true);
    }

}

