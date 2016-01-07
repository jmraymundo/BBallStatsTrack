package com.example.bballstatstrack.models.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.example.bballstatstrack.models.Game;
import com.example.bballstatstrack.models.Game.GameStats;
import com.example.bballstatstrack.models.Player;
import com.example.bballstatstrack.models.Player.PlayerStats;
import com.example.bballstatstrack.models.Team;
import com.example.bballstatstrack.models.Team.TeamStats;
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

import android.util.JsonReader;
import android.util.SparseArray;

public class JSONDeserializer
{
    private static Team mHomeTeam = null;

    private static Team mAwayTeam = null;

    public static Game readGame( JsonReader reader ) throws IOException
    {
        UUID id = null;
        long longDate = Long.MIN_VALUE;
        GameLog gameLog = null;
        reader.beginObject();
        while( reader.hasNext() )
        {
            String name = reader.nextName();
            if( name.equals( GameStats.ID.toString() ) )
            {
                id = UUID.fromString( reader.nextString() );
            }
            else if( name.equals( GameStats.DATE.toString() ) )
            {
                longDate = reader.nextLong();
            }
            else if( name.equals( GameStats.HOME_TEAM.toString() ) )
            {
                mHomeTeam = readTeam( reader );
            }
            else if( name.equals( GameStats.AWAY_TEAM.toString() ) )
            {
                mAwayTeam = readTeam( reader );
            }
            else if( name.equals( GameStats.GAME_LOG.toString() ) )
            {
                gameLog = readGameLog( reader );
            }
        }
        reader.endObject();
        return new Game( id, longDate, mHomeTeam, mAwayTeam, gameLog );
    }

    private static Team getOtherTeam( Team team )
    {
        if( team.equals( mHomeTeam ) )
        {
            return mAwayTeam;
        }
        else if( team.equals( mAwayTeam ) )
        {
            return mHomeTeam;
        }
        else
        {
            return null;
        }
    }

    private static Player getPlayerFromTeam( Team team, int playerNumber )
    {
        return team.getPlayers().get( playerNumber );
    }

    private static Player getPlayerNumberFromOtherTeam( Team team, int number )
    {
        Team otherTeam = getOtherTeam( team );
        return otherTeam.getPlayers().get( number );
    }

    private static Team getTeamFromUUID( UUID teamID )
    {
        if( teamID.equals( mHomeTeam.getID() ) )
        {
            return mHomeTeam;
        }
        else if( teamID.equals( mAwayTeam.getID() ) )
        {
            return mAwayTeam;
        }
        else
        {
            return null;
        }
    }

    private static GameEvent readEvent( JsonReader reader ) throws IOException
    {
        EventType type = null;
        int playerNumber = Integer.MIN_VALUE;
        UUID teamID = null;
        int time = Integer.MIN_VALUE;
        GameEvent appended = null;
        GameEvent thisEvent = null;
        reader.beginObject();
        if( reader.nextName().equals( GameEvent.EVENT_TYPE ) )
        {
            type = EventType.valueOf( reader.nextString() );
        }
        if( reader.nextName().equals( GameEvent.PLAYER_NUMBER ) )
        {
            playerNumber = reader.nextInt();
        }
        if( reader.nextName().equals( GameEvent.TEAM_ID ) )
        {
            teamID = UUID.fromString( reader.nextString() );
        }
        if( reader.nextName().equals( GameEvent.TIME ) )
        {
            time = reader.nextInt();
        }
        if( reader.nextName().equals( GameEvent.APPENDED ) )
        {
            appended = readEvent( reader );
        }
        Team team = getTeamFromUUID( teamID );
        Player player = getPlayerFromTeam( team, playerNumber );
        switch( type )
        {
            case ASSIST:
                thisEvent = new AssistEvent( player, team );
                break;
            case BLOCK:
                thisEvent = new BlockEvent( player, team );
                break;
            case FOUL:
                thisEvent = readEventFoul( reader, player, team );
                break;
            case REBOUND:
                thisEvent = readEventRebound( reader, team, player );
                break;
            case SHOOT:
                thisEvent = readEventShoot( reader, player, team );
                break;
            case STEAL:
                thisEvent = new StealEvent( player, team );
                break;
            case SUBSTITUTION:
                thisEvent = readEventSubstitution( reader, team, player );
                break;
            case TIME_OUT:
                thisEvent = new TimeoutEvent( team );
                break;
            case TURNOVER:
                thisEvent = readEventTurnover( reader, team, player );
                break;
        }
        thisEvent.setTime( time );
        reader.endObject();
        if( thisEvent != null )
        {
            thisEvent.append( appended );
        }
        return thisEvent;
    }

