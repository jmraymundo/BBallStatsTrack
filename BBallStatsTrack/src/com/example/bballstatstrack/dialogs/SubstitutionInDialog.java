package com.example.bballstatstrack.dialogs;

import com.example.bballstatstrack.R;
import com.example.bballstatstrack.activities.GameActivity;
import com.example.bballstatstrack.listeners.SubstitutionInListener;
import com.example.bballstatstrack.listeners.cancel.SubstitutionInDialogOnCancelListener;
import com.example.bballstatstrack.models.Player;
import com.example.bballstatstrack.models.PlayerList;
import com.example.bballstatstrack.models.Team;

import android.widget.Button;

public class SubstitutionInDialog extends MultipleButtonsDialog
{
    public SubstitutionInDialog( GameActivity activity, Team team, Player playerOut )
    {
        super( activity, R.string.substitution_dialog_player_out_question );
        setOnCancelListener( new SubstitutionInDialogOnCancelListener( activity, team ) );

        PlayerList inGamePlayers = team.getInGamePlayers();
        PlayerList allPlayers = team.getPlayers();

        for( int index = 0; index < allPlayers.getSize(); index++ )
        {
            Player playerIn = allPlayers.playerAt( index );
            if( inGamePlayers.contains( playerIn ) )
            {
                continue;
            }
            Button button = new Button( activity );
            button.setText( playerIn.toString() );
            button.setOnClickListener(
                    new SubstitutionInListener( activity, SubstitutionInDialog.this, team, playerOut, playerIn ) );
            mDialogView.addView( button );
        }
    }
}