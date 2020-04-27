package com.example.goldfish;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

public class WinLoseFragment extends DialogFragment {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        MainActivityMultiplayer activity = (MainActivityMultiplayer) getActivity();
        String mydata = activity.getMyData();
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.fragment_winlose, null);


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage(mydata)
                .setView(v)
                .setPositiveButton("Play again!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(((Dialog) dialog).getContext(), MainActivityMultiplayer.class));
                    }
                })
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(((Dialog) dialog).getContext(), MainActivityHomeMenu.class));
                    }
                });

        // Create the AlertDialog object and return it
        builder.create();
        return builder.show();
    }
}


