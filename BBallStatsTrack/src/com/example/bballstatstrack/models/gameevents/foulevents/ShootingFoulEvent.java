package com.example.bballstatstrack.models.gameevents.foulevents;

import com.example.bballstatstrack.models.Player;
import com.example.bballstatstrack.models.Team;
import com.example.bballstatstrack.models.gameevents.FoulEvent;
import com.example.bballstatstrack.models.gameevents.GameEvent;
import com.example.bballstatstrack.models.gameevents.ShootEvent;
import com.example.bballstatstrack.models.gameevents.GameEvent.FoulType;
import com.example.bballstatstrack.models.gameevents.GameEvent.ShootingFoulType;
import com.example.bballstatstrack.models.gameevents.exceptions.GameEventException;

public class ShootingFoulEvent extends FoulEvent
{

    private ShootingFoulType mShootingFoulType;

    public ShootingFoulEvent( ShootingFoulType type, Player player, Team team )
    {
        super( FoulType.SHOOTING, player, team );
        mShootingFoulType = type;
    }

    @Override
    public void append( GameEvent appendedEvent ) throws GameEventException
    {
        if( appendedEvent instanceof ShootEvent )
        {
            switch( mShootingFoulType )
            {
                case NO_AND1:
                    throw new GameEventException( this, mShootingFoulType );
                case AND1:
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
