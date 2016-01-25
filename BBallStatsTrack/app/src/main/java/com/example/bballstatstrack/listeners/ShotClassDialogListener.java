package com.example.bballstatstrack.listeners;

import android.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.bballstatstrack.activities.GameActivity;
import com.example.bballstatstrack.dialogs.ShotTypeDialog;
import com.example.bballstatstrack.models.gameevents.GameEvent.ShotClass;
import com.example.bballstatstrack.models.gameevents.ShootEvent;
import com.example.bballstatstrack.models.gameevents.foulevents.ShootingFoulEvent;

public class ShotClassDialogListener implements OnClickListener {
    private ShootingFoulEvent mShootingFoulEvent;

    private GameActivity mActivity;

    private AlertDialog mDialog;

    private ShootEvent mShootEvent;

    public ShotClassDialogListener(GameActivity activity, AlertDialog dialog, ShootEvent shootEvent,
                                   ShootingFoulEvent shootingFoulEvent) {
        mActivity = activity;
        mDialog = dialog;
        mShootEvent = shootEvent;
        mShootingFoulEvent = shootingFoulEvent;
    }

    @Override
    public void onClick(View v) {
        mDialog.dismiss();
        ShotClass shotClass = (ShotClass) v.getTag();
        mShootEvent.setShotClass(shotClass);
        AlertDialog dialog = new ShotTypeDialog(mActivity, mShootEvent, mShootingFoulEvent);
        dialog.show();
    }

}
