package com.example.bballstatstrack.models;

import org.json.JSONObject;

public class Player
{
    private int mNumber;

    private String mFirstName;

    private String mLastName;

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

    private int mFoulCount = 0;

    private int mPlayingTimeSec = 0;

    public Player( int id, String firstName, String lastName )
    {
        setNumber( id );
        setFirstName( firstName );
        setLastName( lastName );
    }

    public int getNumber()
    {
        return mNumber;
    }

    public void setNumber( int number )
    {
        mNumber = number;
    }

    public String getFirstName()
    {
        return mFirstName;
    }

    public void setFirstName( String firstName )
    {
        mFirstName = firstName;
    }

    public String getLastName()
    {
        return mLastName;
    }

    public void setLastName( String lastName )
    {
        mLastName = lastName;
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
        mFoulCount++;
    }

    public int getFoulCount()
    {
        return mFoulCount;
    }

    public void incrementPlayingTime()
    {
        mPlayingTimeSec++;
    }

    public int getPlayingTimeSec()
    {
        return mPlayingTimeSec;
    }

    public JSONObject toJSON()
    {
        JSONObject jsonObject = new JSONObject();
    }
}
