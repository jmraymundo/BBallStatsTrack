package com.example.bballstatstrack.listeners;

import com.example.bballstatstrack.models.Game;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

public class ResetShotClockListener implements OnClickListener
{
    private Game mGame;

    public ResetShotClockListener( Game game )
    {
        mGame = game;
    }

    @Override
    public void onClick( DialogInterface dialog, int which )
    {
        mGame.resetShotClock24();
    }
}
