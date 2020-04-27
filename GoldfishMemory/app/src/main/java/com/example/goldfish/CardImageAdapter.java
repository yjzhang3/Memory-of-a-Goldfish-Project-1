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

public class CardImageAdapter extends BaseAdapter {

    private Context context;
    private int numCount;
    private int [] imageID;

    public CardImageAdapter(final Context context, final int[] array, final int numCards) {
        this.context = context;
        imageID = array;
        numCount = numCards;
    }

    public void shuffle() {
        //random seed is based on current time
        Random rand = new Random( System.currentTimeMillis());
        int temp, j;

        for( int i = 0; i < numCount; i++) {
            temp = imageID[i];
            j = rand.nextInt( numCount);
            imageID[i] = imageID[j];
            imageID[j] = temp;
        }
    }

    @Override
    public int getCount() {
        return numCount;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView imageView = (ImageView) convertView;

        if( convertView == null) {
            imageView = new ImageView( context);
            imageView.setLayoutParams( new GridView.LayoutParams(275,350));
        }

        imageView.setImageResource( R.drawable.back);

        return imageView;
    }
}


