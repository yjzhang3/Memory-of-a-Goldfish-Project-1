package com.example.goldfish;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class GamePlay extends AppCompatActivity implements PauseGameFragment.PauseDialogListener {

    private static final long START_TIME_IN_MILLIS = 300000;
    private TextView countDownText;
    private Button countDownButton;

    private CountDownTimer countDownTimer;
    private long timeLeftMilliseconds = START_TIME_IN_MILLIS;
    private String timeUsed;
    private boolean timerRunning;

    int numCards = 16;
    int numMatchedCards = 0, firstCardPosition, moveCount = 0;
    final List<Integer> cardImages = new ArrayList<>();
    int [] imageID = new int [ numCards];
    GridView cardDisplayView;
    CardImageAdapter cardImageAdapter;
    ImageView imageView, firstCardView, secondCardView;
    boolean firstClick = false;
    Handler handler;

    // DECLARE DIFFICULTY VARIABLE HERE
    private RadioButton radioHard;
    private RadioButton radioMedium;
    private RadioButton radioEasy;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        Button fab = findViewById(R.id.pause);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PauseGameDialog();
                stopTimer();
            }
        });

        //adjustSettings();
        //pass in difficulty from settings & change number of cards based on difficulty

        countDownText = findViewById(R.id.Start_countdown);
        countDownButton = findViewById(R.id.button_first_startgame);
        countDownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTimer();

            }
        });

        adjustSettings(); // pass in difficulty from settings & change number of cards based on difficulty
        loadImagesAndCards();

        cardDisplayView = findViewById(R.id.grid_view);
        cardImageAdapter = new CardImageAdapter(this, imageID, numCards);
        cardDisplayView.setAdapter( cardImageAdapter);
        cardImageAdapter.shuffle();
        cardImageAdapter.notifyDataSetChanged();

        playGame();

    }

    // Use shared preferences to gather data from difficulty level settings
    // and pass value to adjust settings for gameplay
    public void adjustSettings() {
        SharedPreferences sharedPreferences = getSharedPreferences("diffData", MODE_PRIVATE);
        int value = sharedPreferences.getInt("checkedRadioButtonId",0); // value has the value of that in sharedPrefs

        if (value==R.id.radioHard) { // hard, shows 4 x 4 card array on the game play screen
            numCards = 16;
        }
        else if (value==R.id.radioMedium) {  // medium, 3 x 4 card array
            numCards = 12;
        }

        else { // easy, 2 x 4 card array
            numCards = 8;
        }
    }

//    public void bestTime() {
//        SharedPreferences sharedPreferences = getSharedPreferences("diffData", MODE_PRIVATE);
//        int value = sharedPreferences.getInt("checkedRadioButtonId",0);
//
//        SharedPreferences sharedPreferences1 = this.getSharedPreferences("gameTimes",MODE_PRIVATE);
//        int newTime = sharedPreferences1.getInt("newTime", 0); //get newTime value, 0 is the default
//        int HardBestTime = sharedPreferences1.getInt("hardBestTime",0);
//
//        TextView hardBestTime = (TextView) findViewById(R.id.hardBestTime);
//
//        if (value == R.id.radioHard){
//            if (newTime < HardBestTime) {
//                String newT= getMyData();
//                newT = "Hard:" + newT;
//                hardBestTime.setText(newT);
//
//                //save
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putInt("hardBestTime", newTime);
//                editor.apply();
//            }
//
//            else {
//                hardBestTime.setText("Hard" + HardBestTime);
//                // nothing is saved
//            }
//
//        }
//    }


    public String getMyData() {

        if (timeLeftMilliseconds == 0) {
            timeUsed = "Time is up!";
        }

        else {
            int minutes = (int) (300000 - timeLeftMilliseconds) / 60000;
            int seconds = (int) (300000 - timeLeftMilliseconds) % 60000 / 1000;

            timeUsed = "" + minutes;
            timeUsed += ":";
            if (seconds < 10) timeUsed += "0";
            timeUsed += seconds;
            timeUsed = "Your finish time is" +  " "+ timeUsed;
        }

        return timeUsed;
    }

    private void PauseGameDialog() {
        PauseGameFragment dialog = new PauseGameFragment();
        dialog.show(getSupportFragmentManager(),"Pause dialog");

    }

