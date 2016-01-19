package com.example.bballstatstrack.models.utils;

import java.io.IOException;
import java.util.List;

import com.example.bballstatstrack.models.Game;
import com.example.bballstatstrack.models.Game.GameStats;
import com.example.bballstatstrack.models.Player;
import com.example.bballstatstrack.models.Player.PlayerStats;
import com.example.bballstatstrack.models.PlayerList;
import com.example.bballstatstrack.models.Team;
import com.example.bballstatstrack.models.Team.TeamStats;
import com.example.bballstatstrack.models.game.GameLog;
import com.example.bballstatstrack.models.gameevents.FoulEvent;
import com.example.bballstatstrack.models.gameevents.GameEvent;
import com.example.bballstatstrack.models.gameevents.GameEvent.EventType;
import com.example.bballstatstrack.models.gameevents.ReboundEvent;
import com.example.bballstatstrack.models.gameevents.ShootEvent;
import com.example.bballstatstrack.models.gameevents.SubstitutionEvent;
import com.example.bballstatstrack.models.gameevents.TurnoverEvent;
import com.example.bballstatstrack.models.gameevents.foulevents.NonShootingFoulEvent;
import com.example.bballstatstrack.models.gameevents.foulevents.ShootingFoulEvent;

import android.util.JsonWriter;

public class JSONSerializer
{
    public static void writeEventBase( JsonWriter writer, GameEvent gameEvent ) throws IOException
    {
        EventType event = gameEvent.getEventType();
        Player player = gameEvent.getPlayer();
        Team team = gameEvent.getTeam();
        int time = gameEvent.getTime();
        GameEvent appended = gameEvent.getAppended();
        writer.name( GameEvent.EVENT_TYPE ).value( event.toString() );
        writer.name( GameEvent.PLAYER_NUMBER );
        if( player != null )
        {
            writer.value( player.getNumber() );
        }
        else
        {
            writer.nullValue();
        }
        writer.name( GameEvent.TEAM_ID ).value( team.getID().toString() );
        writer.name( GameEvent.TIME ).value( time );
        writer.name( GameEvent.APPENDED );
        if( appended != null )
        {
            writeEvent( writer, appended );
        }
        else
        {
            writer.nullValue();
        }
    }

    public static void writeEventFoul( JsonWriter writer, FoulEvent foulEvent ) throws IOException
    {
        writer.name( FoulEvent.FOUL_TYPE ).value( foulEvent.getFoulType().toString() );
        if( foulEvent instanceof NonShootingFoulEvent )
        {
            writeEventFoulNonShooting( writer, ( NonShootingFoulEvent ) foulEvent );
        }
        else if( foulEvent instanceof ShootingFoulEvent )
        {
            writeEventFoulShooting( writer, ( ShootingFoulEvent ) foulEvent );
        }
    }

    public static void writeEventFoulNonShooting( JsonWriter writer, NonShootingFoulEvent nonShootingFoulEvent )
            throws IOException
    {
        writer.name( NonShootingFoulEvent.NON_SHOOTING_FOUL_TYPE )
                .value( nonShootingFoulEvent.getNonShootingFoulType().toString() );
    }

    public static void writeEventFoulShooting( JsonWriter writer, ShootingFoulEvent shootingFoulEvent )
            throws IOException
    {
        Player shooter = shootingFoulEvent.getShooter();
        Team shooterTeam = shootingFoulEvent.getShooterTeam();
        writer.name( ShootingFoulEvent.SHOOTER ).value( shooter.getNumber() );
        writer.name( ShootingFoulEvent.SHOOTER_TEAM ).value( shooterTeam.getID().toString() );
        writer.name( ShootingFoulEvent.FT_COUNT ).value( shootingFoulEvent.getFTCount() );
    }

    public static void writeEventRebound( JsonWriter writer, ReboundEvent reboundEvent ) throws IOException
    {
        writer.name( ReboundEvent.REBOUND_TYPE ).value( reboundEvent.getReboundType().toString() );
    }

    public static void writeEventShoot( JsonWriter writer, ShootEvent shootEvent ) throws IOException
    {
        writer.name( ShootEvent.SHOT_CLASS ).value( shootEvent.getShotClass().toString() );
        writer.name( ShootEvent.IS_SHOT_MADE ).value( shootEvent.isShotMade() );
    }

    public static void writeEventSubstitution( JsonWriter writer, SubstitutionEvent substitutionEvent )
            throws IOException
    {
        Player player = substitutionEvent.getNewPlayer();
        writer.name( SubstitutionEvent.NEW_PLAYER_NUMBER ).value( player.getNumber() );
    }

    public static void writeEventTurnover( JsonWriter writer, TurnoverEvent turnoverEvent ) throws IOException
    {
        writer.name( TurnoverEvent.TURNOVER_TYPE ).value( turnoverEvent.getTurnoverType().toString() );
    }

    public static void writeGame( JsonWriter writer, Game game ) throws IOException
    {
        writer.beginObject();
        writer.name( GameStats.ID.toString() ).value( game.getId().toString() );
        writer.name( GameStats.DATE.toString() ).value( game.getDateMillis() );
        writer.name( GameStats.HOME_TEAM.toString() );
        writeTeam( writer, game.getHomeTeam() );
        writer.name( GameStats.AWAY_TEAM.toString() );
        writeTeam( writer, game.getAwayTeam() );
        writer.name( GameStats.GAME_LOG.toString() );
        writeGameLog( writer, game.getGameLog() );
        writer.endObject();
    }

