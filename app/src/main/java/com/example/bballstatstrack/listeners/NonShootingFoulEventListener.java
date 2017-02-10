package com.example.bballstatstrack.listeners;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

import com.example.bballstatstrack.activities.GameActivity;
import com.example.bballstatstrack.dialogs.PenaltyDialog;
import com.example.bballstatstrack.models.Player;
import com.example.bballstatstrack.models.Team;
import com.example.bballstatstrack.models.gameevents.GameEvent.NonShootingFoulType;
import com.example.bballstatstrack.models.gameevents.foulevents.NonShootingFoulEvent;

public class NonShootingFoulEventListener implements OnClickListener {
    private GameActivity mActivity;

    private Team mTeam;

    private Player mPlayer;

    public NonShootingFoulEventListener(GameActivity activity, Team team, Player player) {
        mActivity = activity;
        mTeam = team;
        mPlayer = player;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        dialog.dismiss();
        boolean isPenalty = mActivity.isPenalty(mTeam);
        NonShootingFoulEvent event = new NonShootingFoulEvent(
                isPenalty ? NonShootingFoulType.PENALTY : NonShootingFoulType.NON_PENALTY, mPlayer, mTeam);
        if (isPenalty) {
            AlertDialog nextDialog = new PenaltyDialog(mActivity);
            nextDialog.show();
        }
        mActivity.addNewEvent(event);
    }
}
