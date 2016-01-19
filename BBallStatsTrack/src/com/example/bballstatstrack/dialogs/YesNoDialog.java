package com.example.bballstatstrack.dialogs;

import com.example.bballstatstrack.R;
import com.example.bballstatstrack.listeners.DismissDialogListener;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;

public class YesNoDialog extends AlertDialog
{
    public YesNoDialog( Context context, int titleResID )
    {
        super( context );
        setTitle( titleResID );
        DismissDialogListener listener = new DismissDialogListener();
        setButton( Dialog.BUTTON_POSITIVE, getContext().getText( R.string.yes ), listener );
        setButton( Dialog.BUTTON_NEGATIVE, getContext().getText( R.string.no ), listener );
    }
}
