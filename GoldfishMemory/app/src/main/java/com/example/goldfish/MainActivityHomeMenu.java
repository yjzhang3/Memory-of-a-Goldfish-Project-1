package com.example.goldfish;


import android.content.Intent;
//import android.support.v4.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivityHomeMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_menu);


        //getSupportActionBar().setTitle("MainMenu");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);


// change 'Settings.class' to 'PlayGame.class'
      Button MasterStart = (Button)findViewById(R.id.MasterStart);
        MasterStart.setOnClickListener(new View.OnClickListener() {
          @Override
           public void onClick(View v) {
               Intent startIntent = new Intent(getApplicationContext(), MainActivityMultiplayer.class);
//                // show how to pass information to another activity
                startActivity(startIntent);
            }
       });
//

        Button settings = (Button) findViewById(R.id.settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), Settings.class);
                // show how to pass information to another activity
                startActivity(startIntent);
            }
        });
    }

    // these two are for the home button and main menu button on the phone. Can keep it if you want
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}


