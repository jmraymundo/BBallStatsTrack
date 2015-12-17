package com.example.bballstatstrack.models;

import java.util.ArrayList;
import java.util.List;

import android.util.SparseArray;

public class Team
{
    private String mName;

    SparseArray< Player > mPlayerList = new SparseArray< Player >();

    List< Player > mInGamePlayerList = new ArrayList< Player >( 5 );

    public Team( String name, List< Player > playerList )
    {
        setName( name );
        for( Player player : playerList )
        {
            mPlayerList.append( player.getNumber(), player );
        }
    }

    public SparseArray< Player > getPlayers()
    {
        return mPlayerList;
    }

    public List< Player > getInGamePlayers()
    {
        return mInGamePlayerList;
    }

    public void addStarter( int playerNumber )
    {
        mInGamePlayerList.add( mPlayerList.get( playerNumber ) );
    }

    public void updatePlayingTime()
    {
        for( Player player : mInGamePlayerList )
        {
            player.incrementPlayingTime();
        }
    }

    public void substitutePlayer( Player in, Player out )
    {
        mInGamePlayerList.remove( out );
        mInGamePlayerList.add( in );
    }

    public int getTotalScore()
    {
        int score = 0;
        for( int index = 0; index < mPlayerList.size(); index++ )
        {
            Player player = mPlayerList.valueAt( index );
            score += ( player.get2ptFGMade() * 2 ) + ( player.get3ptFGMade() * 3 ) + player.getFTMade();
        }
        return score;
    }

    public int getTotalRebounds()
    {
        int rebounds = 0;
        for( int index = 0; index < mPlayerList.size(); index++ )
        {
            Player player = mPlayerList.valueAt( index );
            rebounds += player.getOffRebound() + player.getDefRebound();
        }
        return rebounds;
    }

    public String getName()
    {
        return mName;
    }

    public void setName( String name )
    {
        mName = name;
    }
}
