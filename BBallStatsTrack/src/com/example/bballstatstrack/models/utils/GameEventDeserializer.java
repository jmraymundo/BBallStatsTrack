package com.example.bballstatstrack.models.utils;

import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.bballstatstrack.models.Game;
import com.example.bballstatstrack.models.Game.GameStats;
import com.example.bballstatstrack.models.Player;
import com.example.bballstatstrack.models.Team;
import com.example.bballstatstrack.models.game.GameLog;
import com.example.bballstatstrack.models.gameevents.AssistEvent;
import com.example.bballstatstrack.models.gameevents.BlockEvent;
import com.example.bballstatstrack.models.gameevents.FoulEvent;
import com.example.bballstatstrack.models.gameevents.GameEvent;
import com.example.bballstatstrack.models.gameevents.GameEvent.EventType;
import com.example.bballstatstrack.models.gameevents.GameEvent.FoulType;
import com.example.bballstatstrack.models.gameevents.GameEvent.NonShootingFoulType;
import com.example.bballstatstrack.models.gameevents.GameEvent.ReboundType;
import com.example.bballstatstrack.models.gameevents.GameEvent.ShotClass;
import com.example.bballstatstrack.models.gameevents.GameEvent.ShotType;
import com.example.bballstatstrack.models.gameevents.GameEvent.TurnoverType;
import com.example.bballstatstrack.models.gameevents.ReboundEvent;
import com.example.bballstatstrack.models.gameevents.ShootEvent;
import com.example.bballstatstrack.models.gameevents.StealEvent;
import com.example.bballstatstrack.models.gameevents.SubstitutionEvent;
import com.example.bballstatstrack.models.gameevents.TimeoutEvent;
import com.example.bballstatstrack.models.gameevents.TurnoverEvent;
import com.example.bballstatstrack.models.gameevents.foulevents.NonShootingFoulEvent;
import com.example.bballstatstrack.models.gameevents.foulevents.OffensiveFoulEvent;
import com.example.bballstatstrack.models.gameevents.foulevents.ShootingFoulEvent;

import android.util.SparseArray;

public class GameEventDeserializer
{
    private Game mGame;

    public GameEventDeserializer( Game game )
    {
        mGame = game;
    }

    public GameEvent getGameEventFromJSONObject( JSONObject jsonGameEvent ) throws JSONException
    {
        if( null == jsonGameEvent )
        {
            return null;
        }
        EventType eventType = ( EventType ) jsonGameEvent.get( GameEvent.EVENT_TYPE );
        JSONObject jsonAppended = null;
        if( !jsonGameEvent.isNull( GameEvent.APPENDED ) )
        {
            jsonAppended = jsonGameEvent.getJSONObject( GameEvent.APPENDED );
        }
        GameEvent event;
        UUID teamID = ( UUID ) jsonGameEvent.get( GameEvent.TEAM_ID );
        Team team = getTeamFromID( teamID );
        int playerNumber = jsonGameEvent.getInt( GameEvent.PLAYER_NUMBER );
        Player player = team.getPlayers().get( playerNumber );
        switch( eventType )
        {
            case SHOOT:
                event = getShootEventFromJSON( jsonGameEvent, player, team );
                break;
            case REBOUND:
                event = getReboundEventFromJSON( jsonGameEvent, player, team );
                break;
            case ASSIST:
                event = getAssistEvent( player, team );
                break;
            case TURNOVER:
                event = getTurnoverEventFromJSON( jsonGameEvent, player, team );
                break;
            case STEAL:
                event = getStealEvent( player, team );
                break;
            case BLOCK:
                event = getBlockEvent( player, team );
                break;
            case FOUL:
                event = getFoulEventFromJSON( jsonGameEvent, player, team );
                break;
            case SUBSTITUTION:
                event = getSubstitutionEventFromJSON( jsonGameEvent, player, team );
                break;
            case TIME_OUT:
                event = getTimeoutEvent( team );
                break;
            default:
                event = null;
        }
        if( null != jsonAppended )
        {
            event.append( getGameEventFromJSONObject( jsonAppended ) );
        }
        return event;
    }

    private GameEvent getTimeoutEvent( Team team )
    {
        return new TimeoutEvent( team );
    }

    private GameEvent getSubstitutionEventFromJSON( JSONObject jsonGameEvent, Player player, Team team )
            throws JSONException
    {
        int newNumber = jsonGameEvent.getInt( SubstitutionEvent.NEW_PLAYER_NUMBER );
        Player newPlayer = team.getPlayers().get( newNumber );
        return new SubstitutionEvent( player, newPlayer, team );
    }

