package com.example.bballstatstrack.models.gameevents;

import com.example.bballstatstrack.models.Player;
import com.example.bballstatstrack.models.Team;

public class SubstitutionEvent extends GameEvent
{
    Player mNewPlayer;

    public SubstitutionEvent( Player playerOut, Player playerIn, Team team )
    {
        super( Event.SUBSTITUTION, playerOut, team );
        mNewPlayer = playerIn;
        resolveEvent();
    }

    @Override
    public void resolveEvent()
    {
        mTeam.substitutePlayer( mNewPlayer, mPlayer );
    }

}
