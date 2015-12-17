package com.example.bballstatstrack.models.gameevents.exceptions;

import com.example.bballstatstrack.models.gameevents.GameEvent;
import com.example.bballstatstrack.models.gameevents.GameEvent.NonShootingFoulType;
import com.example.bballstatstrack.models.gameevents.GameEvent.ShootingFoulType;
import com.example.bballstatstrack.models.gameevents.GameEvent.ShotType;
import com.example.bballstatstrack.models.gameevents.TimeoutEvent;

public class GameEventException extends Exception
{
    public GameEventException( GameEvent sourceEvent, GameEvent appendedEvent )
    {
        super( "Cannot append " + appendedEvent.getClass().getSimpleName() + " to "
                + sourceEvent.getClass().getSimpleName() + "." );
    }

    public GameEventException( GameEvent sourceEvent, NonShootingFoulType nonShootingFoulType )
    {
        super( "Cannot append ShootEvent to " + sourceEvent.getClass().getSimpleName()
                + " when NonShootingFoulType is equal to " + nonShootingFoulType.name() );
    }

    public GameEventException( GameEvent sourceEvent, ShootingFoulType shootingFoulType )
    {
        super( "Cannot append ShootEvent to " + sourceEvent.getClass().getSimpleName()
                + " when ShootingFoulType is equal to " + shootingFoulType.name() );
    }

    public GameEventException( GameEvent sourceEvent, ShotType shotType )
    {
        super( "Cannot append BlockEvent to " + sourceEvent.getClass().getSimpleName() + " when ShotType is equal to "
                + shotType.name() );
    }

    public GameEventException( TimeoutEvent timeoutEvent )
    {
        super( "Cannot resolve TimeoutEvent. Not enough timeouts remaining." );
    }

}
