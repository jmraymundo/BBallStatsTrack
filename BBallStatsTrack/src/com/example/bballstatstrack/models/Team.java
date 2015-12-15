package com.example.bballstatstrack.models;

import java.util.ArrayList;
import java.util.List;

public class Team
{
    private String mName;

    private int mScore = 0;

    List< Player > mPlayerList = new ArrayList< Player >();

    List< Player > mInGamePlayerList = new ArrayList< Player >( 5 );

    public Team( String name, List< Player > playerList )
    {
        mName = name;
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
}
