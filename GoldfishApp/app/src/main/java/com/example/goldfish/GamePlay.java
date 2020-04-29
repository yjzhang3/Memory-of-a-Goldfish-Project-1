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

    // Timer counts down starting from 5 minutes, or 300 seconds
    private static final long START_TIME_IN_MILLIS = 300000;
    private TextView countDownText;
    private Button countDownButton;

    // Timer variables
    private CountDownTimer countDownTimer;
    private long timeLeftMilliseconds = START_TIME_IN_MILLIS;
    private String timeUsed;
    private boolean timerRunning;

    // Game play variables
    int numCards = 16;
    int numMatchedCards = 0, firstCardPosition, moveCount = 0;
    final List<Integer> cardImages = new ArrayList<>();
    int [] imageID = new int [ numCards];
    GridView cardDisplayView;
    CardImageAdapter cardImageAdapter;
    ImageView imageView, firstCardView, secondCardView;
    boolean firstClick = false;
    Handler handler;

    // Difficulty settings buttons
    private RadioButton radioHard;
    private RadioButton radioMedium;
    private RadioButton radioEasy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);

        // Pause game button
        Button fab = findViewById(R.id.pause);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PauseGameDialog();
                stopTimer();
            }
        });

        countDownText = findViewById(R.id.Start_countdown);
        countDownButton = findViewById(R.id.button_first_startgame);
        countDownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTimer(); // once the Ready? Go! Button is pressed, the timer starts.

            }
        });
        
        // pass in difficulty from settings & change number of cards based on difficulty
        adjustSettings(); 
        loadImagesAndCards();

        // loads gridview of cards
        cardDisplayView = findViewById(R.id.grid_view);
        // uses adapter to fill out each grid cell with an image initially
        cardImageAdapter = new CardImageAdapter(this, imageID, numCards);
        cardDisplayView.setAdapter( cardImageAdapter);
        // shuffle the images on the gameboard
        cardImageAdapter.shuffle();
        cardImageAdapter.notifyDataSetChanged();

        // allows action to be taken when player clicks on a card
        playGame();

    }

    // Use shared preferences to gather data from difficulty level settings
    // and pass value to adjust settings for gameplay
    public void adjustSettings() {
        SharedPreferences sharedPreferences = getSharedPreferences("diffData", MODE_PRIVATE);
        int value = sharedPreferences.getInt("checkedRadioButtonId",0); // value has the value of that in sharedPrefs

        // hard, shows 4 x 4 card array on the game play screen
        if (value==R.id.radioHard) { 
            numCards = 16;
        }
        
        // medium, 3 x 4 card array
        else if (value==R.id.radioMedium) {  
            numCards = 12;
        }
        
        // easy, 2 x 4 card array
        else { 
            numCards = 8;
        }
    }

    public String getMyData() {
        // a special class that pass the information from Acitivity to a Dialog.
        // Specifically, because we need to show the user how much time they
        // spent on the game, the amount of time left in the CountDownTimer is
        // passed back to WinLoseFragment. This way, when all cards match,
        // Dialog window will show the finish time.

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

    // initialize and show a PauseGameDialog. The details of this Dialog is in
    // PauseGameFragment.java. It allows the user to pause the game.
    private void PauseGameDialog() {
        PauseGameFragment dialog = new PauseGameFragment();
        dialog.show(getSupportFragmentManager(),"Pause dialog");

    }


    private void stopTimer() { // stop the timer
        countDownTimer.cancel();
        timerRunning = false;
    }

    public void startTimer() { // start the timer and show the current time
        // everytime it is resumed
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
        countDownButton.setVisibility(View.GONE); // once readygo button is pressed,
        // the button disappears.
    }


    public void updateTimer() { // a method to calculate the actual time left
        // in the CountDownTimer.
        int minutes = (int) timeLeftMilliseconds / 60000;
        int seconds = (int) timeLeftMilliseconds % 60000 / 1000;

        String timeLeftText;

        timeLeftText = "" + minutes;
        timeLeftText += ":";
        if (seconds<10) timeLeftText += "0";
        timeLeftText += seconds;

        countDownText.setText(timeLeftText);

//        if (minutes == 0 && seconds == 0) {
//            FragmentManager manager = getSupportFragmentManager();
//            WinLoseFragment dialog = new WinLoseFragment();
//            dialog.show(manager, "MessageDialog");
//        }

        // Setting preferences, use for best time
        SharedPreferences sharedPreferences = this.getSharedPreferences("gameTimes",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("newTime",(int)timeLeftMilliseconds);


    }

    // The PauseGameDialog fragment will receive a reference to this Activity through the
    // the Fragment.onAttach() callback, which it uses to call these two methods. Then users'
    // decisions are saved and passed to Fragment to initialize other Activities.
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
                
                // Loading the images from the drawable folder
                cardImages.add(R.drawable.pair1);
                cardImages.add(R.drawable.pair2);
                cardImages.add(R.drawable.pair3);
                cardImages.add(R.drawable.pair4);
                cardImages.add(R.drawable.pair5);
                cardImages.add(R.drawable.pair6);
                cardImages.add(R.drawable.pair7);
                cardImages.add(R.drawable.pair8);

                // assigns each cardImage index to two indices so there will be pairs of cards
                for( int i = 0; i < (numCards / 2); i++) {
                    imageID[i] = i;
                    imageID[ i + (numCards / 2)] = i;
                }
            }
        };
        Thread mythread = new Thread(runnable);
        mythread.start();
    }

    // This function allows the app to take action whenever the player clicks a card
    public void playGame() {
        
        Runnable runnable = new Runnable() {
            
            @Override
            public void run() {
                
                cardDisplayView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        
                        // shows the card face once the player clicks the card
                        imageView = (ImageView) view;
                        imageView.setImageResource(cardImages.get( imageID[ position]));

                        // keeping track of clicks two at a time
                        if (!firstClick) {
                            firstClick = true;
                            firstCardPosition = position;
                            firstCardView = imageView;
                        } 
                        
                        // second click only works if a different card is clicked
                        else if (position != firstCardPosition) {
                            firstClick = false;
                            secondCardView = imageView; 
                            // checking if the cards match
                            checkMatch(position, firstCardPosition, firstCardView, secondCardView); 
                        }
                    }
                });
            }
        };
        
        // running this function in a different thread from the main one to prevent possible crashing
        Thread mythread = new Thread(runnable);
        mythread.start();
    }

    // After two cards are clicked, this function checks if they match
    // If two cards match, they disappear from the game board
    // If they don't match, the back sides of the cards are shown again
    public void checkMatch(final int pos1, final int pos2, final ImageView firstCardView, final ImageView secondCardView) {

        handler = new Handler();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                
                // clearing the 2 cards from the board
                if( imageID[ pos1] == imageID[ pos2]) { 
                    
                    numMatchedCards += 2;
                    firstCardView.setVisibility( View.INVISIBLE);
                    firstCardView.setClickable( false);
                    secondCardView.setVisibility( View.INVISIBLE);
                    secondCardView.setClickable( false);

                    // timer stops once all the pairs of cards are matched 
                    if ( numMatchedCards == numCards) {

                        stopTimer();
                        FragmentManager manager = getSupportFragmentManager();
                        WinLoseFragment dialog = new WinLoseFragment();
                        dialog.show(manager, "MessageDialog");
                    }
                }
                
                //shows card back if two cards don't match
                else {
                    firstCardView.setImageResource( R.drawable.back);
                    secondCardView.setImageResource( R.drawable.back);
                }
            }
        };

        // Delay of 0.6 second after the player clicks the second card
        // before the card faces are hidden or cleared  
        handler.postDelayed( runnable, 600);
    }
}









