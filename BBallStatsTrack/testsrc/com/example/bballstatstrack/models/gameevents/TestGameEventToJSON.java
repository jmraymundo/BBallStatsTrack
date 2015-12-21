package com.example.bballstatstrack.models.gameevents;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import com.example.bballstatstrack.models.Game;
import com.example.bballstatstrack.models.Player;
import com.example.bballstatstrack.models.Team;
import com.example.bballstatstrack.models.gameevents.GameEvent.ReboundType;
import com.example.bballstatstrack.models.gameevents.GameEvent.ShootingFoulType;
import com.example.bballstatstrack.models.gameevents.GameEvent.ShotClass;
import com.example.bballstatstrack.models.gameevents.GameEvent.ShotType;
import com.example.bballstatstrack.models.gameevents.GameEvent.TurnoverType;
import com.example.bballstatstrack.models.gameevents.exceptions.GameEventException;
import com.example.bballstatstrack.models.gameevents.foulevents.ShootingFoulEvent;

import android.util.Log;
import junit.framework.TestCase;

public class TestGameEventToJSON extends TestCase
{

    Player player;

    Player player2;

    List< Player > teamArray;

    List< Player > teamArray2;

    Team team;

    Team team2;

    Game game;

    Player player3;

    Player player4;

    public void setUp() throws Exception
    {
        player = new Player( 0, "Michael Jordan" );
        player2 = new Player( 1, "LeBron James" );
        player3 = new Player( 2, "Kobe Bryant" );
        player4 = new Player( 3, "Kevin Durant" );
        teamArray = new ArrayList< Player >();
        teamArray.add( player );
        teamArray.add( player3 );
        teamArray2 = new ArrayList< Player >();
        teamArray2.add( player2 );
        teamArray2.add( player4 );
        team = new Team( "Team name", teamArray );
        team2 = new Team( "Team name2", teamArray2 );
        game = new Game( 12, 24, team, team2 );
    };

    @Test
    public void testGameEventToJSON() throws JSONException, GameEventException
    {

        ShootingFoulEvent event = new ShootingFoulEvent( ShootingFoulType.NO_AND1, player, team, player2 );
        JSONObject jsonEvent = event.toJSON();
        Log.d( "POGI", jsonEvent.toString() );
        GameEvent copyEvent = game.getGameEventFromJSONObject( jsonEvent );
        Log.d( "POGI", copyEvent.toString() );
    }

    @Test
    public void testAppend() throws JSONException, GameEventException
    {
        ShootEvent event = new ShootEvent( ShotClass.FG_3PT, ShotType.MISSED, player, team );
        event.append( new BlockEvent( player2, team2 ) );
        event.append( new ReboundEvent( ReboundType.DEFENSIVE, player2, team2 ) );
        JSONObject jsonEvent = event.toJSON();
        Log.d( "POGI", jsonEvent.toString() );
        GameEvent copyEvent = game.getGameEventFromJSONObject( jsonEvent );
        Log.d( "POGI", copyEvent.toString() );
    }

    @Test
    public void testPeriodLog() throws JSONException, GameEventException
    {
        for( int x = 0; x < 10; x++ )
        {
            game.updateTime();
        }
        ShootEvent event = new ShootEvent( ShotClass.FG_3PT, ShotType.MISSED, player, team );
        event.append( new BlockEvent( player2, team2 ) );
        event.append( new ReboundEvent( ReboundType.DEFENSIVE, player2, team2 ) );
        Log.d( "POGI", event.toString() );
        game.addNewEvent( event );
        for( int x = 0; x < 15; x++ )
        {
            game.updateTime();
        }
        ShootEvent event2 = new ShootEvent( ShotClass.FG_2PT, ShotType.MADE, player, team );
        event2.append( new AssistEvent( player3, team ) );
        game.addNewEvent( event2 );
        Log.d( "POGI", event2.toString() );
        for( int x = 0; x < 4; x++ )
        {
            game.updateTime();
        }
        TurnoverEvent event3 = new TurnoverEvent( TurnoverType.STEAL, player4, team2 );
        event3.append( new StealEvent( player3, team ) );
        Log.d( "POGI", event3.toString() );
        game.addNewEvent( event3 );

        JSONObject jsonEvent = game.toJSON();
        Log.d( "POGI", jsonEvent.toString() );
    }

}
