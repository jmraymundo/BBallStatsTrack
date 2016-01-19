package com.example.bballstatstrack.listeners.cancel;

import com.example.bballstatstrack.activities.GameActivity;
import com.example.bballstatstrack.dialogs.TurnoverTypeDialog;
import com.example.bballstatstrack.models.gameevents.TurnoverEvent;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;

public class StealDialogOnCancelListener implements OnCancelListener
{
    private GameActivity mActivity;

    private TurnoverEvent mTurnoverEvent;

    public StealDialogOnCancelListener( GameActivity activity, TurnoverEvent turnoverEvent )
    {
        mActivity = activity;
        mTurnoverEvent = turnoverEvent;
    }

    @Override
    public void onCancel( DialogInterface dialog )
    {
        dialog.dismiss();
        AlertDialog returnDialog = new TurnoverTypeDialog( mActivity, mTurnoverEvent );
        returnDialog.show();
    }
}
