package com.example.bballstatstrack.dialogs;

import com.example.bballstatstrack.R;
import com.example.bballstatstrack.activities.GameActivity;
import com.example.bballstatstrack.listeners.DismissDialogListener;
import com.example.bballstatstrack.listeners.ResetGameClockListener;

import android.app.Dialog;

public class ResetGameClockDialog extends YesNoDialog
{
    public ResetGameClockDialog( GameActivity activity )
    {
        super( activity, R.string.reset_game_clock_confirmation_question );
        setButton( Dialog.BUTTON_POSITIVE, getContext().getText( R.string.yes ),
                new ResetGameClockListener( activity.getGame() ) );
        setButton( Dialog.BUTTON_NEGATIVE, getContext().getText( R.string.no ), new DismissDialogListener() );
    }
}
