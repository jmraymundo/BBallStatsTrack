package com.example.bballstatstrack.models;

import java.util.ArrayList;
import java.util.List;

public class Team
{
    private String mName;

    List< Player > mPlayerList = new ArrayList< Player >();

    List< Player > mInGamePlayerList = new ArrayList< Player >( 5 );

    public Team( String name, List< Player > playerList )
    {
        setName( name );
        mPlayerList = playerList;
    }

    public List< Player > getPlayers()
    {
        return mPlayerList;
    }

    public List< Player > getInGamePlayers()
    {
        return mInGamePlayerList;
    }

    public void selectStarters( List< Integer > starters )
    {
        for( Player player : mPlayerList )
        {
            if( starters.contains( player.getNumber() ) )
            {
                mInGamePlayerList.add( player );
            }
        }
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
        for( Player player : mPlayerList )
        {
            score += ( player.get2ptFGMade() * 2 ) + ( player.get3ptFGMade() * 3 ) + player.getFTMade();
        }
        return score;
    }

    public int getTotalRebounds()
    {
        int rebounds = 0;
        for( Player player : mPlayerList )
        {
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