    private GameEvent getBlockEvent( Player player, Team team )
    {
        return new BlockEvent( player, team );
    }

    private GameEvent getStealEvent( Player player, Team team )
    {
        return new StealEvent( player, team );
    }

    private GameEvent getTurnoverEventFromJSON( JSONObject jsonGameEvent, Player player, Team team )
            throws JSONException
    {
        TurnoverType turnoverType = ( TurnoverType ) jsonGameEvent.get( TurnoverEvent.TURNOVER_TYPE );
        return new TurnoverEvent( turnoverType, player, team );
    }

    private GameEvent getAssistEvent( Player player, Team team )
    {
        return new AssistEvent( player, team );
    }

    private GameEvent getReboundEventFromJSON( JSONObject jsonGameEvent, Player player, Team team ) throws JSONException
    {
        ReboundType reboundType = ( ReboundType ) jsonGameEvent.get( ReboundEvent.REBOUND_TYPE );
        return new ReboundEvent( reboundType, player, team );
    }

    private GameEvent getShootEventFromJSON( JSONObject jsonGameEvent, Player player, Team team ) throws JSONException
    {
        ShotClass shotClass = ( ShotClass ) jsonGameEvent.get( ShootEvent.SHOT_CLASS );
        ShotType shotType = ( ShotType ) jsonGameEvent.get( ShootEvent.SHOT_TYPE );
        return new ShootEvent( shotClass, shotType, player, team );
    }

    private GameEvent getFoulEventFromJSON( JSONObject jsonGameEvent, Player player, Team team ) throws JSONException
    {
        FoulType foulType = ( FoulType ) jsonGameEvent.get( FoulEvent.FOUL_TYPE );
        switch( foulType )
        {
            case SHOOTING:
                return getShootingFoulEventFromJSON( jsonGameEvent, player, team );
            case NON_SHOOTING:
                NonShootingFoulType nonShootingFoulType = ( NonShootingFoulType ) jsonGameEvent
                        .get( NonShootingFoulEvent.NON_SHOOTING_FOUL_TYPE );
                return new NonShootingFoulEvent( nonShootingFoulType, player, team );
            case OFFENSIVE:
                return new OffensiveFoulEvent( player, team );
        }
        return null;
    }

    private GameEvent getShootingFoulEventFromJSON( JSONObject jsonGameEvent, Player player, Team team )
            throws JSONException
    {
        int shooterNumber = jsonGameEvent.getInt( ShootingFoulEvent.SHOOTER );
        Team otherTeam = ( team.equals( mGame.getAwayTeam() ) ? mGame.getHomeTeam() : mGame.getHomeTeam() );
        Player shooter = otherTeam.getPlayers().get( shooterNumber );
        return new ShootingFoulEvent( player, team, shooter );
    }

    private Team getTeamFromID( UUID teamID )
    {
        if( teamID.equals( mGame.getHomeTeam().getID() ) )
        {
            return mGame.getHomeTeam();
        }
        else if( teamID.equals( mGame.getAwayTeam().getID() ) )
        {
            return mGame.getAwayTeam();
        }
        else
        {
            return null;
        }
    }

    public GameLog getGameLog( JSONObject game ) throws JSONException
    {
        GameLog gameLog = new GameLog();
        JSONArray gameLogArray = game.getJSONArray( GameStats.GAME_LOG.toString() );
        for( int gameIndex = 0; gameIndex < gameLogArray.length(); gameIndex++ )
        {
            JSONArray periodLogArray = gameLogArray.getJSONArray( gameIndex );
            SparseArray< GameEvent > periodLog = getPeriodLogFromJSONArray( periodLogArray );
            gameLog.put( gameIndex, periodLog );
        }
        return gameLog;
    }

    private SparseArray< GameEvent > getPeriodLogFromJSONArray( JSONArray periodLogArray ) throws JSONException
    {
        SparseArray< GameEvent > periodLog = new SparseArray< GameEvent >();
        for( int periodIndex = 0; periodIndex < periodLogArray.length(); periodIndex++ )
        {
            JSONArray jsonGameClockEventPair = periodLogArray.getJSONArray( periodIndex );
            int gameEventClock = jsonGameClockEventPair.getInt( 0 );
            JSONObject jsonGameEvent = jsonGameClockEventPair.getJSONObject( 1 );
            GameEvent gameEvent = getGameEventFromJSONObject( jsonGameEvent );
            periodLog.put( gameEventClock, gameEvent );
        }
        return periodLog;
    }
}
