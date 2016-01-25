package com.example.bballstatstrack.listeners;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

import com.example.bballstatstrack.activities.GameActivity;
import com.example.bballstatstrack.dialogs.FreeThrowDialog;
import com.example.bballstatstrack.dialogs.ReboundDialog;
import com.example.bballstatstrack.models.gameevents.GameEvent.ShotClass;
import com.example.bballstatstrack.models.gameevents.ShootEvent;

public class FreeThrowListener implements OnClickListener {
    private GameActivity mActivity;

    private ShootEvent mShootEvent;

    private boolean mShotMade;

    private int mFTCount;

    public FreeThrowListener(GameActivity activity, ShootEvent shootEvent, boolean shotMade, int ftCount) {
        mActivity = activity;
        mShootEvent = shootEvent;
        mShotMade = shotMade;
        mFTCount = ftCount;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        mActivity.startEvent();
        dialog.dismiss();
        ShootEvent event = new ShootEvent(ShotClass.FT, mShotMade, mShootEvent.getPlayer(), mShootEvent.getTeam());
        AlertDialog nextDialog;
        if (--mFTCount != 0) {
            mActivity.addNewEvent(event);
            nextDialog = new FreeThrowDialog(mActivity, mShootEvent, mFTCount);
            nextDialog.show();
        } else if (!mShotMade) {
            nextDialog = new ReboundDialog(mActivity, event);
            nextDialog.show();
        } else {
            mActivity.addNewEvent(event);
            mActivity.swapBallPossession();
        }
    }

}
