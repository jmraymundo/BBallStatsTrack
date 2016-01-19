package com.example.bballstatstrack.listeners;

import com.example.bballstatstrack.R;
import com.example.bballstatstrack.activities.GameActivity;
import com.example.bballstatstrack.models.PlayerList;
import com.example.bballstatstrack.models.Team;

import android.app.AlertDialog;
import android.view.View;
import android.widget.Toast;

public class TeamAwayStartersConfirmationListener extends TeamStartersConfirmationListener
{
    public TeamAwayStartersConfirmationListener( GameActivity activity, Team team, AlertDialog dialog,
            PlayerList selectedPlayers )
    {
        super( activity, team, dialog, selectedPlayers );
    }

    @Override
    public void onClick( View v )
    {
        if( mSelectedPlayers.getSize() != 5 )
        {
            Toast.makeText( mActivity, mActivity.getResources().getString( R.string.need_five_players ),
                    Toast.LENGTH_SHORT ).show();
        }
        else
        {
            mTeam.setStarters( mSelectedPlayers );
            mActivity.startNewGame();
            mDialog.dismiss();
        }
    }
}
