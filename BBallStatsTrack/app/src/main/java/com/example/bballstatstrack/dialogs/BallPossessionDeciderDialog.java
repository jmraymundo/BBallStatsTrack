package com.example.bballstatstrack.dialogs;

import com.example.bballstatstrack.activities.GameActivity;
import com.example.bballstatstrack.listeners.BallPossessionDeciderDialogListener;

import android.app.Dialog;

public class BallPossessionDeciderDialog extends TeamDeciderDialog
{
    public BallPossessionDeciderDialog( GameActivity activity )
    {
        super( activity );
        setButton( Dialog.BUTTON_NEGATIVE, mHomeTeam.getName(),
                new BallPossessionDeciderDialogListener( activity, mHomeTeam ) );
        setButton( Dialog.BUTTON_POSITIVE, mAwayTeam.getName(),
                new BallPossessionDeciderDialogListener( activity, mAwayTeam ) );
    }
}
