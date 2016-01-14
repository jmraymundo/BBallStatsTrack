package com.example.bballstatstrack.dialogs;

import com.example.bballstatstrack.R;
import com.example.bballstatstrack.activities.GameActivity;
import com.example.bballstatstrack.listeners.MaxPeriodClockDialogListener;

import android.app.Dialog;

public class MaxPeriodClockDialog extends ClockDialog
{
    public MaxPeriodClockDialog( GameActivity activity )
    {
        super( activity, R.string.set_max_period_clock_title, 12 );
        setButton( Dialog.BUTTON_POSITIVE, getContext().getText( android.R.string.ok ),
                new MaxPeriodClockDialogListener( activity, mClockField ) );
    }
}
