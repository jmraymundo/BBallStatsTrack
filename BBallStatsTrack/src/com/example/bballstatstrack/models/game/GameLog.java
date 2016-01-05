package com.example.bballstatstrack.models.game;

import java.util.ArrayList;
import java.util.List;

import com.example.bballstatstrack.models.gameevents.GameEvent;
import com.example.bballstatstrack.models.utils.StringUtils;

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

    public List< String > toStringList()
    {
        List< String > output = new ArrayList< String >();
        for( int index = 0; index < size(); index++ )
        {
            SparseArray< GameEvent > periodLog = valueAt( index );
            for( int innerIndex = 0; innerIndex < periodLog.size(); innerIndex++ )
            {
                output.add( periodLog.valueAt( innerIndex ).toString() );
            }
            output.add( "Start of " + StringUtils.getPeriodString( index ) );
        }
        return output;
    }
}
