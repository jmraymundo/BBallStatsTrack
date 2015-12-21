package com.example.bballstatstrack.models.gameevents.foulevents;

import com.example.bballstatstrack.models.Player;
import com.example.bballstatstrack.models.Team;
import com.example.bballstatstrack.models.gameevents.FoulEvent;

public class OffensiveFoulEvent extends FoulEvent
{

    public OffensiveFoulEvent( Player player, Team team )
    {
        super( FoulType.OFFENSIVE, player, team );
    }

    @Override
    public String toString()
    {
        return mPlayer.getFullName() + " committed an offensive foul.";
    }
}
