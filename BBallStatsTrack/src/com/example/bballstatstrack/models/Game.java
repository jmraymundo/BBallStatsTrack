package com.example.bballstatstrack.models;

import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.UUID;

import com.example.bballstatstrack.models.game.GameLog;
import com.example.bballstatstrack.models.gameevents.GameEvent;

import android.text.format.DateFormat;

public class Game extends Observable
{
    private static final int MAX_SHOT_CLOCK = 24;

    private Team mAwayTeam;

    private Team mHomeTeam;

    private Team mHasBallPossession;

    private int mPeriod = 0;

    private int mCurrentPeriodClock;

    private int mEventPeriodClock = -1;

    private int mCurrentShotClock;

    private final int mMaxRegulationPeriodClock;

    private final int mMaxOTPeriodClock;

    private final int mReducedMaxShotClock;

    private GameLog mGameLog;

    private List< GameEvent > mPeriodLog;

    private int mHomePeriodFouls = 0;

    private int mAwayPeriodFouls = 0;

    private UUID mID;

    private MyDate mDate;

    private boolean mIsShotClockOn = true;

    public Game( int maxPeriodClock, int maxOTGameCock, int resetShotClock, Team homeTeam, Team awayTeam )
    {
        mMaxRegulationPeriodClock = maxPeriodClock * 60;
        mMaxOTPeriodClock = maxOTGameCock * 60;
        mReducedMaxShotClock = resetShotClock;
        mHomeTeam = homeTeam;
        mAwayTeam = awayTeam;
        String id = mAwayTeam.getName() + mHomeTeam.getName() + System.currentTimeMillis();
        mID = UUID.nameUUIDFromBytes( id.getBytes() );
        initializeClocks();
        initializeLogs();
        mDate = new MyDate();
    }

    public Game( UUID id, long longDate, Team homeTeam, Team awayTeam, GameLog gameLog )
    {
        mMaxRegulationPeriodClock = 0;
        mMaxOTPeriodClock = 0;
        mReducedMaxShotClock = 0;
        mID = id;
        mDate = new MyDate( longDate );
        mHomeTeam = homeTeam;
        mAwayTeam = awayTeam;
        mGameLog = gameLog;
        mPeriod = mGameLog.size();
    }

    public void addNewEvent( GameEvent event )
    {
        event.resolve();
        event.setTime( mEventPeriodClock );
        mPeriodLog.add( event );
        endNewEvent();
        setChanged();
        notifyObservers();
    }

    public void addPeriodFoul( Team team )
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

    public void endNewEvent()
    {
        mEventPeriodClock = -1;
    }

    public int getAwayPeriodFouls()
    {
        return mAwayPeriodFouls;
    }

    public Team getAwayTeam()
    {
        return mAwayTeam;
    }

    public int getCurrentPeriodClock()
    {
        return mCurrentPeriodClock;
    }

    public int getCurrentShotClock()
    {
        if( mIsShotClockOn )
        {
            return mCurrentShotClock;
        }
        return -1;
    }

    public MyDate getDate()
    {
        return mDate;
    }

    public long getDateMillis()
    {
        return mDate.getTime();
    }

    public GameLog getGameLog()
    {
        return mGameLog;
    }

    public int getHomePeriodFouls()
    {
        return mHomePeriodFouls;
    }

    public Team getHomeTeam()
    {
        return mHomeTeam;
    }

    public UUID getId()
    {
        return mID;
    }

    public int getPeriod()
    {
        return mPeriod;
    }

    public Team getTeamWithoutPossession()
    {
        Team team = getTeamWithPossession();
        if( mHomeTeam.equals( team ) )
        {
            return mAwayTeam;
        }
        else if( mAwayTeam.equals( team ) )
        {
            return mHomeTeam;
        }
        else
        {
            return null;
        }
    }

    public Team getTeamWithPossession()
    {
        return mHasBallPossession;
    }

