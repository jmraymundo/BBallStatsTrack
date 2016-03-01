package com.example.bballstatstrack.dialogs;

import com.example.bballstatstrack.R;
import com.example.bballstatstrack.activities.GameActivity;
import com.example.bballstatstrack.listeners.TimeoutEventListener;
import com.example.bballstatstrack.listeners.cancel.TimeoutTeamDeciderDialogCancelListener;
import com.example.bballstatstrack.models.Team;

import android.app.AlertDialog;
import android.app.Dialog;

public class TimeoutTeamDeciderDialog extends AlertDialog
{
    public TimeoutTeamDeciderDialog( GameActivity activity )
    {
        super( activity );
        Team homeTeam = activity.getGame().getHomeTeam();
        Team awayTeam = activity.getGame().getAwayTeam();
        setTitle( R.string.timeout_decider_dialog_question );
        setButton( Dialog.BUTTON_NEGATIVE, homeTeam.getName(), new TimeoutEventListener( activity, homeTeam ) );
        setButton( Dialog.BUTTON_POSITIVE, awayTeam.getName(), new TimeoutEventListener( activity, awayTeam ) );
        setOnCancelListener( new TimeoutTeamDeciderDialogCancelListener( activity ) );
    }
}
