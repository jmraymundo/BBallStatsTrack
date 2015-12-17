package com.example.bballstatstrack.models.gameevents;

import com.example.bballstatstrack.models.Player;
import com.example.bballstatstrack.models.Team;

public class FoulEvent extends GameEvent
{
    private FoulType mType;

    public FoulEvent( FoulType type, Player player, Team team )
    {
        super( Event.FOUL, player, team );
        mType = type;
        resolveEvent();
    }

    @Override
    public void resolveEvent()
    {
        mPlayer.makeFoul();
        mTeam.addFoul();
    }

    public FoulType getFoulType()
    {
        return mType;
    }

}
