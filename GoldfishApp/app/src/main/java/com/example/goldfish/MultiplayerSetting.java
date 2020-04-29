package com.example.goldfish;

// this class was originally designed so that the user could choose single
// player or multiplayer. But we decided to just do one type of gameplay
// because the size of the screen doesn't allow more cards than 16.

// this class is currently not in use but could be beneficial for the future.
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MultiplayerSetting extends AppCompatActivity {
    private RadioGroup radioGroupMultiplayer;
    private RadioButton radioOnePlayer;
    private RadioButton radioTwoPlayer;

    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multiplayer_setting);

        getSupportActionBar().setTitle("Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.radioGroupMultiplayer = (RadioGroup)this.findViewById(R.id.radioGroupMultiplayer);
        this.radioOnePlayer = (RadioButton) this.findViewById(R.id.radioOnePlayer);
        this.radioTwoPlayer = (RadioButton) this.findViewById(R.id.radioTwoPlayer);

        this.saveButton = (Button) findViewById(R.id.saveButton);

        this.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MultiplayerSetting.this.saveData(view);
            }
        });
        this.loadData();
    }

    private void loadData(){
        SharedPreferences sharedPreferences = this.getSharedPreferences("multiData",MODE_PRIVATE);

        if(sharedPreferences!=null){
            int checkedRadioButtonId = sharedPreferences.getInt("checkedRadioButtonId",R.id.radioOnePlayer);
            this.radioGroupMultiplayer.check(checkedRadioButtonId);
        }
        else{
            this.radioGroupMultiplayer.check(R.id.radioOnePlayer);
            Toast.makeText(this, "Use the default game setting",Toast.LENGTH_LONG).show();
        }
    }

    public void saveData(View view){
        SharedPreferences sharedPreferences = this.getSharedPreferences("multiData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        int checkedRadioButtonId = radioGroupMultiplayer.getCheckedRadioButtonId();
        editor.putInt("checkedRadioButtonId",checkedRadioButtonId);

        editor.apply();
        Toast.makeText(this,"Game Setting saved!",Toast.LENGTH_LONG).show();
    }

}