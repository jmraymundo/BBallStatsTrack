package com.example.bballstatstrack.listeners;

import android.app.AlertDialog;
import android.view.View.OnClickListener;

import com.example.bballstatstrack.activities.GameActivity;
import com.example.bballstatstrack.models.Team;

public abstract class TeamStartersConfirmationListener implements OnClickListener {
    protected final Team mTeam;

    protected final AlertDialog mDialog;

    protected GameActivity mActivity;

    public TeamStartersConfirmationListener(GameActivity activity, Team team, AlertDialog dialog) {
        mActivity = activity;
        mTeam = team;
        mDialog = dialog;
    }
}
