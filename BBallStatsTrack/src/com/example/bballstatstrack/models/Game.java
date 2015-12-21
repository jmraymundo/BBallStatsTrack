package com.example.bballstatstrack.models;

import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.bballstatstrack.models.gameevents.AssistEvent;
import com.example.bballstatstrack.models.gameevents.BlockEvent;
import com.example.bballstatstrack.models.gameevents.FoulEvent;
import com.example.bballstatstrack.models.gameevents.GameEvent;
import com.example.bballstatstrack.models.gameevents.GameEvent.Event;
import com.example.bballstatstrack.models.gameevents.GameEvent.FoulType;
import com.example.bballstatstrack.models.gameevents.GameEvent.NonShootingFoulType;
import com.example.bballstatstrack.models.gameevents.GameEvent.ReboundType;
import com.example.bballstatstrack.models.gameevents.GameEvent.ShootingFoulType;
import com.example.bballstatstrack.models.gameevents.GameEvent.ShotClass;
import com.example.bballstatstrack.models.gameevents.GameEvent.ShotType;
import com.example.bballstatstrack.models.gameevents.GameEvent.TurnoverType;
import com.example.bballstatstrack.models.gameevents.ReboundEvent;
import com.example.bballstatstrack.models.gameevents.ShootEvent;
import com.example.bballstatstrack.models.gameevents.StealEvent;
import com.example.bballstatstrack.models.gameevents.SubstitutionEvent;
import com.example.bballstatstrack.models.gameevents.TimeoutEvent;
import com.example.bballstatstrack.models.gameevents.TurnoverEvent;
import com.example.bballstatstrack.models.gameevents.exceptions.GameEventException;
import com.example.bballstatstrack.models.gameevents.foulevents.NonShootingFoulEvent;
import com.example.bballstatstrack.models.gameevents.foulevents.OffensiveFoulEvent;
import com.example.bballstatstrack.models.gameevents.foulevents.ShootingFoulEvent;

import android.util.Log;
import android.util.SparseArray;

public class Game
{
    private static final String B_BALL_STAT_TRACK = "BBallStatTrack";

    private enum GameStats
    {

        AWAY_TEAM( "awayTeam" ), HOME_TEAM( "homeTeam" ), PERIOD( "period" ), GAME_LOG( "gameLog" ), PERIOD_LOG(
                "periodLog" );

        private final String mConstant;

        private GameStats( String constant )
        {
            mConstant = constant;
        }

        @Override
        public String toString()
        {
            return mConstant;
        }
    }

    private Team mAwayTeam;

    private Team mHomeTeam;

    private Team mHasBallPossession;

    private boolean isGameOngoing;

    private int mPeriod = 0;

    private int mCurrentGameClock;

    private int mCurrentShotClock;

    private static final int MAX_SHOT_CLOCK = 24;

    private final int mMaxGameClock;

    private final int mReducedMaxShotClock;

    private SparseArray< SparseArray< GameEvent > > mGameLog = new SparseArray< SparseArray< GameEvent > >();

    private SparseArray< GameEvent > mPeriodLog = new SparseArray< GameEvent >();

    public Game( int maxGameClock, int resetShotClock, Team awayTeam, Team homeTeam )
    {
        mMaxGameClock = maxGameClock * 60;
        mReducedMaxShotClock = resetShotClock;
        mAwayTeam = awayTeam;
        mHomeTeam = homeTeam;
        initializeClocks();
        pauseGame();
    }

    public Game( JSONObject game )
    {
        mMaxGameClock = 0;
        mReducedMaxShotClock = 0;
        try
        {
            mAwayTeam = new Team( game.getJSONObject( GameStats.AWAY_TEAM.toString() ) );
            mHomeTeam = new Team( game.getJSONObject( GameStats.HOME_TEAM.toString() ) );
            mPeriod = game.getInt( GameStats.PERIOD.toString() );
            mGameLog = getGameLog( game );
        }
        catch( JSONException e )
        {
            e.printStackTrace();
            Log.e( B_BALL_STAT_TRACK, "Attribute missing from Game JSONObject!", e );
        }
        catch( GameEventException e )
        {
            e.printStackTrace();
            Log.e( B_BALL_STAT_TRACK, "Error in appending events during GameLog reconstruction!", e );
        }
    }

    private SparseArray< SparseArray< GameEvent > > getGameLog( JSONObject game )
            throws JSONException, GameEventException
    {
        SparseArray< SparseArray< GameEvent > > gameLog = new SparseArray< SparseArray< GameEvent > >();
        JSONArray gameLogArray = game.getJSONArray( GameStats.GAME_LOG.toString() );
        for( int gameIndex = 0; gameIndex < gameLogArray.length(); gameIndex++ )
        {
            JSONArray periodLogArray = gameLogArray.getJSONArray( gameIndex );
            SparseArray< GameEvent > periodLog = getPeriodLogFromJSONArray( periodLogArray );
            gameLog.put( gameIndex, periodLog );
        }
        return gameLog;
    }

