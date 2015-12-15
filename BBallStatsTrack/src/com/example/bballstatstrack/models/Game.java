package com.example.bballstatstrack.models;

public class Game
{
    private Team mAwayTeam;

    private Team mHomeTeam;

    private Team mHasBallPossession;

    private boolean isGameOngoing = false;

    private int mPeriod = 0;

    private int mCurrentGameClock;

    private int mCurrentShotClock;

    private static final int MAX_SHOT_CLOCK = 24;

    private final int mMaxGameClock;

    private final int mReducedMaxShotClock;

    public Game( int maxGameClock, int resetShotClock )
    {
        mMaxGameClock = maxGameClock;
        mReducedMaxShotClock = resetShotClock;
        initializeClocks();
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

    public void setTeamWithPossession( Team newTeam )
    {
        mHasBallPossession = newTeam;
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
