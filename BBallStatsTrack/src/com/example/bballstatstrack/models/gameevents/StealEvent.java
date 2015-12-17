package com.example.bballstatstrack.models.gameevents;

import com.example.bballstatstrack.models.Player;
import com.example.bballstatstrack.models.Team;
import com.example.bballstatstrack.models.gameevents.exceptions.GameEventException;

public class StealEvent extends GameEvent
{

    public StealEvent( Player player, Team team )
    {
        super( Event.STEAL, player, team );
    }

    @Override
    public void resolveEvent()
    {
        mPlayer.makeSteal();
        if( mAppended != null )
        {
            mAppended.resolveEvent();
        }
    }

}
