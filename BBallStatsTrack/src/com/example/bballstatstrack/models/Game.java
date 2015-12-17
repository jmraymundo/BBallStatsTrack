package com.example.bballstatstrack.models;

import org.json.JSONObject;

import com.example.bballstatstrack.models.gameevents.GameEvent;

import android.util.SparseArray;

public class Game
{
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
        mMaxGameClock = maxGameClock;
        mReducedMaxShotClock = resetShotClock;
        mAwayTeam = awayTeam;
        mHomeTeam = homeTeam;
        initializeClocks();
        pauseGame();
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
}
