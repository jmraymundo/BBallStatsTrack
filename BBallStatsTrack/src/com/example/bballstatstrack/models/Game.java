package com.example.bballstatstrack.models;

import java.util.Date;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.bballstatstrack.models.game.GameLog;
import com.example.bballstatstrack.models.gameevents.GameEvent;
import com.example.bballstatstrack.models.gameevents.foulevents.NonShootingFoulEvent;
import com.example.bballstatstrack.models.gameevents.foulevents.ShootingFoulEvent;
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

    private boolean mGameOngoing = false;

    private int mPeriod = 0;

    private int mCurrentGameClock;

    private int mEventGameClock = -1;

    private int mCurrentShotClock;

    private static final int MAX_SHOT_CLOCK = 24;

    private final int mMaxGameClock;

    private final int mMaxOTGameClock;

    private final int mReducedMaxShotClock;

    private GameLog mGameLog;

    private SparseArray< GameEvent > mPeriodLog;

    private int mHomePeriodFouls = 0;

    private int mAwayPeriodFouls = 0;

    private UUID mID;

    private MyDate mDate;

    public Game( int maxGameClock, int maxOTGameCock, int resetShotClock, Team homeTeam, Team awayTeam )
    {
        mMaxGameClock = maxGameClock * 60;
        mMaxOTGameClock = maxOTGameCock * 60;
        mReducedMaxShotClock = resetShotClock;
        mHomeTeam = homeTeam;
        mAwayTeam = awayTeam;
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
        mMaxOTGameClock = 0;
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
    }

    public void addNewEvent( GameEvent event )
    {
        event.resolve();
        mPeriodLog.append( mEventGameClock, event );
        endNewEvent();
        checkTeamFoulEvent( event );
    }

    public void checkTeamFoulEvent( GameEvent event )
    {
        if( event == null )
        {
            return;
        }
        if( event instanceof ShootingFoulEvent || event instanceof NonShootingFoulEvent )
        {
            addPeriodFoul( event.getTeam() );
        }
        else
        {
            checkTeamFoulEvent( event.getAppended() );
        }
    }

    private void addPeriodFoul( Team team )
    {
        if( team.equals( mAwayTeam ) )
        {
            mAwayPeriodFouls++;
        }
        else if( team.equals( mHomeTeam ) )
        {
            mHomePeriodFouls++;
        }
    }

    public boolean isPenalty( Team team )
    {
        if( team.equals( mAwayTeam ) )
        {
            return( mAwayPeriodFouls >= 5 );
        }
        else if( team.equals( mHomeTeam ) )
        {
            return( mHomePeriodFouls >= 5 );
        }
        return false;
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
        resetShotClock24();
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
        resetPeriodFouls();
    }

    private void resetPeriodFouls()
    {
        mHomePeriodFouls = 0;
        mAwayPeriodFouls = 0;
    }

    public int getPeriod()
    {
        return mPeriod;
    }

    public int getHomePeriodFouls()
    {
        return mHomePeriodFouls;
    }

    public int getAwayPeriodFouls()
    {
        return mAwayPeriodFouls;
    }

    public void resetShotClock24()
    {
        mCurrentShotClock = MAX_SHOT_CLOCK;
    }

    public void resetShotClock()
    {
        mCurrentShotClock = mReducedMaxShotClock;
    }

    public void resetGameClock()
    {
        mCurrentGameClock = mMaxGameClock;
    }

    public void pauseGame()
    {
        mGameOngoing = false;
    }

    public void unpauseGame()
    {
        mGameOngoing = true;
    }

    public Team getTeamWithPossession()
    {
        return mHasBallPossession;
    }

    public void setTeamWithPossession( Team team )
    {
        if( team.equals( mAwayTeam ) )
        {
            mHasBallPossession = mAwayTeam;
        }
        else if( team.equals( mHomeTeam ) )
        {
            mHasBallPossession = mHomeTeam;
        }
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
        if( isClocksValid() && isGameOngoing() )
        {
            mAwayTeam.updatePlayingTime();
            mHomeTeam.updatePlayingTime();
            mCurrentGameClock--;
            mCurrentShotClock--;
        }
        else
        {
            pauseGame();
        }
    }

    private boolean isClocksValid()
    {
        return mCurrentGameClock > 0 && mCurrentShotClock > 0;
    }

    public boolean isGameOngoing()
    {
        return mGameOngoing;
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
