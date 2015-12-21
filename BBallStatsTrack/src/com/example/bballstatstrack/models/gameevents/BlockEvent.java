package com.example.bballstatstrack.models.gameevents;

import com.example.bballstatstrack.models.Player;
import com.example.bballstatstrack.models.Team;
import com.example.bballstatstrack.models.gameevents.exceptions.GameEventException;

public class BlockEvent extends GameEvent
{

    public BlockEvent( Player player, Team team )
    {
        super( Event.BLOCK, player, team );
    }

    @Override
    public void append( GameEvent appendedEvent ) throws GameEventException
    {
        if( mAppended != null )
        {
            mAppended.append( appendedEvent );
            return;
        }
        if( appendedEvent instanceof ReboundEvent )
        {
            mAppended = appendedEvent;
            return;
        }
        super.append( appendedEvent );
    }

    @Override
    public void resolveEvent()
    {
        mPlayer.makeBlock();
        if( mAppended != null )
        {
            mAppended.resolveEvent();
        }
    }

    @Override
    public String toString()
    {
        String output = "Blocked by " + mPlayer.getFullName() + ". ";
        if( mAppended != null )
        {
            output = output.concat( mAppended.toString() );
        }
        return output.trim();
    }
}
