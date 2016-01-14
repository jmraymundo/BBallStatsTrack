package com.example.bballstatstrack.models.game;

import java.util.ArrayList;
import java.util.List;

import com.example.bballstatstrack.models.gameevents.GameEvent;
import com.example.bballstatstrack.models.utils.StringUtils;

import android.util.SparseArray;

public class GameLog extends SparseArray< List< GameEvent > >
{
    private int mCurrentPeriod;

    public GameLog()
    {
        mCurrentPeriod = 0;
        put( mCurrentPeriod, new ArrayList< GameEvent >() );
    }

    public int getCurrentPeriod()
    {
        return mCurrentPeriod;
    }

    public List< GameEvent > getCurrentPeriodLog()
    {
        return get( mCurrentPeriod );
    }

    public void nextPeriod()
    {
        mCurrentPeriod++;
        put( mCurrentPeriod, new ArrayList< GameEvent >() );
    }

    public ArrayList< String > toStringList()
    {
        ArrayList< String > output = new ArrayList< String >();
        for( int index = size() - 1; index >= 0; index-- )
        {
            List< GameEvent > periodLog = valueAt( index );
            for( int innerIndex = periodLog.size() - 1; innerIndex >= 0; innerIndex-- )
            {
                GameEvent event = periodLog.get( innerIndex );
                String time = StringUtils.getMinSecFormattedString( event.getTime() );
                output.add( time + " : " + event.toString() );
            }
            output.add( "Start of " + StringUtils.getPeriodString( index ) );
        }
        return output;
    }
}
