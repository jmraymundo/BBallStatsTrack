package com.example.bballstatstrack.dialogs;

import com.example.bballstatstrack.R;
import com.example.bballstatstrack.activities.GameActivity;
import com.example.bballstatstrack.listeners.FreeThrowListener;
import com.example.bballstatstrack.models.gameevents.ShootEvent;

import android.app.Dialog;

public class FreeThrowDialog extends YesNoDialog
{
    public FreeThrowDialog( GameActivity activity, ShootEvent shootEvent, int ftCount )
    {
        super( activity, R.string.free_throw_dialog_question );
        setButton( Dialog.BUTTON_POSITIVE, getContext().getText( R.string.yes ),
                new FreeThrowListener( activity, shootEvent, true, ftCount ) );
        setButton( Dialog.BUTTON_NEGATIVE, getContext().getText( R.string.no ),
                new FreeThrowListener( activity, shootEvent, false, ftCount ) );
        setCancelable( false );

        activity.startEvent();
    }
}
