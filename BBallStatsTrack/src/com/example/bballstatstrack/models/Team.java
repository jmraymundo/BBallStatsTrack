package com.example.bballstatstrack.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import android.util.SparseArray;

public class Team
{
    private static final String B_BALL_STAT_TRACK = "BBallStatTrack";

    private String mName;

    SparseArray< Player > mPlayerList = new SparseArray< Player >();

    List< Player > mInGamePlayerList = new ArrayList< Player >( 5 );

    private int mTotalFouls;

    private int mTeamRebound;

    private int mTimeOuts;

    private UUID mID;

    public Team( JSONObject team )
    {
        try
        {
            setName( team.getString( TeamStats.NAME.toString() ) );
            setPlayerList( team );
            setIngamePlayerList( team );
            mTotalFouls = team.getInt( TeamStats.TOTAL_FOULS.toString() );
            mTeamRebound = team.getInt( TeamStats.TEAM_REBOUNDS.toString() );
            mTimeOuts = team.getInt( TeamStats.TIMEOUTS.toString() );
            mID = UUID.fromString( team.getString( TeamStats.TEAM_ID.toString() ) );
        }
        catch( JSONException e )
        {
            e.printStackTrace();
            Log.e( B_BALL_STAT_TRACK, "Attribute missing from Team JSONObject!", e );
        }
    }

    public Team( String name, List< Player > playerList )
    {
        setName( name );
        mID = UUID.nameUUIDFromBytes( name.getBytes() );
        for( Player player : playerList )
        {
            mPlayerList.append( player.getNumber(), player );
        }
    }

    public void addFoul()
    {
        mTotalFouls++;
    }

    public void addStarter( int playerNumber )
    {
        mInGamePlayerList.add( mPlayerList.get( playerNumber ) );
    }

    public int get2ptFGMade()
    {
        int total = 0;
        Player player;
        for( int index = 0; index < mPlayerList.size(); index++ )
        {
            player = mPlayerList.valueAt( index );
            total += player.get2ptFGMade();
        }
        return total;
    }

    public int get2ptFGMiss()
    {
        int total = 0;
        Player player;
        for( int index = 0; index < mPlayerList.size(); index++ )
        {
            player = mPlayerList.valueAt( index );
            total += player.get2ptFGMiss();
        }
        return total;
    }

    public int get3ptFGMade()
    {
        int total = 0;
        Player player;
        for( int index = 0; index < mPlayerList.size(); index++ )
        {
            player = mPlayerList.valueAt( index );
            total += player.get3ptFGMade();
        }
        return total;
    }

    public int get3ptFGMiss()
    {
        int total = 0;
        Player player;
        for( int index = 0; index < mPlayerList.size(); index++ )
        {
            player = mPlayerList.valueAt( index );
            total += player.get3ptFGMiss();
        }
        return total;
    }

    public int getFTMade()
    {
        int total = 0;
        Player player;
        for( int index = 0; index < mPlayerList.size(); index++ )
        {
            player = mPlayerList.valueAt( index );
            total += player.getFTMade();
        }
        return total;
    }

    public int getFTMiss()
    {
        int total = 0;
        Player player;
        for( int index = 0; index < mPlayerList.size(); index++ )
        {
            player = mPlayerList.valueAt( index );
            total += player.getFTMiss();
        }
        return total;
    }

    public UUID getID()
    {
        return mID;
    }

    public List< Player > getInGamePlayers()
    {
        return mInGamePlayerList;
    }

    public String getName()
    {
        return mName;
    }

    public SparseArray< Player > getPlayers()
    {
        return mPlayerList;
    }

    public int getTeamRebounds()
    {
        return mTeamRebound;
    }

    public int getTimeOuts()
    {
        return mTimeOuts;
    }

    public int getTotalFouls()
    {
        return mTotalFouls;
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

    public int getTotalScore()
    {
        return getFTMade() + ( get2ptFGMade() * 2 ) + ( get3ptFGMade() * 3 );
    }

    public void makeTeamRebound()
    {
        mTeamRebound++;
    }

    public void setName( String name )
    {
        mName = name;
    }

    public void setTimeOuts( int timeOuts )
    {
        mTimeOuts = timeOuts;
    }

    public void substitutePlayer( Player in, Player out )
    {
        mInGamePlayerList.remove( out );
        mInGamePlayerList.add( in );
    }

    public void updatePlayingTime()
    {
        for( Player player : mInGamePlayerList )
        {
            player.incrementPlayingTime();
        }
    }

    public void useTimeOut()
    {
        mTimeOuts--;
    }

    private void setIngamePlayerList( JSONObject team ) throws JSONException
    {
        JSONArray jsonArray = team.getJSONArray( TeamStats.INGAME_PLAYER_LIST.toString() );
        for( int index = 0; index < jsonArray.length(); index++ )
        {
            int playerNumber = jsonArray.getInt( index );
            mInGamePlayerList.add( mPlayerList.get( playerNumber ) );
        }
    }

    private void setPlayerList( JSONObject team ) throws JSONException
    {
        JSONArray jsonArray = team.getJSONArray( TeamStats.PLAYER_LIST.toString() );
        for( int index = 0; index < jsonArray.length(); index++ )
        {
            Player player = new Player( jsonArray.getJSONObject( index ) );
            mPlayerList.put( player.getNumber(), player );
        }
    }

    public enum TeamStats
    {
        NAME( "name" ), PLAYER_LIST( "playerList" ), INGAME_PLAYER_LIST( "inGamePlayerList" ), TOTAL_FOULS(
                "totalFouls" ), TEAM_REBOUNDS( "teamRebound" ), TIMEOUTS( "timeOuts" ), TEAM_ID( "signature" );

        private final String mConstant;

        private TeamStats( String constant )
        {
            mConstant = constant;
        }

        @Override
        public String toString()
        {
            return mConstant;
        }
    }
}