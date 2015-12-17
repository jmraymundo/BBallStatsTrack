package com.example.bballstatstrack.models.gameevents;

import com.example.bballstatstrack.models.Player;
import com.example.bballstatstrack.models.Team;
import com.example.bballstatstrack.models.gameevents.exceptions.GameEventException;

public class BlockEvent extends GameEvent
{

    public BlockEvent( Player player, Team team )
    {
        super( Event.BLOCK, player, team );
        resolveEvent();
    }

    @Override
    public void append( GameEvent appendedEvent ) throws GameEventException
    {
        if( appendedEvent instanceof ReboundEvent )
        {
            mAppended = appendedEvent;
            return;
        }
        else
        {
            super.append( appendedEvent );
        }
    }

    @Override
    public void resolveEvent()
    {
        mPlayer.makeBlock();
    }
}
