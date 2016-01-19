package com.example.bballstatstrack.listeners.multichoice;

import com.example.bballstatstrack.models.PlayerList;

import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;

public class AddStartersMultiChoiceClickListener implements OnMultiChoiceClickListener
{
    private PlayerList mPlayers;

    private PlayerList mStarters;

    public AddStartersMultiChoiceClickListener( PlayerList starters, PlayerList players )
    {
        mStarters = starters;
        mPlayers = players;
    }

    @Override
    public void onClick( DialogInterface dialog, int index, boolean isChecked )
    {
        if( isChecked )
        {
            mStarters.addPlayer( mPlayers.playerAt( index ) );
        }
        else
        {
            mStarters.removePlayer( mPlayers.playerAt( index ) );
        }
    }
}