    private static FoulEvent readEventFoul( JsonReader reader, Player player, Team team ) throws IOException
    {
        FoulType foulType = FoulType.valueOf( reader.nextString() );
        switch( foulType )
        {
            case OFFENSIVE:
                return new OffensiveFoulEvent( player, team );
            case NON_SHOOTING:
                return new NonShootingFoulEvent( NonShootingFoulType.valueOf( reader.nextString() ), player, team );
            case SHOOTING:
                return new ShootingFoulEvent( player, team, getPlayerNumberFromOtherTeam( team, reader.nextInt() ),
                        getTeamFromUUID( UUID.fromString( reader.nextString() ) ), reader.nextInt() );
        }
        return null;
    }

    private static ReboundEvent readEventRebound( JsonReader reader, Team team, Player player ) throws IOException
    {
        return new ReboundEvent( ReboundType.valueOf( reader.nextString() ), player, team );
    }

    private static ShootEvent readEventShoot( JsonReader reader, Player player, Team team ) throws IOException
    {
        ShotClass shotClass = ShotClass.valueOf( reader.nextString() );
        ShotType shotType = ShotType.valueOf( reader.nextString() );
        return new ShootEvent( shotClass, shotType, player, team );
    }

    private static SubstitutionEvent readEventSubstitution( JsonReader reader, Team team, Player player )
            throws IOException
    {
        return new SubstitutionEvent( player, getPlayerFromTeam( team, reader.nextInt() ), team );
    }

    private static TurnoverEvent readEventTurnover( JsonReader reader, Team team, Player player ) throws IOException
    {
        return new TurnoverEvent( TurnoverType.valueOf( reader.nextString() ), player, team );
    }

    private static GameLog readGameLog( JsonReader reader ) throws IOException
    {
        GameLog gameLog = new GameLog();
        reader.beginArray();
        int index = 0;
        while( reader.hasNext() )
        {
            gameLog.append( index, readPeriodLog( reader ) );
            index++;
        }
        reader.endArray();
        return gameLog;
    }

    private static List< Integer > readInGamePlayerNumbers( JsonReader reader ) throws IOException
    {
        List< Integer > list = new ArrayList< Integer >();
        reader.beginArray();
        while( reader.hasNext() )
        {
            list.add( reader.nextInt() );
        }
        reader.endArray();
        return list;
    }

    private static List< GameEvent > readPeriodLog( JsonReader reader ) throws IOException
    {
        List< GameEvent > periodLog = new ArrayList< GameEvent >();
        reader.beginArray();
        while( reader.hasNext() )
        {
            reader.beginObject();
            GameEvent event = readEvent( reader );
            periodLog.add( event );
            reader.endObject();
        }
        reader.endArray();
        return periodLog;
    }

