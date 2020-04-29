package com.example.goldfish;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

// Create a DialogFragment to initialize the alertdialog in GamePlay. This class creates an PauseGame
// Fragment that will be called during the game so that a pop window will show up, and it asks for
// user input, whether they want to exit out the current game session and go back to main menu, or
// whether they just want to pause for now.
public class PauseGameFragment extends AppCompatDialogFragment {

    // defines an instance of the PauseDialogListener to deliver action events
    private PauseDialogListener listener;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.fragment_pause_game, null);
        // pop up the dialog window

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // build an AlertDialog with following properties (two buttons)

        builder.setMessage(R.string.leave_message);
        builder.setView(v);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                listener.onDialogPositiveClick(PauseGameFragment.this);
                listener.onYesClicked();

            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //dismiss();
//                listener.onDialogNegativeClick(PauseGameFragment.this);
                listener.onNoClicked();
            }
        });

        // Create the AlertDialog object and return it
        builder.create();
        return builder.show();
    }

    // Defines an interface through which it delivers the events (users'
    // decisions back to the host activity.
public interface PauseDialogListener {
    void onNoClicked();
    void onYesClicked();
}


    // Override the Fragment.onAttach() method to instantiate the PauseDialogListener
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (PauseDialogListener) context;
            // Verify that the host activity implements the callback interface
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + "must implement PauseDialogListener");
            // The activity doesn't implement the interface, throw exception
        }
    }
}



