package com.example.bballstatstrack.dialogs;

import com.example.bballstatstrack.R;
import com.example.bballstatstrack.activities.GameActivity;
import com.example.bballstatstrack.listeners.FoulButtonDialogListener;
import com.example.bballstatstrack.listeners.cancel.UnpauseGameOnCancelListener;
import com.example.bballstatstrack.models.Player;
import com.example.bballstatstrack.models.Team;

import android.widget.Button;

public class FoulButtonDialog extends MultipleButtonsDialog
{
    public FoulButtonDialog( GameActivity activity )
    {
        super( activity, R.string.foul_dialog_player_question );
        boolean isAlreadyPaused = activity.isTimerStopped();
        setOnCancelListener( new UnpauseGameOnCancelListener( activity, isAlreadyPaused ) );

        Team team = activity.getGame().getTeamWithoutPossession();
        for( Player player : team.getInGamePlayers() )
        {
            Button button = new Button( activity );
            button.setText( player.toString() );
            button.setOnClickListener( new FoulButtonDialogListener( activity, FoulButtonDialog.this, team, player ) );
            mDialogView.addView( button );
        }

        activity.startEvent();
        if( isAlreadyPaused )
        {
            return;
        }
        activity.timerStop();
    }

}