    private static Player readPlayer( JsonReader reader ) throws IOException
    {
        int number = Integer.MIN_VALUE;
        String fullName = null;
        int miss1pt = Integer.MIN_VALUE;
        int miss2pt = Integer.MIN_VALUE;
        int miss3pt = Integer.MIN_VALUE;
        int made1pt = Integer.MIN_VALUE;
        int made2pt = Integer.MIN_VALUE;
        int made3pt = Integer.MIN_VALUE;
        int offReb = Integer.MIN_VALUE;
        int defReb = Integer.MIN_VALUE;
        int assist = Integer.MIN_VALUE;
        int to = Integer.MIN_VALUE;
        int stl = Integer.MIN_VALUE;
        int blk = Integer.MIN_VALUE;
        int foul = Integer.MIN_VALUE;
        int playingTimeSec = Integer.MIN_VALUE;
        reader.beginObject();
        String name;
        while( reader.hasNext() )
        {
            name = reader.nextName();
            if( name.equals( PlayerStats.NUMBER.toString() ) )
            {
                number = reader.nextInt();
            }
            else if( name.equals( PlayerStats.NAME.toString() ) )
            {
                fullName = reader.nextString();
            }
            else if( name.equals( PlayerStats.MISS_1PT.toString() ) )
            {
                miss1pt = reader.nextInt();
            }
            else if( name.equals( PlayerStats.MISS_2PT.toString() ) )
            {
                miss2pt = reader.nextInt();
            }
            else if( name.equals( PlayerStats.MISS_3PT.toString() ) )
            {
                miss3pt = reader.nextInt();
            }
            else if( name.equals( PlayerStats.MADE_1PT.toString() ) )
            {
                made1pt = reader.nextInt();
            }
            else if( name.equals( PlayerStats.MADE_2PT.toString() ) )
            {
                made2pt = reader.nextInt();
            }
            else if( name.equals( PlayerStats.MADE_3PT.toString() ) )
            {
                made3pt = reader.nextInt();
            }
            else if( name.equals( PlayerStats.OFFENSIVE_REBOUND.toString() ) )
            {
                offReb = reader.nextInt();
            }
            else if( name.equals( PlayerStats.DEFENSIVE_REBOUND.toString() ) )
            {
                defReb = reader.nextInt();
            }
            else if( name.equals( PlayerStats.ASSIST.toString() ) )
            {
                assist = reader.nextInt();
            }
            else if( name.equals( PlayerStats.TURNOVER.toString() ) )
            {
                to = reader.nextInt();
            }
            else if( name.equals( PlayerStats.STEAL.toString() ) )
            {
                stl = reader.nextInt();
            }
            else if( name.equals( PlayerStats.BLOCK.toString() ) )
            {
                blk = reader.nextInt();
            }
            else if( name.equals( PlayerStats.FOUL.toString() ) )
            {
                foul = reader.nextInt();
            }
            else if( name.equals( PlayerStats.PLAYING_TIME.toString() ) )
            {
                playingTimeSec = reader.nextInt();
            }
        }
        reader.endObject();
        return new Player( number, fullName, miss1pt, miss2pt, miss3pt, made1pt, made2pt, made3pt, offReb, defReb,
                assist, to, stl, blk, foul, playingTimeSec );
    }

    private static SparseArray< Player > readPlayerList( JsonReader reader ) throws IOException
    {
        SparseArray< Player > list = new SparseArray< Player >();
        Player player;
        reader.beginArray();
        while( reader.hasNext() )
        {
            player = readPlayer( reader );
            list.append( player.getNumber(), player );
        }
        reader.endArray();
        return list;
    }

    private static Team readTeam( JsonReader reader ) throws IOException
    {
        UUID id = null;
        String teamName = null;
        SparseArray< Player > playerList = null;
        List< Integer > inGamePlayerList = null;
        int teamFouls = Integer.MIN_VALUE;
        int teamRebounds = Integer.MIN_VALUE;
        int timeouts = Integer.MIN_VALUE;
        int possessionTimeSec = Integer.MIN_VALUE;
        reader.beginObject();
        String name;
        while( reader.hasNext() )
        {
            name = reader.nextName();
            if( name.equals( TeamStats.TEAM_ID.toString() ) )
            {
                id = UUID.fromString( reader.nextString() );
            }
            else if( name.equals( TeamStats.NAME.toString() ) )
            {
                teamName = reader.nextString();
            }
            else if( name.equals( TeamStats.PLAYER_LIST.toString() ) )
            {
                playerList = readPlayerList( reader );
            }
            else if( name.equals( TeamStats.INGAME_PLAYER_LIST.toString() ) )
            {
                inGamePlayerList = readInGamePlayerNumbers( reader );
            }
            else if( name.equals( TeamStats.TOTAL_FOULS.toString() ) )
            {
                teamFouls = reader.nextInt();
            }
            else if( name.equals( TeamStats.TEAM_REBOUNDS.toString() ) )
            {
                teamRebounds = reader.nextInt();
            }
            else if( name.equals( TeamStats.TIMEOUTS.toString() ) )
            {
                timeouts = reader.nextInt();
            }
            else if( name.equals( TeamStats.POSSESSION_TIME.toString() ) )
            {
                possessionTimeSec = reader.nextInt();
            }
        }
        reader.endObject();
        return new Team( id, teamName, playerList, inGamePlayerList, teamFouls, teamRebounds, timeouts,
                possessionTimeSec );
    }
}
