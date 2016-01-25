package com.example.bballstatstrack.listeners;

import android.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.bballstatstrack.activities.GameActivity;
import com.example.bballstatstrack.models.gameevents.ReboundEvent;
import com.example.bballstatstrack.models.gameevents.ShootEvent;

public class ReboundEventListener implements OnClickListener {
    private GameActivity mActivity;

    private AlertDialog mDialog;

    private ReboundEvent mReboundEvent;

    public ReboundEventListener(GameActivity activity, AlertDialog dialog, ReboundEvent reboundEvent) {
        mActivity = activity;
        mDialog = dialog;
        mReboundEvent = reboundEvent;
    }

    @Override
    public void onClick(View v) {
        ShootEvent shootEvent = (ShootEvent) v.getTag();
        shootEvent.append(mReboundEvent);
        mActivity.addNewEvent(shootEvent);
        checkReboundEvent(mReboundEvent);
        mDialog.dismiss();
    }

    private void checkReboundEvent(ReboundEvent event) {
        if (event.getPlayer() == null) {
            mActivity.timerStop();
        } else {
            mActivity.timerStart();
        }
        switch (event.getReboundType()) {
            case DEFENSIVE:
                mActivity.swapBallPossession();
                break;
            case OFFENSIVE:
                mActivity.resetMidShotClock();
                break;
            default:
                break;

        }
    }
}
