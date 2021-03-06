package com.example.bballstatstrack.listeners.cancel;

import com.example.bballstatstrack.activities.GameActivity;
import com.example.bballstatstrack.dialogs.FoulButtonDialog;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;

public class FoulTypeDialogOnCancelListener implements OnCancelListener
{
    private GameActivity mActivity;

    public FoulTypeDialogOnCancelListener( GameActivity activity )
    {
        mActivity = activity;
    }

    @Override
    public void onCancel( DialogInterface dialog )
    {
        dialog.dismiss();
        AlertDialog returnDialog = new FoulButtonDialog( mActivity );
        returnDialog.show();
    }
}
