package com.example.bballstatstrack.dialogs;

import com.example.bballstatstrack.R;
import com.example.bballstatstrack.activities.GameActivity;
import com.example.bballstatstrack.listeners.BlockCheckDialogListener;
import com.example.bballstatstrack.models.gameevents.ShootEvent;

import android.app.Dialog;

public class BlockCheckDialog extends YesNoDialog
{
    public BlockCheckDialog( GameActivity activity, ShootEvent shootEvent )
    {
        super( activity, R.string.shoot_dialog_block_check_question );
        setButton( Dialog.BUTTON_POSITIVE, getContext().getText( R.string.yes ),
                new BlockCheckDialogListener( activity, shootEvent, true ) );
        setButton( Dialog.BUTTON_NEGATIVE, getContext().getText( R.string.no ),
                new BlockCheckDialogListener( activity, shootEvent, false ) );
        setCancelable( false );
    }
}
