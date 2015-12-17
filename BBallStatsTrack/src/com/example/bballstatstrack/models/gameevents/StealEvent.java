package com.example.bballstatstrack.models.gameevents;

import com.example.bballstatstrack.models.Player;
import com.example.bballstatstrack.models.Team;

public class StealEvent extends GameEvent
{

    public StealEvent( Player player, Team team )
    {
        super( Event.STEAL, player, team );
        resolveEvent();
    }

    @Override
    public void resolveEvent()
    {
        mPlayer.makeSteal();
    }

}
