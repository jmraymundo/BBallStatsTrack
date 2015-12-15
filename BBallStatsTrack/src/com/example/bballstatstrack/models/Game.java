package com.example.bballstatstrack.models;

public class Game
{
    private Team mAwayTeam;

    private Team mHomeTeam;

    private Team mHasBallPossession;

    private boolean isGameOngoing = false;

    private int mQuarter = 0;

    private int mCurrentGameClock;

    private int mCurrentShotClock;

    private static final int MAX_SHOT_CLOCK = 24;

    private final int mMaxGameClock;

    private final int mReducedMaxShotClock;

    private GameTimer mTimerThread = new GameTimer();

    public Game( int maxGameClock, int resetShotClock )
    {
        mMaxGameClock = maxGameClock;
        mReducedMaxShotClock = resetShotClock;
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

    public void continueGame()
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

    private class GameTimer implements Runnable
    {
        public void run()
        {
            if( isGameOngoing )
            {
                mAwayTeam.updatePlayingTime();
                mHomeTeam.updatePlayingTime();
                mCurrentGameClock--;
                mCurrentShotClock--;
            }
            try
            {
                Thread.sleep( 1000 );
            }
            catch( InterruptedException e )
            {
                e.printStackTrace();
            }
        }
    };
}
