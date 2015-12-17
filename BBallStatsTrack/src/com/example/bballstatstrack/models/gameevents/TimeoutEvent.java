package com.example.bballstatstrack.models.gameevents;

import com.example.bballstatstrack.models.Player;
import com.example.bballstatstrack.models.Team;
import com.example.bballstatstrack.models.gameevents.exceptions.GameEventException;

public class TimeoutEvent extends GameEvent
{

    public TimeoutEvent( Team team ) throws GameEventException
    {
        super( Event.TIME_OUT, null, team );
        resolveEvent();
    }

    @Override
    public void resolveEvent() throws GameEventException
    {
        int timeoutRemaining = mTeam.getTimeOuts();
        if( timeoutRemaining <= 0 )
        {
            throw new GameEventException( this );
        }
        mTeam.useTimeOut();
    }

}
