package com.example.bballstatstrack.models.utils;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.bballstatstrack.models.Game;
import com.example.bballstatstrack.models.Game.GameStats;
import com.example.bballstatstrack.models.Player;
import com.example.bballstatstrack.models.Player.PlayerStats;
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

import android.util.SparseArray;

public class JSONSerializer
{
    private static JSONSerializer mInstance;

    private JSONSerializer()
    {
    }

    public static JSONSerializer getInstance()
    {
        if( mInstance == null )
        {
            mInstance = new JSONSerializer();
        }
        return mInstance;
    }

    public JSONArray toJSONArray( GameLog gameLog ) throws JSONException
    {
        JSONArray jsonArray = new JSONArray();
        for( int index = 0; index < gameLog.size(); index++ )
        {
            SparseArray< GameEvent > periodLog = gameLog.get( index );
            JSONArray periodLogArray = toJSONArray( periodLog );
            jsonArray.put( periodLogArray );
        }
        return jsonArray;
    }

    public JSONArray toJSONArray( SparseArray< GameEvent > periodLog ) throws JSONException
    {
        JSONArray jsonArray = new JSONArray();
        for( int index = 0; index < periodLog.size(); index++ )
        {
            JSONArray gameClockEventPair = new JSONArray();
            gameClockEventPair.put( periodLog.keyAt( index ) );
            GameEvent event = periodLog.valueAt( index );
            JSONObject gameEventObject = toJSONObject( event );
            gameClockEventPair.put( gameEventObject );
            jsonArray.put( gameClockEventPair );
        }
        return jsonArray;
    }

    public JSONObject toJSONObject( FoulEvent foulEvent ) throws JSONException
    {
        GameEvent baseEvent = ( GameEvent ) foulEvent;
        JSONObject jsonObject = toJSONObjectDefault( baseEvent );
        jsonObject.put( FoulEvent.FOUL_TYPE, foulEvent.getFoulType() );
        return jsonObject;
    }

    public JSONObject toJSONObject( Game game ) throws JSONException
    {
        JSONObject jsonObject = new JSONObject();
        JSONObject awayTeam = toJSONObject( game.getAwayTeam() );
        JSONObject homeTeam = toJSONObject( game.getHomeTeam() );
        JSONArray gameLogArray = toJSONArray( game.getGameLog() );
        jsonObject.put( GameStats.ID.toString(), game.getId() );
        jsonObject.put( GameStats.DATE.toString(), game.getDateMillis() );
        jsonObject.put( GameStats.AWAY_TEAM.toString(), awayTeam );
        jsonObject.put( GameStats.HOME_TEAM.toString(), homeTeam );
        jsonObject.put( GameStats.GAME_LOG.toString(), gameLogArray );
        return jsonObject;
    }

    public JSONObject toJSONObject( NonShootingFoulEvent nonShootingFoulEvent ) throws JSONException
    {
        FoulEvent baseEvent = ( FoulEvent ) nonShootingFoulEvent;
        JSONObject jsonObject = toJSONObject( baseEvent );
        jsonObject.put( NonShootingFoulEvent.NON_SHOOTING_FOUL_TYPE, nonShootingFoulEvent.getNonShootingFoulType() );
        return jsonObject;
    }

    public JSONObject toJSONObject( Player player ) throws JSONException
    {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put( PlayerStats.NUMBER.toString(), player.getNumber() );
        jsonObject.put( PlayerStats.NAME.toString(), player.getFullName() );
        jsonObject.put( PlayerStats.MISS_1PT.toString(), player.getFTMiss() );
        jsonObject.put( PlayerStats.MISS_2PT.toString(), player.get2ptFGMiss() );
        jsonObject.put( PlayerStats.MISS_3PT.toString(), player.get3ptFGMiss() );
        jsonObject.put( PlayerStats.MADE_1PT.toString(), player.getFTMade() );
        jsonObject.put( PlayerStats.MADE_2PT.toString(), player.get2ptFGMade() );
        jsonObject.put( PlayerStats.MADE_3PT.toString(), player.get3ptFGMade() );
        jsonObject.put( PlayerStats.OFFENSIVE_REBOUND.toString(), player.getOffRebound() );
        jsonObject.put( PlayerStats.DEFENSIVE_REBOUND.toString(), player.getDefRebound() );
        jsonObject.put( PlayerStats.ASSIST.toString(), player.getAssist() );
        jsonObject.put( PlayerStats.TURNOVER.toString(), player.getTurnover() );
        jsonObject.put( PlayerStats.STEAL.toString(), player.getSteal() );
        jsonObject.put( PlayerStats.BLOCK.toString(), player.getBlock() );
        jsonObject.put( PlayerStats.FOUL.toString(), player.getFoulCount() );
        jsonObject.put( PlayerStats.PLAYING_TIME.toString(), player.getPlayingTimeSec() );
        return jsonObject;
    }

    public JSONObject toJSONObject( ReboundEvent reboundEvent ) throws JSONException
    {
        GameEvent baseEvent = ( GameEvent ) reboundEvent;
        JSONObject jsonObject = toJSONObjectDefault( baseEvent );
        jsonObject.put( ReboundEvent.REBOUND_TYPE, reboundEvent.getReboundType() );
        return jsonObject;
    }