    private SparseArray< GameEvent > getPeriodLogFromJSONArray( JSONArray periodLogArray )
            throws JSONException, GameEventException
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

    public GameEvent getGameEventFromJSONObject( JSONObject jsonGameEvent ) throws JSONException, GameEventException
    {
        if( null == jsonGameEvent )
        {
            return null;
        }
        Event eventType = ( Event ) jsonGameEvent.get( GameEvent.EVENT_TYPE );
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

    private Team getTeamFromID( UUID teamID )
    {
        if( teamID.equals( mAwayTeam.getID() ) )
        {
            return mAwayTeam;
        }
        else if( teamID.equals( mHomeTeam.getID() ) )
        {
            return mHomeTeam;
        }
        else
        {
            return null;
        }
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
        ShootingFoulType shootingFoulType = ( ShootingFoulType ) jsonGameEvent
                .get( ShootingFoulEvent.SHOOTING_FOUL_TYPE );
        int shooterNumber = jsonGameEvent.getInt( ShootingFoulEvent.SHOOTER );
        Team otherTeam = ( team.equals( mAwayTeam ) ? mHomeTeam : mAwayTeam );
        Player shooter = otherTeam.getPlayers().get( shooterNumber );
        return new ShootingFoulEvent( shootingFoulType, player, team, shooter );
    }

    public void addNewEvent( GameEvent event )
    {
        mPeriodLog.append( mCurrentGameClock, event );
    }

    public Team getAwayTeam()
    {
        return mAwayTeam;
    }

    public Team getHomeTeam()
    {
        return mHomeTeam;
    }

    private void initializeClocks()
    {
        resetGameClock();
        resetShotClock( true );
    }

    public int getCurrentGameClock()
    {
        return mCurrentGameClock;
    }

    public int getCurrentShotClock()
    {
        return mCurrentShotClock;
    }

    public void nextPeriod()
    {
        mGameLog.append( mPeriod, mPeriodLog );
        mPeriod++;
        mPeriodLog = new SparseArray< GameEvent >();
    }

    public int getPeriod()
    {
        return mPeriod;
    }

    public void resetShotClock( boolean isMax )
    {
        if( isMax )
        {
            mCurrentShotClock = MAX_SHOT_CLOCK;
        }
        else
        {
            mCurrentShotClock = mReducedMaxShotClock;
        }
    }

    public void resetGameClock()
    {
        mCurrentGameClock = mMaxGameClock;
    }

    public void pauseGame()
    {
        isGameOngoing = false;
    }

    public void startGame()
    {
        isGameOngoing = true;
    }

    public Team getTeamWithPossession()
    {
        return mHasBallPossession;
    }

    public void swapBallPossession()
    {
        if( mHasBallPossession.equals( mHomeTeam ) )
        {
            mHasBallPossession = mAwayTeam;
        }
        else
        {
            mHasBallPossession = mHomeTeam;
        }
    }

    public void updateTime()
    {
        mAwayTeam.updatePlayingTime();
        mHomeTeam.updatePlayingTime();
        mCurrentGameClock--;
        mCurrentShotClock--;
    }

    public boolean isGameOngoing()
    {
        return isGameOngoing;
    }

    public JSONObject toJSON() throws JSONException
    {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put( GameStats.AWAY_TEAM.toString(), mAwayTeam );
        jsonObject.put( GameStats.HOME_TEAM.toString(), mHomeTeam );
        jsonObject.put( GameStats.PERIOD.toString(), mPeriod );
        jsonObject.put( GameStats.GAME_LOG.toString(), getGameLogJSON() );
        return jsonObject;
    }

    private JSONArray getGameLogJSON() throws JSONException
    {
        JSONArray jsonArray = new JSONArray();
        for( int index = 0; index < mGameLog.size(); index++ )
        {
            JSONArray periodLogArray = getPeriodLogArray( index );
            jsonArray.put( periodLogArray );
        }
        return jsonArray;
    }

    private JSONArray getPeriodLogArray( int gameLogIndex ) throws JSONException
    {
        JSONArray jsonArray = new JSONArray();
        SparseArray< GameEvent > periodLog = mGameLog.valueAt( gameLogIndex );
        for( int index = 0; index < periodLog.size(); index++ )
        {
            JSONArray gameClockEventPair = new JSONArray();
            gameClockEventPair.put( periodLog.keyAt( index ) );
            gameClockEventPair.put( periodLog.valueAt( index ).toJSON() );
            jsonArray.put( gameClockEventPair );
        }
        return jsonArray;
    }
}
