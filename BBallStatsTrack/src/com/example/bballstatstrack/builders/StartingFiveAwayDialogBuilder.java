package com.example.bballstatstrack.builders;

import com.example.bballstatstrack.activities.GameActivity;
import com.example.bballstatstrack.listeners.TeamAwayStartersConfirmationListener;
import com.example.bballstatstrack.models.Team;

import android.app.AlertDialog;

public class StartingFiveAwayDialogBuilder extends StartingFiveDialogBuilder
{

    public StartingFiveAwayDialogBuilder( GameActivity activity, Team team )
    {
        super( activity, team );
    }

    @Override
    public AlertDialog create()
    {
        AlertDialog dialog = super.create();
        dialog.show();
        dialog.getButton( AlertDialog.BUTTON_POSITIVE ).setOnClickListener(
                new TeamAwayStartersConfirmationListener( mActivity, mTeam, dialog, mSelectedPlayers ) );
        return dialog;
    }
}
