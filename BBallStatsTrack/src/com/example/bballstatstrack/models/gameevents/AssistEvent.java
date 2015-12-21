package com.example.bballstatstrack.models.gameevents;

import com.example.bballstatstrack.models.Player;
import com.example.bballstatstrack.models.Team;

public class AssistEvent extends GameEvent
{

    public AssistEvent( Player player, Team team )
    {
        super( Event.ASSIST, player, team );
    }

    @Override
    public void resolveEvent()
    {
        mPlayer.makeAssist();
    }

    @Override
    public String toString()
    {
        return "Assist by " + mPlayer.getFullName() + ".";
    }
}
