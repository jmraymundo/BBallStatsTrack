package com.example.bballstatstrack.dialogs;

import com.example.bballstatstrack.R;
import com.example.bballstatstrack.activities.GameActivity;
import com.example.bballstatstrack.listeners.NonShootingFoulEventListener;
import com.example.bballstatstrack.listeners.ShootingFoulEventListener;
import com.example.bballstatstrack.listeners.cancel.UnpauseGameOnCancelListener;
import com.example.bballstatstrack.models.Player;
import com.example.bballstatstrack.models.Team;

import android.app.Dialog;

public class FoulTypeDialog extends YesNoDialog
{
    public FoulTypeDialog( GameActivity activity, Team team, Player player )
    {
        super( activity, R.string.foul_dialog_shooting_question );
        setButton( Dialog.BUTTON_POSITIVE, getContext().getText( R.string.yes ),
                new ShootingFoulEventListener( activity, team, player ) );
        setButton( Dialog.BUTTON_NEGATIVE, getContext().getText( R.string.no ),
                new NonShootingFoulEventListener( activity, team, player ) );
        setOnCancelListener( new UnpauseGameOnCancelListener( activity ) );
    }
}
