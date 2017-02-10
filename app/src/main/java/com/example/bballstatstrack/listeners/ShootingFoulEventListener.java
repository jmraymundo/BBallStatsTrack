package com.example.bballstatstrack.listeners;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

import com.example.bballstatstrack.activities.GameActivity;
import com.example.bballstatstrack.dialogs.ShootButtonDialog;
import com.example.bballstatstrack.models.Player;
import com.example.bballstatstrack.models.Team;
import com.example.bballstatstrack.models.gameevents.foulevents.ShootingFoulEvent;

public class ShootingFoulEventListener implements OnClickListener {
    private GameActivity mActivity;

    private Team mTeam;

    private Player mPlayer;

    public ShootingFoulEventListener(GameActivity activity, Team team, Player player) {
        mActivity = activity;
        mTeam = team;
        mPlayer = player;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        dialog.dismiss();
        ShootingFoulEvent event = new ShootingFoulEvent(mPlayer, mTeam);
        AlertDialog nextDialog = new ShootButtonDialog(mActivity, event);
        nextDialog.show();
    }

}
