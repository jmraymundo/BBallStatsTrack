package com.example.bballstatstrack.models.gameevents;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.bballstatstrack.models.Player;
import com.example.bballstatstrack.models.Team;
import com.example.bballstatstrack.models.gameevents.exceptions.GameEventException;

public abstract class GameEvent
{
    public static final String TEAM_ID = "teamID";

    public static final String APPENDED = "appended";

    public static final String EVENT_TYPE = "event";

    public static final String PLAYER_NUMBER = "playerNumber";

    protected Event mEvent;

    protected Player mPlayer;

    protected Team mTeam;

    protected GameEvent mAppended = null;

    protected GameEvent( Event event, Player player, Team team )
    {
        mEvent = event;
        mPlayer = player;
        mTeam = team;
    }

    public enum Event
    {
        FOUL, SHOOT, REBOUND, TURNOVER, ASSIST, SUBSTITUTION, TIME_OUT, BLOCK, STEAL;
    }

    public enum FoulType
    {
        SHOOTING, NON_SHOOTING, OFFENSIVE;
    }

    public enum ShootingFoulType
    {
        AND1, NO_AND1;
    }

    public enum NonShootingFoulType
    {
        PENALTY, NON_PENALTY;
    }

    public enum TurnoverType
    {
        STEAL, OFFENSIVE_FOUL, OTHER;
    }

    public enum ShotClass
    {
        FT, FG_2PT, FG_3PT;
    }

    public enum ShotType
    {
        MADE, MISSED;
    }

    public enum MissType
    {
        BLOCKED, UNFORCED;
    }

    public enum ReboundType
    {
        OFFENSIVE, DEFENSIVE, TEAM_REBOUND;
    }

    public void append( GameEvent appendedEvent ) throws GameEventException
    {
        throw new GameEventException( this, appendedEvent );
    }

    public JSONObject toJSON() throws JSONException
    {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put( EVENT_TYPE, mEvent );
        jsonObject.put( PLAYER_NUMBER, mPlayer.getNumber() );
        jsonObject.put( TEAM_ID, mTeam.getID() );
        if( mAppended != null )
        {
            jsonObject.put( APPENDED, mAppended.toJSON() );
        }
        return jsonObject;
    }

    @Override
    public abstract String toString();

    public abstract void resolveEvent();
}
