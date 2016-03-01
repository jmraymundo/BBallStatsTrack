package com.example.bballstatstrack.listeners.cancel;

import com.example.bballstatstrack.activities.GameActivity;
import com.example.bballstatstrack.dialogs.ReboundDialog;
import com.example.bballstatstrack.models.gameevents.ShootEvent;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;

public class ReboundEventOnCancelListener implements OnCancelListener
{
    private GameActivity mActivity;

    private ShootEvent mShootEvent;

    public ReboundEventOnCancelListener( GameActivity activity, ShootEvent shootEvent )
    {
        mActivity = activity;
        mShootEvent = shootEvent;
    }

    @Override
    public void onCancel( DialogInterface dialog )
    {
        dialog.dismiss();
        AlertDialog returnDialog = new ReboundDialog( mActivity, mShootEvent );
        returnDialog.show();
    }
}