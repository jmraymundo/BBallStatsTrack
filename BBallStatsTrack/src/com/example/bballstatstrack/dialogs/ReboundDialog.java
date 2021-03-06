package com.example.bballstatstrack.dialogs;

import com.example.bballstatstrack.activities.GameActivity;
import com.example.bballstatstrack.listeners.ReboundDialogListener;
import com.example.bballstatstrack.models.gameevents.ShootEvent;

import android.app.Dialog;

public class ReboundDialog extends TeamDeciderDialog
{
    public ReboundDialog( GameActivity activity, ShootEvent shootEvent )
    {
        super( activity );
        setButton( Dialog.BUTTON_NEGATIVE, mHomeTeam.getName(),
                new ReboundDialogListener( activity, shootEvent, mHomeTeam ) );
        setButton( Dialog.BUTTON_POSITIVE, mAwayTeam.getName(),
                new ReboundDialogListener( activity, shootEvent, mAwayTeam ) );
    }
}
