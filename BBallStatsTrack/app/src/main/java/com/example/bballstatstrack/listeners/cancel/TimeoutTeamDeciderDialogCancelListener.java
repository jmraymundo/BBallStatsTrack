package com.example.bballstatstrack.listeners.cancel;

import com.example.bballstatstrack.activities.GameActivity;
import com.example.bballstatstrack.dialogs.CoachButtonDialog;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;

public class TimeoutTeamDeciderDialogCancelListener implements OnCancelListener
{
    private GameActivity mActivity;

    public TimeoutTeamDeciderDialogCancelListener( GameActivity activity )
    {
        mActivity = activity;
    }

    @Override
    public void onCancel( DialogInterface dialog )
    {
        dialog.dismiss();
        AlertDialog returnDialog = new CoachButtonDialog( mActivity );
        returnDialog.show();
    }
}
