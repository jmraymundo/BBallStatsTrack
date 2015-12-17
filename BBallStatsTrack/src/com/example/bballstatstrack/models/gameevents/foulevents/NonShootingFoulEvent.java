package com.example.bballstatstrack.models.gameevents.foulevents;

import com.example.bballstatstrack.models.Player;
import com.example.bballstatstrack.models.Team;
import com.example.bballstatstrack.models.gameevents.FoulEvent;
import com.example.bballstatstrack.models.gameevents.GameEvent;
import com.example.bballstatstrack.models.gameevents.ShootEvent;
import com.example.bballstatstrack.models.gameevents.GameEvent.FoulType;
import com.example.bballstatstrack.models.gameevents.GameEvent.NonShootingFoulType;
import com.example.bballstatstrack.models.gameevents.exceptions.GameEventException;

public class NonShootingFoulEvent extends FoulEvent
{

    private NonShootingFoulType mNonShootingFoulType;

    public NonShootingFoulEvent( NonShootingFoulType type, Player player, Team team )
    {
        super( FoulType.NON_SHOOTING, player, team );
        mNonShootingFoulType = type;
    }

    @Override
    public void append( GameEvent appendedEvent ) throws GameEventException
    {
        if( appendedEvent instanceof ShootEvent )
        {
            switch( mNonShootingFoulType )
            {
                case NON_PENALTY:
                    throw new GameEventException( this, mNonShootingFoulType );
                case PENALTY:
                    mAppended = appendedEvent;
                    return;
            }
        }
        else
        {
            super.append( appendedEvent );
        }
    }

}