    public String getTitle()
    {
        String home = mHomeTeam.getName();
        int homeScore = mHomeTeam.getTotalScore();
        String away = mAwayTeam.getName();
        int awayScore = mAwayTeam.getTotalScore();
        return home + " VS " + away + " [Final Score: " + homeScore + "-" + awayScore + "]";
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

    public boolean isPeriodOngoing()
    {
        return mCurrentPeriodClock > 0;
    }

    public boolean isPossessionOngoing()
    {
        return mCurrentShotClock > 0;
    }

    public void nextPeriod()
    {
        mGameLog.nextPeriod();
        mPeriod = mGameLog.getCurrentPeriod();
        mPeriodLog = mGameLog.getCurrentPeriodLog();
        mIsShotClockOn = true;
        initializeClocks();
        resetPeriodFouls();
        setChanged();
        notifyObservers();
    }

    public void resetMidShotClock()
    {
        resetShotClock( mReducedMaxShotClock );
    }

    public void resetPeriodClock()
    {
        if( mPeriod < 4 )
        {
            mCurrentPeriodClock = mMaxRegulationPeriodClock;
        }
        else
        {
            mCurrentPeriodClock = mMaxOTPeriodClock;
        }
        setChanged();
        notifyObservers();
    }

    public void resetShotClock24()
    {
        resetShotClock( MAX_SHOT_CLOCK );
    }

    public void setTeamWithPossession( Team team )
    {
        mHasBallPossession = team;
        setChanged();
        notifyObservers();
    }

    public void startNewEvent()
    {
        mEventPeriodClock = mCurrentPeriodClock;
    }

    public void swapBallPossession()
    {
        if( mHasBallPossession.equals( mHomeTeam ) )
        {
            setTeamWithPossession( mAwayTeam );
        }
        else
        {
            setTeamWithPossession( mHomeTeam );
        }
        resetShotClock24();
    }

    public void updateTime()
    {
        if( isPeriodOngoing() && isPossessionOngoing() )
        {
            mHasBallPossession.updatePossessionTime();
            mAwayTeam.updatePlayingTime();
            mHomeTeam.updatePlayingTime();
            updatePeriodClock();
            updateShotClock();
            setChanged();
        }
        notifyObservers();
    }

    private int getNewCurrentShotClock( int max )
    {
        int timeDifference;
        if( mEventPeriodClock == -1 )
        {
            timeDifference = 0;
        }
        else
        {
            timeDifference = mEventPeriodClock - mCurrentPeriodClock;
        }
        return max - timeDifference;
    }

    private void initializeClocks()
    {
        resetPeriodClock();
        resetShotClock24();
        setChanged();
    }

    private void initializeLogs()
    {
        mGameLog = new GameLog();
        mPeriodLog = mGameLog.getCurrentPeriodLog();
        mPeriod = mGameLog.getCurrentPeriod();
    }

    private void resetPeriodFouls()
    {
        mHomePeriodFouls = 0;
        mAwayPeriodFouls = 0;
    }

    private void resetShotClock( int max )
    {
        if( mIsShotClockOn )
        {
            if( mCurrentShotClock < max )
            {
                mCurrentShotClock = getNewCurrentShotClock( max );
            }
            if( mCurrentShotClock > mCurrentPeriodClock )
            {
                turnOffShotClock();
            }
        }
        setChanged();
        notifyObservers();

    }

    private void turnOffShotClock()
    {
        mIsShotClockOn = false;
    }

    private void updatePeriodClock()
    {
        mCurrentPeriodClock--;
    }

    private void updateShotClock()
    {
        if( mIsShotClockOn )
        {
            mCurrentShotClock--;
        }
    }

    public enum GameStats
    {
        ID( "id" ), AWAY_TEAM( "awayTeam" ), HOME_TEAM( "homeTeam" ), GAME_LOG( "gameLog" ), PERIOD_LOG(
                "periodLog" ), DATE( "date" );

        private String mString;

        private GameStats( String string )
        {
            mString = string;
        }

        public String getString()
        {
            return mString;
        }
    }

    public class MyDate extends Date
    {
        public MyDate()
        {
            super();
        }

        public MyDate( long milliseconds )
        {
            super( milliseconds );
        }

        @Override
        public String toString()
        {
            return DateFormat.format( "EEE, MMM dd, yyyy", this ).toString();
        }
    }
}
