package com.example.bballstatstrack.models.gameevents;

import com.example.bballstatstrack.models.Player;
import com.example.bballstatstrack.models.Team;
import com.example.bballstatstrack.models.gameevents.exceptions.GameEventException;

public class ReboundEvent extends GameEvent
{
    ReboundType mReboundType;

    public ReboundEvent( ReboundType type, Player player, Team team )
    {
        super( Event.REBOUND, player, team );
        mReboundType = type;
    }

    @Override
    public void resolveEvent()
    {
        switch( mReboundType )
        {
            case OFFENSIVE:
                mPlayer.makeRebound( true );
                return;
            case DEFENSIVE:
                mPlayer.makeRebound( false );
                return;
            case TEAM_REBOUND:
                mTeam.makeTeamRebound();
                return;
        }
        if( mAppended != null )
        {
            mAppended.resolveEvent();
        }
    }
}
