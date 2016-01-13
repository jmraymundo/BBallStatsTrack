package com.example.bballstatstrack.listeners;

import com.example.bballstatstrack.activities.GameActivity;
import com.example.bballstatstrack.dialogs.AssistDialog;
import com.example.bballstatstrack.dialogs.FreeThrowDialog;
import com.example.bballstatstrack.models.gameevents.ShootEvent;
import com.example.bballstatstrack.models.gameevents.foulevents.ShootingFoulEvent;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

public class AssistCheckDialogListener implements OnClickListener
{
    private GameActivity mActivity;

    private ShootEvent mShootEvent;

    private ShootingFoulEvent mShootingFoulEvent;

    private boolean mAssisted;

    public AssistCheckDialogListener( GameActivity activity, ShootEvent shootEvent, ShootingFoulEvent shootingFoulEvent,
            boolean isAssisted )
    {
        mActivity = activity;
        mShootEvent = shootEvent;
        mShootingFoulEvent = shootingFoulEvent;
        mAssisted = isAssisted;
    }

    @Override
    public void onClick( DialogInterface dialog, int which )
    {
        dialog.dismiss();
        AlertDialog nextDialog;
        if( mAssisted )
        {
            nextDialog = new AssistDialog( mActivity, mShootEvent, mShootingFoulEvent );
            nextDialog.show();
        }
        else
        {
            mActivity.addNewEvent( mShootEvent );
            if( mShootingFoulEvent == null )
            {
                mActivity.swapBallPossession();
            }
            else
            {
                nextDialog = new FreeThrowDialog( mActivity, mShootEvent, 1 );
                nextDialog.show();
            }

        }
    }

}