    public JSONObject toJSONObject( ShootEvent shootEvent ) throws JSONException
    {
        GameEvent baseEvent = ( GameEvent ) shootEvent;
        JSONObject jsonObject = toJSONObjectDefault( baseEvent );
        jsonObject.put( ShootEvent.SHOT_CLASS, shootEvent.getShotClass() );
        jsonObject.put( ShootEvent.SHOT_TYPE, shootEvent.getShotType() );
        return jsonObject;
    }

    public JSONObject toJSONObject( ShootingFoulEvent shootingFoulEvent ) throws JSONException
    {
        FoulEvent baseEvent = ( FoulEvent ) shootingFoulEvent;
        JSONObject jsonObject = toJSONObject( baseEvent );
        Player shooter = shootingFoulEvent.getShooter();
        jsonObject.put( ShootingFoulEvent.SHOOTER, shooter.getNumber() );
        jsonObject.put( ShootingFoulEvent.FT_COUNT, shootingFoulEvent.getFTCount() );
        return jsonObject;
    }

    public JSONObject toJSONObject( SubstitutionEvent substitutionEvent ) throws JSONException
    {
        GameEvent baseEvent = ( GameEvent ) substitutionEvent;
        JSONObject jsonObject = toJSONObjectDefault( baseEvent );
        Player player = substitutionEvent.getNewPlayer();
        jsonObject.put( SubstitutionEvent.NEW_PLAYER_NUMBER, player.getNumber() );
        return jsonObject;
    }

    public JSONObject toJSONObject( Team team ) throws JSONException
    {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put( TeamStats.NAME.toString(), team.getName() );
        jsonObject.put( TeamStats.PLAYER_LIST.toString(), getJSONArray( team.getPlayers() ) );
        jsonObject.put( TeamStats.INGAME_PLAYER_LIST.toString(), getJSONArray( team.getInGamePlayers() ) );
        jsonObject.put( TeamStats.TOTAL_FOULS.toString(), team.getTeamFouls() );
        jsonObject.put( TeamStats.TEAM_REBOUNDS.toString(), team.getTeamRebounds() );
        jsonObject.put( TeamStats.TIMEOUTS.toString(), team.getTimeOuts() );
        jsonObject.put( TeamStats.POSSESSION_TIME.toString(), team.getPossessionTimeSec() );
        jsonObject.put( TeamStats.TEAM_ID.toString(), team.getID() );
        return jsonObject;
    }

    public JSONObject toJSONObject( TurnoverEvent turnoverEvent ) throws JSONException
    {
        GameEvent baseEvent = ( GameEvent ) turnoverEvent;
        JSONObject jsonObject = toJSONObjectDefault( baseEvent );
        jsonObject.put( TurnoverEvent.TURNOVER_TYPE, turnoverEvent.getTurnoverType() );
        return jsonObject;
    }

    public JSONObject toJSONObjectDefault( GameEvent gameEvent ) throws JSONException
    {
        JSONObject jsonObject = new JSONObject();
        EventType event = gameEvent.getEventType();
        Player player = gameEvent.getPlayer();
        Team team = gameEvent.getTeam();
        GameEvent appended = gameEvent.getAppended();
        jsonObject.put( GameEvent.EVENT_TYPE, event );
        if( player != null )
        {
            jsonObject.put( GameEvent.PLAYER_NUMBER, player.getNumber() );
        }
        jsonObject.put( GameEvent.TEAM_ID, team.getID() );
        if( appended != null )
        {
            jsonObject.put( GameEvent.APPENDED, toJSONObject( appended ) );
        }
        return jsonObject;
    }

    private JSONArray getJSONArray( List< Player > list )
    {
        JSONArray jsonArray = new JSONArray();
        for( Player player : list )
        {
            jsonArray.put( player.getNumber() );
        }
        return jsonArray;
    }

    private JSONArray getJSONArray( SparseArray< Player > sparseArray ) throws JSONException
    {
        JSONArray jsonArray = new JSONArray();
        for( int index = 0; index < sparseArray.size(); index++ )
        {
            Player player = sparseArray.valueAt( index );
            JSONObject playerObject = toJSONObject( player );
            jsonArray.put( playerObject );
        }
        return jsonArray;
    }

    private JSONObject toJSONObject( GameEvent event ) throws JSONException
    {
        if( event instanceof ShootEvent )
        {
            return toJSONObject( ( ShootEvent ) event );
        }
        else if( event instanceof FoulEvent )
        {
            return toJSONObject( ( FoulEvent ) event );
        }
        else if( event instanceof TurnoverEvent )
        {
            return toJSONObject( ( TurnoverEvent ) event );
        }
        else if( event instanceof ReboundEvent )
        {
            return toJSONObject( ( ReboundEvent ) event );
        }
        else if( event instanceof NonShootingFoulEvent )
        {
            return toJSONObject( ( NonShootingFoulEvent ) event );
        }
        else if( event instanceof ShootingFoulEvent )
        {
            return toJSONObject( ( ShootingFoulEvent ) event );
        }
        else if( event instanceof SubstitutionEvent )
        {
            return toJSONObject( ( SubstitutionEvent ) event );
        }
        else
        {
            return toJSONObjectDefault( event );
        }
    }
}
