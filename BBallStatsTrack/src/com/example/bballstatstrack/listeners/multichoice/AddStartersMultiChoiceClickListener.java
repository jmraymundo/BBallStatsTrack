package com.example.bballstatstrack.listeners.multichoice;

import com.example.bballstatstrack.models.PlayerList;

import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;

public class AddStartersMultiChoiceClickListener implements OnMultiChoiceClickListener
{
    private PlayerList mPlayers;

    public AddStartersMultiChoiceClickListener( PlayerList players )
    {
        mPlayers = players;
    }

    @Override
    public void onClick( DialogInterface dialog, int index, boolean isChecked )
    {
        mPlayers.setStarter( index, isChecked );
    }
}