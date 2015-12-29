package com.example.bballstatstrack.models.gameevents;

import com.example.bballstatstrack.models.Player;
import com.example.bballstatstrack.models.Team;

public abstract class GameEvent
{
    public static final String TEAM_ID = "teamID";

    public static final String APPENDED = "appended";

    public static final String EVENT_TYPE = "event";

    public static final String PLAYER_NUMBER = "playerNumber";

    protected EventType mEvent;

    protected Player mPlayer;

    protected Team mTeam;

    protected GameEvent mAppended = null;

    protected GameEvent( EventType event, Player player, Team team )
    {
        mEvent = event;
        mPlayer = player;
        mTeam = team;
    }

    public enum EventType
    {
        FOUL, SHOOT, REBOUND, TURNOVER, ASSIST, SUBSTITUTION, TIME_OUT, BLOCK, STEAL;
    }

    public enum FoulType
    {
        SHOOTING, NON_SHOOTING, OFFENSIVE;
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

    public void append( GameEvent appendedEvent )
    {
        return;
    }

    @Override
    public abstract String toString();

    public abstract void resolve();

    public GameEvent getAppended()
    {
        return mAppended;
    }

    public EventType getEventType()
    {
        return mEvent;
    }

    public Player getPlayer()
    {
        return mPlayer;
    }

    public Team getTeam()
    {
        return mTeam;
    }
}
