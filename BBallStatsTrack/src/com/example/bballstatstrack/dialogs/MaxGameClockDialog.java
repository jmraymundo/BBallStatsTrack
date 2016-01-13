package com.example.bballstatstrack.dialogs;

import com.example.bballstatstrack.R;
import com.example.bballstatstrack.activities.GameActivity;
import com.example.bballstatstrack.listeners.MaxGameClockDialogListener;

import android.app.Dialog;

public class MaxGameClockDialog extends ClockDialog
{
    public MaxGameClockDialog( GameActivity activity )
    {
        super( activity, R.string.set_max_game_clock_title, 12 );
        setButton( Dialog.BUTTON_POSITIVE, getContext().getText( android.R.string.ok ),
                new MaxGameClockDialogListener( activity, mClockField ) );
    }
}
