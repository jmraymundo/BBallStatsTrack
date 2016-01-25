package com.example.bballstatstrack.listeners;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

import com.example.bballstatstrack.activities.GameActivity;
import com.example.bballstatstrack.dialogs.AssistCheckDialog;
import com.example.bballstatstrack.dialogs.BlockCheckDialog;
import com.example.bballstatstrack.dialogs.FreeThrowDialog;
import com.example.bballstatstrack.models.gameevents.GameEvent.ShotClass;
import com.example.bballstatstrack.models.gameevents.ShootEvent;
import com.example.bballstatstrack.models.gameevents.foulevents.ShootingFoulEvent;

public class ShootEventListener implements OnClickListener {
    private GameActivity mActivity;

    private ShootEvent mShootEvent;

    private boolean mShotMade;

    private ShootingFoulEvent mShootingFoulEvent;

    public ShootEventListener(GameActivity activity, ShootEvent shootEvent, boolean shotMade,
                              ShootingFoulEvent shootingFoulEvent) {
        mActivity = activity;
        mShootEvent = shootEvent;
        mShotMade = shotMade;
        mShootingFoulEvent = shootingFoulEvent;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        dialog.dismiss();
        mShootEvent.setShotMade(mShotMade);
        setFTCount();
        AlertDialog nextDialog;
        if (mShotMade) {
            nextDialog = new AssistCheckDialog(mActivity, mShootEvent, mShootingFoulEvent);
        } else if (mShootingFoulEvent == null) {
            nextDialog = new BlockCheckDialog(mActivity, mShootEvent);
        } else {
            nextDialog = new FreeThrowDialog(mActivity, mShootEvent, mShootingFoulEvent.getFTCount());
        }
        nextDialog.show();
    }

    private void setFTCount() {
        ShotClass shotClass = mShootEvent.getShotClass();
        if (mShootingFoulEvent != null) {
            int ftCount;
            if (mShotMade) {
                ftCount = 1;
            } else if (shotClass == ShotClass.FG_2PT) {
                ftCount = 2;
            } else {
                ftCount = 3;
            }
            mShootingFoulEvent.setFTCount(ftCount);
        }
    }

}
