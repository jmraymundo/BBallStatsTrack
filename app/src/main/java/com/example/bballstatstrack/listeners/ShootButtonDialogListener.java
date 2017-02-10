package com.example.bballstatstrack.listeners;

import android.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.bballstatstrack.activities.GameActivity;
import com.example.bballstatstrack.dialogs.ShotClassDialog;
import com.example.bballstatstrack.models.gameevents.ShootEvent;
import com.example.bballstatstrack.models.gameevents.foulevents.ShootingFoulEvent;

public class ShootButtonDialogListener implements OnClickListener {

    private GameActivity mActivity;

    private AlertDialog mDialog;

    private ShootEvent mShootEvent;

    private ShootingFoulEvent mShootingFoulEvent;

    public ShootButtonDialogListener(GameActivity activity, AlertDialog dialog, ShootEvent shootEvent,
                                     ShootingFoulEvent shootingFoulEvent) {
        mActivity = activity;
        mDialog = dialog;
        mShootEvent = shootEvent;
        mShootingFoulEvent = shootingFoulEvent;
    }

    @Override
    public void onClick(View v) {
        mDialog.dismiss();
        if (mShootingFoulEvent != null) {
            mShootingFoulEvent.setShooter(mShootEvent.getPlayer());
            mShootingFoulEvent.setShooterTeam(mShootEvent.getTeam());
            mActivity.addNewEvent(mShootingFoulEvent);
        }
        AlertDialog dialog = new ShotClassDialog(mActivity, mShootEvent, mShootingFoulEvent);
        dialog.show();
    }

}
