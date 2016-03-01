package com.example.bballstatstrack.models.gameevents;

import com.example.bballstatstrack.models.Player;
import com.example.bballstatstrack.models.Team;

public class BlockEvent extends GameEvent
{

    public BlockEvent( Player player, Team team )
    {
        super( EventType.BLOCK, player, team );
    }

    @Override
    public void append( GameEvent appendedEvent )
    {
        if( mAppended != null )
        {
            mAppended.append( appendedEvent );
            return;
        }
        if( appendedEvent instanceof ReboundEvent )
        {
            mAppended = appendedEvent;
        }
    }

    @Override
    public void resolve()
    {
        mPlayer.makeBlock();
        if( mAppended != null )
        {
            mAppended.resolve();
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
