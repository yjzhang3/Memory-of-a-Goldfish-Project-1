package com.example.goldfish;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivityMultiplayer extends AppCompatActivity implements PauseGameFragment.PauseDialogListener {

    private static final long START_TIME_IN_MILLIS = 6000;
    private TextView countDownText;
    private Button countDownButton;

    private CountDownTimer countDownTimer;
    private long timeLeftMilliseconds = START_TIME_IN_MILLIS;
    private String timeUsed;
    private boolean timerRunning;

    final int numCards = 16;
    int numMatchedCards = 0, firstCardPosition, moveCount = 0;
    final List<Integer> cardImages = new ArrayList<>();
    int [] imageID = new int [ numCards];
    GridView cardDisplayView;
    CardImageAdapter cardImageAdapter;
    ImageView imageView, firstCardView, secondCardView;
    boolean firstClick = false;
    Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayer);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.pause);
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
                startTimer();

            }
        });

        //adjustSettings(); // pass in difficulty from settings & change number of cards based on difficulty
        loadImagesAndCards();

        cardDisplayView = findViewById(R.id.grid_view);
        cardImageAdapter = new CardImageAdapter(this, imageID, numCards);
        cardDisplayView.setAdapter( cardImageAdapter);
        cardImageAdapter.shuffle();
        cardImageAdapter.notifyDataSetChanged();

        playGame();

    }

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