//    public void showNoticeDialog() {
//        DialogFragment dialog = new PauseGameFragment();
//        dialog.show(getSupportFragmentManager(),"PauseMessageDialog");
//    }

//    private void startStop() {
//        if (timerRunning) {
//            stopTimer();
//        } else {
//            startTimer();
//        }
//    }

    private void stopTimer() {
        countDownTimer.cancel();
        timerRunning = false;
    }

    public void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftMilliseconds, 1000) {
            @Override
            public void onTick(long l) {
                timeLeftMilliseconds = l;
                updateTimer();

            }

            @Override
            public void onFinish() {

            }
        }.start();

        timerRunning = true;
        countDownButton.setVisibility(View.GONE);
    }


    public void updateTimer() {
        int minutes = (int) timeLeftMilliseconds / 60000;
        int seconds = (int) timeLeftMilliseconds % 60000 / 1000;

        String timeLeftText;

        timeLeftText = "" + minutes;
        timeLeftText += ":";
        if (seconds<10) timeLeftText += "0";
        timeLeftText += seconds;

        countDownText.setText(timeLeftText);

        if (minutes == 0 && seconds == 0) {
            FragmentManager manager = getSupportFragmentManager();
            WinLoseFragment dialog = new WinLoseFragment();
            dialog.show(manager, "MessageDialog");
        }

        // Setting preferences, use for best time
        SharedPreferences sharedPreferences = this.getSharedPreferences("gameTimes",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("newTime",(int)timeLeftMilliseconds);


    }

    @Override
    public void onNoClicked() {
        startTimer();
    }

    public void onYesClicked() {
        Intent startIntent = new Intent(getApplicationContext(), MainActivityHomeMenu.class);
        // show how to pass information to another activity
        startActivity(startIntent);
    }

    public void loadImagesAndCards() {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                cardImages.add(R.drawable.pair1);
                cardImages.add(R.drawable.pair2);
                cardImages.add(R.drawable.pair3);
                cardImages.add(R.drawable.pair4);
                cardImages.add(R.drawable.pair5);
                cardImages.add(R.drawable.pair6);
                cardImages.add(R.drawable.pair7);
                cardImages.add(R.drawable.pair8);

                for( int i = 0; i < (numCards / 2); i++) {
                    imageID[i] = i;
                    imageID[ i + (numCards / 2)] = i;
                }
            }
        };
        Thread mythread = new Thread(runnable);
        mythread.start();
    }

    public void playGame() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                cardDisplayView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        imageView = (ImageView) view;
                        imageView.setImageResource(cardImages.get( imageID[ position]));

                        if (!firstClick) {
                            firstClick = true;
                            firstCardPosition = position;
                            firstCardView = imageView;
                            moveCount ++;

                        } else if (position != firstCardPosition) {
                            firstClick = false;
                            secondCardView = imageView;
                            moveCount ++;
                            checkMatch(position, firstCardPosition, firstCardView, secondCardView);
                        }
                    }
                });
            }
        };
        Thread mythread = new Thread(runnable);
        mythread.start();
    }

    public void checkMatch(final int pos1, final int pos2, final ImageView firstCardView, final ImageView secondCardView) {

        handler = new Handler();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if( imageID[ pos1] == imageID[ pos2]) {
                    numMatchedCards += 2;
                    firstCardView.setVisibility( View.INVISIBLE);
                    firstCardView.setClickable( false);
                    secondCardView.setVisibility( View.INVISIBLE);
                    secondCardView.setClickable( false);

                    if ( numMatchedCards == numCards) {

                        stopTimer();
                        FragmentManager manager = getSupportFragmentManager();
                        WinLoseFragment dialog = new WinLoseFragment();
                        dialog.show(manager, "MessageDialog");

                    }
                }
                else {
                    firstCardView.setImageResource( R.drawable.back);
                    secondCardView.setImageResource( R.drawable.back);
                }
            }
        };

        handler.postDelayed( runnable, 600);
    }
}









