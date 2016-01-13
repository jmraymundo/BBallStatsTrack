package com.example.bballstatstrack.listeners;

import com.example.bballstatstrack.activities.GameActivity;
import com.example.bballstatstrack.dialogs.BlockDialog;
import com.example.bballstatstrack.dialogs.ReboundDialog;
import com.example.bballstatstrack.models.gameevents.ShootEvent;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

public class BlockCheckDialogListener implements OnClickListener
{
    private GameActivity mActivity;

    private ShootEvent mShootEvent;

    private boolean mBlocked;

    public BlockCheckDialogListener( GameActivity activity, ShootEvent shootEvent, boolean isBlocked )
    {
        mActivity = activity;
        mShootEvent = shootEvent;
        mBlocked = isBlocked;
    }

    @Override
    public void onClick( DialogInterface dialog, int which )
    {
        dialog.dismiss();
        AlertDialog nextDialog;
        if( mBlocked )
        {
            nextDialog = new BlockDialog( mActivity, mShootEvent );
        }
        else
        {
            nextDialog = new ReboundDialog( mActivity, mShootEvent );
        }
        nextDialog.show();
    }
}
