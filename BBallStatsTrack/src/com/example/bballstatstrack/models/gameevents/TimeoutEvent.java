package com.example.bballstatstrack.models.gameevents;

import com.example.bballstatstrack.models.Team;

public class TimeoutEvent extends GameEvent
{

    public TimeoutEvent( Team team )
    {
        super( Event.TIME_OUT, null, team );
    }

    @Override
    public void resolveEvent()
    {
        mTeam.useTimeOut();
        if( mAppended != null )
        {
            mAppended.resolveEvent();
        }
    }

    @Override
    public String toString()
    {
        return " Timeout called by " + mTeam.getName() + ".";
    }

}