    public static void writeGameLog( JsonWriter writer, GameLog gameLog ) throws IOException
    {
        writer.beginArray();
        for( int index = 0; index < gameLog.size(); index++ )
        {
            List< GameEvent > periodLog = gameLog.get( index );
            writePeriodLog( writer, periodLog );
        }
        writer.endArray();
    }

    public static void writeInGamePlayerNumbers( JsonWriter writer, PlayerList playerList ) throws IOException
    {
        writer.beginArray();
        for( int index = 0; index < playerList.getSize(); index++ )
        {
            writer.value( playerList.playerAt( index ).getNumber() );
        }
        writer.endArray();
    }

    public static void writePeriodLog( JsonWriter writer, List< GameEvent > periodLog ) throws IOException
    {
        writer.beginArray();
        for( int index = 0; index < periodLog.size(); index++ )
        {
            writer.beginObject();
            GameEvent event = periodLog.get( index );
            writer.name( StringUtils.getMinSecFormattedString( event.getTime() ) );
            writeEvent( writer, event );
            writer.endObject();
        }
        writer.endArray();
    }

    public static void writePlayer( JsonWriter writer, Player player ) throws IOException
    {
        writer.beginObject();
        writer.name( PlayerStats.NUMBER.getString() ).value( player.getNumber() );
        writer.name( PlayerStats.NAME.getString() ).value( player.getFullName() );
        writer.name( PlayerStats.MISS_1PT.getString() ).value( player.getFTMiss() );
        writer.name( PlayerStats.MISS_2PT.getString() ).value( player.get2ptFGMiss() );
        writer.name( PlayerStats.MISS_3PT.getString() ).value( player.get3ptFGMiss() );
        writer.name( PlayerStats.MADE_1PT.getString() ).value( player.getFTMade() );
        writer.name( PlayerStats.MADE_2PT.getString() ).value( player.get2ptFGMade() );
        writer.name( PlayerStats.MADE_3PT.getString() ).value( player.get3ptFGMade() );
        writer.name( PlayerStats.OFFENSIVE_REBOUND.getString() ).value( player.getOffRebound() );
        writer.name( PlayerStats.DEFENSIVE_REBOUND.getString() ).value( player.getDefRebound() );
        writer.name( PlayerStats.ASSIST.getString() ).value( player.getAssist() );
        writer.name( PlayerStats.TURNOVER.getString() ).value( player.getTurnover() );
        writer.name( PlayerStats.STEAL.getString() ).value( player.getSteal() );
        writer.name( PlayerStats.BLOCK.getString() ).value( player.getBlock() );
        writer.name( PlayerStats.FOUL.getString() ).value( player.getFoulCount() );
        writer.name( PlayerStats.PLAYING_TIME.getString() ).value( player.getPlayingTimeSec() );
        writer.endObject();
    }

    public static void writeTeam( JsonWriter writer, Team team ) throws IOException
    {
        writer.beginObject();
        writer.name( TeamStats.TEAM_ID.toString() ).value( team.getID().toString() );
        writer.name( TeamStats.NAME.toString() ).value( team.getName() );
        writer.name( TeamStats.PLAYER_LIST.toString() );
        writePlayerList( writer, team.getPlayers() );
        writer.name( TeamStats.INGAME_PLAYER_LIST.toString() );
        writeInGamePlayerNumbers( writer, team.getInGamePlayers() );
        writer.name( TeamStats.TOTAL_FOULS.toString() ).value( team.getTeamFouls() );
        writer.name( TeamStats.TEAM_REBOUNDS.toString() ).value( team.getTeamRebounds() );
        writer.name( TeamStats.TIMEOUTS.toString() ).value( team.getTimeOuts() );
        writer.name( TeamStats.POSSESSION_TIME.toString() ).value( team.getPossessionTimeSec() );
        writer.endObject();
    }

    private static void writeEvent( JsonWriter writer, GameEvent event ) throws IOException
    {
        writer.beginObject();
        writeEventBase( writer, event );
        if( event instanceof ShootEvent )
        {
            writeEventShoot( writer, ( ShootEvent ) event );
        }
        else if( event instanceof FoulEvent )
        {
            writeEventFoul( writer, ( FoulEvent ) event );
        }
        else if( event instanceof TurnoverEvent )
        {
            writeEventTurnover( writer, ( TurnoverEvent ) event );
        }
        else if( event instanceof ReboundEvent )
        {
            writeEventRebound( writer, ( ReboundEvent ) event );
        }
        else if( event instanceof SubstitutionEvent )
        {
            writeEventSubstitution( writer, ( SubstitutionEvent ) event );
        }
        writer.endObject();
    }

    private static void writePlayerList( JsonWriter writer, PlayerList playerList ) throws IOException
    {
        writer.beginArray();
        for( int index = 0; index < playerList.getSize(); index++ )
        {
            Player player = playerList.playerAt( index );
            writePlayer( writer, player );
        }
        writer.endArray();
    }
}
