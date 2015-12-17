package com.example.bballstatstrack.models.gameevents;

import java.util.ArrayList;

import org.junit.Test;

import com.example.bballstatstrack.models.Player;
import com.example.bballstatstrack.models.Team;
import com.example.bballstatstrack.models.gameevents.GameEvent.ShootingFoulType;
import com.example.bballstatstrack.models.gameevents.GameEvent.ShotClass;
import com.example.bballstatstrack.models.gameevents.GameEvent.ShotType;
import com.example.bballstatstrack.models.gameevents.exceptions.GameEventException;
import com.example.bballstatstrack.models.gameevents.foulevents.ShootingFoulEvent;

public class TestGameEventException
{
    @Test
    public void test()
    {
        Player player = new Player( 0, "John", "Doe" );
        Team team = new Team( "Team name", new ArrayList< Player >() );
        ShootingFoulEvent event = new ShootingFoulEvent( ShootingFoulType.NO_AND1, player, team );
        try
        {
            event.append( new ShootEvent( ShotClass.FG_3PT, ShotType.MADE, player, team ) );
        }
        catch( GameEventException e )
        {
            System.out.println( e.getMessage() );
        }
    }
}
