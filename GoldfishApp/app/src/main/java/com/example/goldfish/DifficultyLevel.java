package com.example.goldfish;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DifficultyLevel extends AppCompatActivity {

    private RadioGroup radioGroupDifficulty;
    private Button saveButtonD;
    private RadioButton radioButtonDifficulty;
    private RadioButton radioMedium;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty_level);

        getSupportActionBar().setTitle("Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.radioGroupDifficulty = (RadioGroup) this.findViewById(R.id.radioGroupDifficulty);
        this.saveButtonD = (Button) findViewById(R.id.saveButtonD);

        this.saveButtonD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DifficultyLevel.this.saveData(view);

            }
        });

        this.loadData();
    }

    public void saveData(View view){
        SharedPreferences sharedPreferences = this.getSharedPreferences("diffData",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int checkedRadioButtonId = radioGroupDifficulty.getCheckedRadioButtonId();
        radioButtonDifficulty = (RadioButton) findViewById(checkedRadioButtonId);
        editor.putInt("checkedRadioButtonId", checkedRadioButtonId);
        editor.apply();
        Toast.makeText(this,"Game Setting saved!", Toast.LENGTH_SHORT).show();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("diffData", MODE_PRIVATE);

        if (sharedPreferences != null){
            int checkedRadioButtonId = sharedPreferences.getInt("checkedRadioButtonId",R.id.radioMedium);
            this.radioGroupDifficulty.check(checkedRadioButtonId);

        }
        else {
            this.radioGroupDifficulty.check(R.id.radioMedium); //default game setting
        }
    }

}