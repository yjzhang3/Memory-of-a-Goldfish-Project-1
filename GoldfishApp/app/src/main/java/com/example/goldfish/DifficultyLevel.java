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
        
        // Enables backbutton to go back to the previous activity
        getSupportActionBar().setTitle("Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        // Find which radio button was clocked in the radio group
        this.radioGroupDifficulty = (RadioGroup) this.findViewById(R.id.radioGroupDifficulty);
        this.saveButtonD = (Button) findViewById(R.id.saveButtonD);
        
        // once the button is clicked, the user's choice is saved
        this.saveButtonD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DifficultyLevel.this.saveData(view);
            }
        });

        this.loadData();
    }
    
    // Data is saved using Shared Prefences. This data was saved as an integer value that would be passed to another 
    // function to change the number of cards per difficulty level
    public void saveData(View view){
        SharedPreferences sharedPreferences = this.getSharedPreferences("diffData",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int checkedRadioButtonId = radioGroupDifficulty.getCheckedRadioButtonId();
        radioButtonDifficulty = (RadioButton) findViewById(checkedRadioButtonId);
        editor.putInt("checkedRadioButtonId", checkedRadioButtonId);
        editor.apply();
        Toast.makeText(this,"Game Setting saved!", Toast.LENGTH_SHORT).show();
    }
    
    // Upon opening game, the saved radio button choice/difficulty level is applied.
    private void loadData() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("diffData", MODE_PRIVATE);
        
        if (sharedPreferences != null){ 
            int checkedRadioButtonId = sharedPreferences.getInt("checkedRadioButtonId",R.id.radioMedium);
            this.radioGroupDifficulty.check(checkedRadioButtonId);

        }
        else {
            // Default game setting when user has not selected a difficulty level.
            this.radioGroupDifficulty.check(R.id.radioMedium); // Default is set to a medium level
        }
    }

}
