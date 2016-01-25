package com.example.bballstatstrack.listeners;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

import com.example.bballstatstrack.models.Game;

public class ResetShotClockListener implements OnClickListener {
    private Game mGame;

    public ResetShotClockListener(Game game) {
        mGame = game;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        mGame.resetShotClock24();
    }
}
