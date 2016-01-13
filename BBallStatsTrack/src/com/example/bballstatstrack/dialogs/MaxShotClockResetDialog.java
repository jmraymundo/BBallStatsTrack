package com.example.bballstatstrack.dialogs;

import com.example.bballstatstrack.R;
import com.example.bballstatstrack.activities.GameActivity;
import com.example.bballstatstrack.listeners.MaxShotClockResetDialogListener;

import android.app.Dialog;

public class MaxShotClockResetDialog extends ClockDialog
{

    public MaxShotClockResetDialog( GameActivity activity )
    {
        super( activity, R.string.set_reset_shot_clock_title, 24 );
        setButton( Dialog.BUTTON_POSITIVE, getContext().getText( android.R.string.ok ),
                new MaxShotClockResetDialogListener( activity, mClockField ) );
    }
}
