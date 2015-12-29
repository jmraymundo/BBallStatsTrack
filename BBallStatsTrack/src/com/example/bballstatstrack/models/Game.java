package com.example.bballstatstrack.models;

import java.util.Date;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.bballstatstrack.models.game.GameLog;
import com.example.bballstatstrack.models.gameevents.GameEvent;
import com.example.bballstatstrack.models.gameevents.exceptions.GameEventException;
import com.example.bballstatstrack.models.utils.GameEventDeserializer;

import android.text.format.DateFormat;
import android.util.Log;
import android.util.SparseArray;

public class Game
{
    private static final String B_BALL_STAT_TRACK = "BBallStatTrack";

    public enum GameStats
    {

        ID( "id" ), AWAY_TEAM( "awayTeam" ), HOME_TEAM( "homeTeam" ), GAME_LOG( "gameLog" ), PERIOD_LOG(
                "periodLog" ), DATE( "date" );

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

    private int mEventGameClock = -1;

    private int mCurrentShotClock;

    private static final int MAX_SHOT_CLOCK = 24;

    private final int mMaxGameClock;

    private final int mReducedMaxShotClock;

    private GameLog mGameLog;

    private SparseArray< GameEvent > mPeriodLog;

    private UUID mID;

    private MyDate mDate;

    public Game( int maxGameClock, int resetShotClock, Team awayTeam, Team homeTeam )
    {
        mMaxGameClock = maxGameClock * 60;
        mReducedMaxShotClock = resetShotClock;
        mAwayTeam = awayTeam;
        mHomeTeam = homeTeam;
        String id = mAwayTeam.getName() + mHomeTeam.getName() + System.currentTimeMillis();
        mID = UUID.nameUUIDFromBytes( id.getBytes() );
        initializeClocks();
        initializeLogs();
        pauseGame();
        mDate = new MyDate();
    }

    public Game( JSONObject game )
    {
        mMaxGameClock = 0;
        mReducedMaxShotClock = 0;
        try
        {
            mID = UUID.fromString( game.getString( GameStats.ID.toString() ) );
            mAwayTeam = new Team( game.getJSONObject( GameStats.AWAY_TEAM.toString() ) );
            mHomeTeam = new Team( game.getJSONObject( GameStats.HOME_TEAM.toString() ) );
            GameEventDeserializer serializer = new GameEventDeserializer( this );
            mGameLog = serializer.getGameLog( game );
            mPeriod = mGameLog.size();
            mDate = new MyDate( game.getLong( GameStats.DATE.toString() ) );
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

    public void addNewEvent( GameEvent event )
    {
        event.resolve();
        mPeriodLog.append( mEventGameClock, event );
        endNewEvent();
    }

    public void startNewEvent()
    {
        mEventGameClock = mCurrentGameClock;
    }

    public void endNewEvent()
    {
        mEventGameClock = -1;
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

    private void initializeLogs()
    {
        mGameLog = new GameLog();
        mPeriodLog = mGameLog.getCurrentPeriodLog();
        mPeriod = mGameLog.getCurrentPeriod();
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
        mGameLog.nextPeriod();
        mPeriod = mGameLog.getCurrentPeriod();
        mPeriodLog = mGameLog.getCurrentPeriodLog();
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

    public GameLog getGameLog()
    {
        return mGameLog;
    }

    public UUID getId()
    {
        return mID;
    }

    public String getTitle()
    {
        String home = mHomeTeam.getName();
        int homeScore = mHomeTeam.getTotalScore();
        String away = mAwayTeam.getName();
        int awayScore = mAwayTeam.getTotalScore();
        return home + " " + homeScore + " - " + awayScore + " " + away;
    }

    public class MyDate extends Date
    {
        public MyDate( long milliseconds )
        {
            super( milliseconds );
        }

        public MyDate()
        {
            super();
        }

        @Override
        public String toString()
        {
            return DateFormat.format( "EEE, MMM dd, yyyy", this ).toString();
        }
    }

    public MyDate getDate()
    {
        return mDate;
    }

    public long getDateMillis()
    {
        return mDate.getTime();
    }
}
