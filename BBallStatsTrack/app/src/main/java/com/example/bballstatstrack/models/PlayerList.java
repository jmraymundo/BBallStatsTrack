package com.example.bballstatstrack.models;

import java.util.ArrayList;
import java.util.Iterator;

import android.os.Parcel;
import android.os.Parcelable;

public class PlayerList implements Parcelable, Iterable< Player >
{
    public static final Parcelable.Creator< PlayerList > CREATOR = new Parcelable.Creator< PlayerList >()
    {
        public PlayerList createFromParcel( Parcel in )
        {
            return new PlayerList( in );
        }

        public PlayerList[] newArray( int size )
        {
            return new PlayerList[size];
        }
    };

    private ArrayList< Player > mList;

    private ArrayList< Player > mStarters;

    private ArrayList< Player > mInGame;

    public PlayerList()
    {
        mList = new ArrayList< Player >();
        mStarters = new ArrayList< Player >( 5 );
        mInGame = new ArrayList< Player >( 5 );
    }

    public PlayerList( Parcel in )
    {
        this();
        in.readTypedList( mList, Player.CREATOR );
        in.readTypedList( mStarters, Player.CREATOR );
        in.readTypedList( mInGame, Player.CREATOR );
    }

    public void addPlayer( Player player )
    {
        mList.add( player );
    }

    public boolean contains( Player player )
    {
        return mList.contains( player );
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    public Player getPlayer( int playerNumber )
    {
        for( Player player : mList )
        {
            if( player.getNumber() == playerNumber )
            {
                return player;
            }
        }
        return null;
    }

    public int getSize()
    {
        return mList.size();
    }

    @Override
    public Iterator< Player > iterator()
    {
        return mList.iterator();
    }

    public Player playerAt( int index )
    {
        return mList.get( index );
    }

    public boolean removePlayer( int playerNumber )
    {
        Iterator< Player > iterator = mList.iterator();
        while( iterator.hasNext() )
        {
            if( playerNumber == iterator.next().getNumber() )
            {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    public void removePlayer( Player player )
    {
        Iterator< Player > iterator = mList.iterator();
        while( iterator.hasNext() )
        {
            if( player.equals( iterator.next() ) )
            {
                iterator.remove();
                return;
            }
        }
    }

    public void updatePlayingTime()
    {
        for( Player player : mInGame )
        {
            player.incrementPlayingTime();
        }
    }

    @Override
    public void writeToParcel( Parcel dest, int flags )
    {
        dest.writeTypedList( mList );
        dest.writeTypedList( mStarters );
        dest.writeTypedList( mInGame );
    }

    public void setStarter( int index, boolean isChecked )
    {
        Player player = mList.get( index );
        if( isChecked )
        {
            mStarters.add( player );
            mInGame.add( player );
        }
        else
        {
            mStarters.remove( player );
            mInGame.remove( player );
        }
    }

    public void substitute( Player in, Player out )
    {
        mInGame.remove( out );
        mInGame.add( in );
    }

    public boolean isStartersInvalid()
    {
        return mStarters.size() != 5;
    }

    public ArrayList< Player > getInGame()
    {
        return mInGame;
    }

    public void setStarter( Player player )
    {
        mStarters.add( player );
    }
}
