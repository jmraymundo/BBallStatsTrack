package com.example.bballstatstrack.listeners;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

import com.example.bballstatstrack.activities.GameActivity;
import com.example.bballstatstrack.dialogs.ReboundEventDialog;
import com.example.bballstatstrack.models.Team;
import com.example.bballstatstrack.models.gameevents.ShootEvent;

public class ReboundDialogListener implements OnClickListener {
    private GameActivity mActivity;

    private ShootEvent mShootEvent;

    private Team mTeam;

    public ReboundDialogListener(GameActivity activity, ShootEvent shootEvent, Team team) {
        mActivity = activity;
        mShootEvent = shootEvent;
        mTeam = team;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        AlertDialog nextDialog = new ReboundEventDialog(mActivity, mShootEvent, mTeam);
        nextDialog.show();
    }

}
