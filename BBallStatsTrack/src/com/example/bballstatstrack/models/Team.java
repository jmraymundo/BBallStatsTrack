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

    private String mName;

    SparseArray< Player > mPlayerList = new SparseArray< Player >();

    List< Player > mInGamePlayerList = new ArrayList< Player >( 5 );

    private int mTotalFouls;

    private int mTeamRebound;

    private int mTimeOuts;

    private UUID mID;

    public Team( String name, List< Player > playerList )
    {
        setName( name );
        mID = UUID.nameUUIDFromBytes( name.getBytes() );
        for( Player player : playerList )
        {
            mPlayerList.append( player.getNumber(), player );
        }
    }

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
            mID = ( UUID ) team.get( TeamStats.TEAM_ID.toString() );
        }
        catch( JSONException e )
        {
            e.printStackTrace();
            Log.e( B_BALL_STAT_TRACK, "Attribute missing from Team JSONObject!", e );
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

    private void setIngamePlayerList( JSONObject team ) throws JSONException
    {
        JSONArray jsonArray = team.getJSONArray( TeamStats.INGAME_PLAYER_LIST.toString() );
        for( int index = 0; index < jsonArray.length(); index++ )
        {
            int playerNumber = jsonArray.getInt( index );
            mInGamePlayerList.add( mPlayerList.get( playerNumber ) );
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

    public void addFoul()
    {
        mTotalFouls++;
    }

    public int getTotalFouls()
    {
        return mTotalFouls;
    }

    public void makeTeamRebound()
    {
        mTeamRebound++;
    }

    public int getTeamRebounds()
    {
        return mTeamRebound;
    }

    public void useTimeOut()
    {
        mTimeOuts--;
    }

    public int getTimeOuts()
    {
        return mTimeOuts;
    }

    public void setTimeOuts( int timeOuts )
    {
        mTimeOuts = timeOuts;
    }

    public UUID getID()
    {
        return mID;
    }
}