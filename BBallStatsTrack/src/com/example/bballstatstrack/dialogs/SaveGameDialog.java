package com.example.bballstatstrack.dialogs;

import com.example.bballstatstrack.R;
import com.example.bballstatstrack.activities.GameActivity;
import com.example.bballstatstrack.listeners.SaveGameDialogListener;

import android.app.Dialog;

public class SaveGameDialog extends YesNoDialog
{
    public SaveGameDialog( GameActivity activity )
    {
        super( activity, R.string.save_dialog_confirmation_question );
        setButton( Dialog.BUTTON_POSITIVE, getContext().getText( R.string.yes ),
                new SaveGameDialogListener( activity, activity.getGame() ) );
    }
}
