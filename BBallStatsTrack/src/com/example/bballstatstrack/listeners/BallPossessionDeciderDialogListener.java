package com.example.bballstatstrack.listeners;

import com.example.bballstatstrack.activities.GameActivity;
import com.example.bballstatstrack.models.Team;

import android.content.DialogInterface;

public class BallPossessionDeciderDialogListener implements DialogInterface.OnClickListener
{
    private Team mTeam;

    private GameActivity mActivity;

    public BallPossessionDeciderDialogListener( GameActivity game, Team team )
    {
        mActivity = game;
        mTeam = team;
    }

    @Override
    public void onClick( DialogInterface dialog, int which )
    {
        dialog.dismiss();
        mActivity.getGame().setTeamWithPossession( mTeam );
        mActivity.timerStart();
    }
}
