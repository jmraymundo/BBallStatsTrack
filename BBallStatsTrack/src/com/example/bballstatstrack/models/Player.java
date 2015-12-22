package com.example.bballstatstrack.models;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Player
{
    private static final String B_BALL_STAT_TRACK = "BBallStatTrack";

    public enum PlayerStats
    {
        NUMBER( "number" ), NAME( "fullName" ), MISS_1PT( "ftMiss" ), MADE_1PT( "ftMade" ), MISS_2PT(
                "2ptFGMiss" ), MADE_2PT( "2ptFGMade" ), MISS_3PT( "3ptFGMiss" ), MADE_3PT(
                        "3ptFGMade" ), OFFENSIVE_REBOUND( "offRebound" ), DEFENSIVE_REBOUND( "defRebound" ), ASSIST(
                                "assist" ), TURNOVER( "turnover" ), STEAL( "steal" ), BLOCK( "block" ), FOUL(
                                        "foul" ), PLAYING_TIME( "playingTimeSec" );

        private final String mConstant;

        private PlayerStats( String constant )
        {
            mConstant = constant;
        }

        public String toString()
        {
            return mConstant;
        }
    }

    private int mNumber;

    private String mFullName;

    private int m2ptFGMiss = 0;

    private int m2ptFGMade = 0;

    private int m3ptFGMiss = 0;

    private int m3ptFGMade = 0;

    private int mFTMiss = 0;

    private int mFTMade = 0;

    private int mOffRebound = 0;

    private int mDefRebound = 0;

    private int mAssist = 0;

    private int mTurnover = 0;

    private int mSteal = 0;

    private int mBlock = 0;

    private int mFoul = 0;

    private int mPlayingTimeSec = 0;

    public Player( int id, String fullName )
    {
        setNumber( id );
        setFullName( fullName );
    }

    public Player( JSONObject player )
    {
        try
        {
            setNumber( player.getInt( PlayerStats.NUMBER.toString() ) );
            setFullName( player.getString( PlayerStats.NAME.toString() ) );
            mFTMade = player.getInt( PlayerStats.MADE_1PT.toString() );
            mFTMiss = player.getInt( PlayerStats.MISS_1PT.toString() );
            m2ptFGMade = player.getInt( PlayerStats.MADE_2PT.toString() );
            m2ptFGMiss = player.getInt( PlayerStats.MISS_2PT.toString() );
            m3ptFGMade = player.getInt( PlayerStats.MADE_3PT.toString() );
            m3ptFGMiss = player.getInt( PlayerStats.MISS_3PT.toString() );
            mOffRebound = player.getInt( PlayerStats.OFFENSIVE_REBOUND.toString() );
            mDefRebound = player.getInt( PlayerStats.DEFENSIVE_REBOUND.toString() );
            mAssist = player.getInt( PlayerStats.ASSIST.toString() );
            mTurnover = player.getInt( PlayerStats.TURNOVER.toString() );
            mSteal = player.getInt( PlayerStats.STEAL.toString() );
            mBlock = player.getInt( PlayerStats.BLOCK.toString() );
            mFoul = player.getInt( PlayerStats.FOUL.toString() );
            mPlayingTimeSec = player.getInt( PlayerStats.PLAYING_TIME.toString() );
        }
        catch( JSONException e )
        {
            e.printStackTrace();
            Log.e( B_BALL_STAT_TRACK, "Attribute missing from Player JSONObject!", e );
        }
    }

    public int getNumber()
    {
        return mNumber;
    }

    public void setNumber( int number )
    {
        mNumber = number;
    }

    public String getFullName()
    {
        return mFullName;
    }

    public void setFullName( String fullName )
    {
        mFullName = fullName;
    }

    public void shoot2pt( boolean made )
    {
        if( made )
        {
            m2ptFGMade++;
        }
        else
        {
            m2ptFGMiss++;
        }
    }

    public int get2ptFGMiss()
    {
        return m2ptFGMiss;
    }

    public int get2ptFGMade()
    {
        return m2ptFGMade;
    }

    public void shoot3pt( boolean made )
    {
        if( made )
        {
            m3ptFGMade++;
        }
        else
        {
            m3ptFGMiss++;
        }
    }

    public int get3ptFGMiss()
    {
        return m3ptFGMiss;
    }

    public int get3ptFGMade()
    {
        return m3ptFGMade;
    }

    public void shootFT( boolean made )
    {
        if( made )
        {
            mFTMade++;
        }
        else
        {
            mFTMiss++;
        }
    }

    public int getFTMiss()
    {
        return mFTMiss;
    }

    public int getFTMade()
    {
        return mFTMade;
    }

    public void makeRebound( boolean offensive )
    {
        if( offensive )
        {
            mOffRebound++;
        }
        else
        {
            mDefRebound++;
        }
    }

    public int getOffRebound()
    {
        return mOffRebound;
    }

    public int getDefRebound()
    {
        return mDefRebound;
    }

    public void makeAssist()
    {
        mAssist++;
    }

    public int getAssist()
    {
        return mAssist;
    }

    public void makeTurnover()
    {
        mTurnover++;
    }

    public int getTurnover()
    {
        return mTurnover;
    }

    public void makeSteal()
    {
        mSteal++;
    }

    public int getSteal()
    {
        return mSteal;
    }

    public void makeBlock()
    {
        mBlock++;
    }

    public int getBlock()
    {
        return mBlock;
    }

    public void makeFoul()
    {
        mFoul++;
    }

    public int getFoulCount()
    {
        return mFoul;
    }

    public void incrementPlayingTime()
    {
        mPlayingTimeSec++;
    }

    public int getPlayingTimeSec()
    {
        return mPlayingTimeSec;
    }
}
