package com.example.bballstatstrack.listeners;

import android.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.bballstatstrack.activities.GameActivity;
import com.example.bballstatstrack.models.Player;
import com.example.bballstatstrack.models.Team;
import com.example.bballstatstrack.models.gameevents.SubstitutionEvent;

public class SubstitutionInListener implements OnClickListener {

    private AlertDialog mDialog;

    private Team mTeam;

    private Player mPlayerOut;

    private Player mPlayerIn;

    private GameActivity mActivity;

    public SubstitutionInListener(GameActivity activity, AlertDialog dialog, Team team, Player playerOut,
                                  Player playerIn) {
        mActivity = activity;
        mDialog = dialog;
        mTeam = team;
        mPlayerOut = playerOut;
        mPlayerIn = playerIn;
    }

    @Override
    public void onClick(View v) {
        SubstitutionEvent event = new SubstitutionEvent(mPlayerOut, mPlayerIn, mTeam);
        mActivity.addNewEvent(event);
        mDialog.dismiss();
    }

}
