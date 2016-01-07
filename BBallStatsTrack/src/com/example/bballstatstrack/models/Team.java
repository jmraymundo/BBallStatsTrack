package com.example.bballstatstrack.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.bballstatstrack.models.Player.PlayerStats;

import android.util.Log;
import android.util.SparseArray;

public class Team
{
    private static final String B_BALL_STAT_TRACK = "BBallStatTrack";

    private String mName;

    SparseArray< Player > mPlayerList = new SparseArray< Player >();

    List< Player > mInGamePlayerList = new ArrayList< Player >( 5 );

    private int mTeamFouls;

    private int mTeamRebound;

    private int mTimeOuts;

    private int mPossessionTime = 0;

    private UUID mID;

    public Team( JSONObject team )
    {
        try
        {
            setName( team.getString( TeamStats.NAME.toString() ) );
            setPlayerList( team );
            setIngamePlayerList( team );
            mTeamFouls = team.getInt( TeamStats.TOTAL_FOULS.toString() );
            mTeamRebound = team.getInt( TeamStats.TEAM_REBOUNDS.toString() );
            mTimeOuts = team.getInt( TeamStats.TIMEOUTS.toString() );
            mPossessionTime = team.getInt( TeamStats.POSSESSION_TIME.toString() );
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
        mTeamFouls++;
    }

    public void addStarter( int playerNumber )
    {
        mInGamePlayerList.add( mPlayerList.get( playerNumber ) );
    }

    public int get2ptFGMade()
    {

        return getStats( PlayerStats.MADE_2PT );
    }

    public int get2ptFGMiss()
    {

        return getStats( PlayerStats.MISS_2PT );
    }

    public int get3ptFGMade()
    {

        return getStats( PlayerStats.MADE_3PT );
    }

    public int get3ptFGMiss()
    {

        return getStats( PlayerStats.MISS_3PT );
    }

    public int getAssists()
    {

        return getStats( PlayerStats.ASSIST );
    }

    public int getBlocks()
    {

        return getStats( PlayerStats.BLOCK );
    }

    public int getDefRebounds()
    {

        return getStats( PlayerStats.DEFENSIVE_REBOUND );
    }

    public int getFTMade()
    {

        return getStats( PlayerStats.MADE_1PT );
    }

    public int getFTMiss()
    {

        return getStats( PlayerStats.MISS_1PT );
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

    public int getOffRebounds()
    {

        return getStats( PlayerStats.OFFENSIVE_REBOUND );
    }

    public SparseArray< Player > getPlayers()
    {
        return mPlayerList;
    }

    public int getPossessionTimeSec()
    {
        return mPossessionTime;
    }

    public int getSteals()
    {
        return getStats( PlayerStats.STEAL );
    }

    public int getTeamFouls()
    {
        return mTeamFouls;
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
        return getStats( PlayerStats.FOUL );
    }

    public int getTotalRebounds()
    {
        return getOffRebounds() + getDefRebounds();
    }

    public int getTotalScore()
    {
        return getFTMade() + ( get2ptFGMade() * 2 ) + ( get3ptFGMade() * 3 );
    }

    public int getTurnovers()
    {
        return getStats( PlayerStats.TURNOVER );
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

    public void updatePossessionTime()
    {
        mPossessionTime++;
    }

    public void useTimeOut()
    {
        mTimeOuts--;
    }

    private int getStats( PlayerStats stat )
    {
        int total = 0;
        Player player;
        for( int index = 0; index < mPlayerList.size(); index++ )
        {
            player = mPlayerList.valueAt( index );
            switch( stat )
            {
                case ASSIST:
                    total += player.getAssist();
                    break;
                case BLOCK:
                    total += player.getBlock();
                    break;
                case DEFENSIVE_REBOUND:
                    total += player.getDefRebound();
                    break;
                case FOUL:
                    total += player.getFoulCount();
                    break;
                case MADE_1PT:
                    total += player.getFTMade();
                    break;
                case MADE_2PT:
                    total += player.get2ptFGMade();
                    break;
                case MADE_3PT:
                    total += player.get3ptFGMade();
                    break;
                case MISS_1PT:
                    total += player.getFTMiss();
                    break;
                case MISS_2PT:
                    total += player.get2ptFGMiss();
                    break;
                case MISS_3PT:
                    total += player.get3ptFGMiss();
                    break;
                case OFFENSIVE_REBOUND:
                    total += player.getOffRebound();
                    break;
                case STEAL:
                    total += player.getSteal();
                    break;
                case TURNOVER:
                    total += player.getTurnover();
                    break;
                default:
                    break;
            }
        }
        return total;
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
                "totalFouls" ), TEAM_REBOUNDS( "teamRebound" ), TIMEOUTS( "timeOuts" ), POSSESSION_TIME(
                        "possessionTime" ), TEAM_ID( "signature" );

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