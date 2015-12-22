package com.example.bballstatstrack.models.game;

import com.example.bballstatstrack.models.gameevents.GameEvent;

import android.util.SparseArray;

public class GameLog extends SparseArray< SparseArray< GameEvent > >
{
    private int mCurrentPeriod;

    public GameLog()
    {
        mCurrentPeriod = 0;
        put( mCurrentPeriod, new SparseArray< GameEvent >() );
    }

    public int getCurrentPeriod()
    {
        return mCurrentPeriod;
    }

    public SparseArray< GameEvent > getCurrentPeriodLog()
    {
        return get( mCurrentPeriod );
    }

    public void nextPeriod()
    {
        mCurrentPeriod++;
        put( mCurrentPeriod, new SparseArray< GameEvent >() );
    }
}
