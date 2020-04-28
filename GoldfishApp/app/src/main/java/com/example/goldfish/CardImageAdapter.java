package com.example.goldfish;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;
import java.util.Random;
import java.util.logging.Handler;

// Purpose of this adapter is to fill out each grid cell of gridview with an image when the game starts
// Gridview is a frame that holds the images in an array
public class CardImageAdapter extends BaseAdapter {

    private Context context;
    private int numCount;
    private int [] imageID;

    // Constructor
    // Allows the GamePlay activity to create an adapter and pass in the relevant variables to here
    public CardImageAdapter(final Context context, final int[] array, final int numCards) {
        this.context = context;
        imageID = array;
        numCount = numCards;
    }

    // Shuffles the numbers in imageID array so that the card faces will be displayed shuffled
    public void shuffle() {
        
        // Random seed is based on current time
        Random rand = new Random( System.currentTimeMillis());
        int temp, j;

        // Swaps the content of an index with that of another index
        for( int i = 0; i < numCount; i++) {
            temp = imageID[i];
            j = rand.nextInt( numCount);
            imageID[i] = imageID[j];
            imageID[j] = temp;
        }
    }

    // Default function, returns number of images that will be filling gridview
    @Override
    public int getCount() {
        return numCount;
    }

    // Default function, not used
    @Override
    public Object getItem(int position) {
        return null;
    }

    // Default function, not used
    @Override
    public long getItemId(int position) {
        return 0;
    }

    // Most important part of adapter
    // Function is called for each grid cell to fill it up with an image
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView imageView = (ImageView) convertView;

        // Initial creation of ImageView and how it will fill the gridview
        if( convertView == null) {
            
            imageView = new ImageView( context);
            imageView.setLayoutParams( new GridView.LayoutParams(275,350));
        }

        // Each grid cell is just the card back logo by default
        imageView.setImageResource( R.drawable.back);

        return imageView;
    }
}


