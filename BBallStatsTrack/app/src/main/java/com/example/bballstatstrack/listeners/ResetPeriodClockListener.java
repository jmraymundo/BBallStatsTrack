package com.example.bballstatstrack.listeners;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

import com.example.bballstatstrack.models.Game;

public class ResetPeriodClockListener implements OnClickListener {
    private Game mGame;

    public ResetPeriodClockListener(Game game) {
        mGame = game;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        mGame.resetPeriodLog();
        mGame.resetPeriodClock();
    }
}
