package com.example.bballstatstrack.listeners;

import com.example.bballstatstrack.activities.GameActivity;
import com.example.bballstatstrack.dialogs.FoulTypeDialog;
import com.example.bballstatstrack.models.Player;
import com.example.bballstatstrack.models.Team;

import android.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;

public class FoulButtonDialogListener implements OnClickListener
{

    private GameActivity mActivity;

    private AlertDialog mDialog;

    private Team mTeam;

    private Player mPlayer;

    public FoulButtonDialogListener( GameActivity activity, AlertDialog dialog, Team team, Player player )
    {
        mActivity = activity;
        mDialog = dialog;
        mTeam = team;
        mPlayer = player;
    }

    @Override
    public void onClick( View v )
    {
        AlertDialog dialog = new FoulTypeDialog( mActivity, mTeam, mPlayer );
        dialog.show();
        mDialog.dismiss();
    }
}
