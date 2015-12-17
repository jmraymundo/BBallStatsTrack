package com.example.bballstatstrack.models.gameevents;

import com.example.bballstatstrack.models.Player;
import com.example.bballstatstrack.models.Team;
import com.example.bballstatstrack.models.gameevents.exceptions.GameEventException;

public class FoulEvent extends GameEvent
{
    private FoulType mType;

    public FoulEvent( FoulType type, Player player, Team team )
    {
        super( Event.FOUL, player, team );
        mType = type;
    }

    @Override
    public void resolveEvent()
    {
        mPlayer.makeFoul();
        mTeam.addFoul();
        if( mAppended != null )
        {
            mAppended.resolveEvent();
        }
    }

    public FoulType getFoulType()
    {
        return mType;
    }

}
