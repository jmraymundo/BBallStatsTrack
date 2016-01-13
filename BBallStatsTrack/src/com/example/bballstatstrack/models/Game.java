package com.example.bballstatstrack.models;

import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.UUID;

import com.example.bballstatstrack.models.game.GameLog;
import com.example.bballstatstrack.models.gameevents.GameEvent;
import com.example.bballstatstrack.models.gameevents.TurnoverEvent;
import com.example.bballstatstrack.models.gameevents.foulevents.NonShootingFoulEvent;
import com.example.bballstatstrack.models.gameevents.foulevents.ShootingFoulEvent;

import android.text.format.DateFormat;

public class Game extends Observable
{
    private static final int MAX_SHOT_CLOCK = 24;

    private Team mAwayTeam;

    private Team mHomeTeam;

    private Team mHasBallPossession;

    private int mPeriod = 0;

    private int mCurrentGameClock;

    private int mEventGameClock = -1;

    private int mCurrentShotClock;

    private final int mMaxGameClock;

    private final int mMaxOTGameClock;

    private final int mReducedMaxShotClock;

    private GameLog mGameLog;

    private List< GameEvent > mPeriodLog;

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
        mDate = new MyDate();
    }

    public Game( UUID id, long longDate, Team homeTeam, Team awayTeam, GameLog gameLog )
    {
        mMaxGameClock = 0;
        mMaxOTGameClock = 0;
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
        event.setTime( mEventGameClock );
        mPeriodLog.add( event );
        checkTeamFoulEvent( event );
        checkTurnoverEvent( event );
        endNewEvent();
        setChanged();
        notifyObservers();
    }

    public void endNewEvent()
    {
        mEventGameClock = -1;
    }

    public int getAwayPeriodFouls()
    {
        return mAwayPeriodFouls;
    }

    public Team getAwayTeam()
    {
        return mAwayTeam;
    }

    public int getCurrentGameClock()
    {
        return mCurrentGameClock;
    }

    public int getCurrentShotClock()
    {
        return mCurrentShotClock;
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
        return mCurrentGameClock > 0;
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
        initializeClocks();
        resetPeriodFouls();
        setChanged();
        notifyObservers();
    }

    public void resetGameClock()
    {
        if( mPeriod < 4 )
        {
            mCurrentGameClock = mMaxGameClock;
        }
        else
        {
            mCurrentGameClock = mMaxOTGameClock;
        }
        setChanged();
        notifyObservers();
    }

    public void resetMidShotClock()
    {
        if( mCurrentShotClock < mReducedMaxShotClock )
        {
            mCurrentShotClock = mReducedMaxShotClock;
        }
        setChanged();
        notifyObservers();
    }

    public void resetShotClock24()
    {
        int timeDifference;
        if( mEventGameClock == -1 )
        {
            timeDifference = 0;
        }
        else
        {
            timeDifference = mEventGameClock - mCurrentGameClock;
        }
        mCurrentShotClock = MAX_SHOT_CLOCK - timeDifference;
        setChanged();
        notifyObservers();
    }

    public void setTeamWithPossession( Team team )
    {
        mHasBallPossession = team;
        setChanged();
        notifyObservers();
    }

    public void startNewEvent()
    {
        mEventGameClock = mCurrentGameClock;
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
            mCurrentGameClock--;
            mCurrentShotClock--;
            setChanged();
        }
        notifyObservers();
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

    private void checkTeamFoulEvent( GameEvent event )
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

    private void checkTurnoverEvent( GameEvent event )
    {
        if( event == null )
        {
            return;
        }
        if( event instanceof TurnoverEvent )
        {
            swapBallPossession();
        }
    }

    private void initializeClocks()
    {
        resetGameClock();
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
