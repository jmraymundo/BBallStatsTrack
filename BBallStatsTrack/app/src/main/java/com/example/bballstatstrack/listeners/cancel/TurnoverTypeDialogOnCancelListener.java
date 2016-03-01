package com.example.bballstatstrack.listeners.cancel;

import com.example.bballstatstrack.activities.GameActivity;
import com.example.bballstatstrack.dialogs.TurnoverButtonDialog;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;

public class TurnoverTypeDialogOnCancelListener implements OnCancelListener
{
    private GameActivity mActivity;

    public TurnoverTypeDialogOnCancelListener( GameActivity activity )
    {
        mActivity = activity;
    }

    @Override
    public void onCancel( DialogInterface dialog )
    {
        dialog.dismiss();
        AlertDialog returnDialog = new TurnoverButtonDialog( mActivity );
        returnDialog.show();
    }
}