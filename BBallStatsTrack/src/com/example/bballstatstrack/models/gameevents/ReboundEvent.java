package com.example.bballstatstrack.models.gameevents;

import com.example.bballstatstrack.models.Player;
import com.example.bballstatstrack.models.Team;

public class ReboundEvent extends GameEvent
{
    ReboundType mReboundType;

    public ReboundEvent( ReboundType type, Player player, Team team )
    {
        super( Event.REBOUND, player, team );
        mReboundType = type;
        resolveEvent();
        resolveEvent();
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
    }
}
