package com.example.bballstatstrack.listeners;

import com.example.bballstatstrack.activities.GameActivity;
import com.example.bballstatstrack.dialogs.SubstitutionInDialog;
import com.example.bballstatstrack.models.Player;
import com.example.bballstatstrack.models.Team;

import android.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;

public class SubstitutionOutListener implements OnClickListener
{
    private GameActivity mActivity;

    private AlertDialog mDialog;

    private Team mTeam;

    private Player mPlayerOut;

    public SubstitutionOutListener( GameActivity activity, AlertDialog dialog, Team team, Player playerOut )
    {
        mActivity = activity;
        mDialog = dialog;
        mTeam = team;
        mPlayerOut = playerOut;
    }

    @Override
    public void onClick( View v )
    {
        showSubstitutionInDialog( mTeam, mPlayerOut );
        mDialog.dismiss();
    }

    private void showSubstitutionInDialog( Team team, Player playerOut )
    {
        AlertDialog dialog = new SubstitutionInDialog( mActivity, mTeam, mPlayerOut );
        dialog.show();
    }

}
