package com.example.bballstatstrack.listeners;

import com.example.bballstatstrack.activities.GameActivity;
import com.example.bballstatstrack.models.PlayerList;
import com.example.bballstatstrack.models.Team;

import android.app.AlertDialog;
import android.view.View.OnClickListener;

public abstract class TeamStartersConfirmationListener implements OnClickListener
{
    protected final Team mTeam;

    protected final AlertDialog mDialog;

    protected final PlayerList mSelectedPlayers;

    protected GameActivity mActivity;

    public TeamStartersConfirmationListener( GameActivity activity, Team team, AlertDialog dialog,
            PlayerList selectedPlayers )
    {
        mActivity = activity;
        mTeam = team;
        mDialog = dialog;
        mSelectedPlayers = selectedPlayers;
    }
}
