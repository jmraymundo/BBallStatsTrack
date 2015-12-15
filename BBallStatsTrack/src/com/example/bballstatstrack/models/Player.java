package com.example.bballstatstrack.models;

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

    public int get2ptFGMiss()
    {
        return m2ptFGMiss;
    }

    public int get2ptFGMade()
    {
        return m2ptFGMade;
    }

    public int get3ptFGMiss()
    {
        return m3ptFGMiss;
    }

    public int get3ptFGMade()
    {
        return m3ptFGMade;
    }

    public int getFTMiss()
    {
        return mFTMiss;
    }

    public int getFTMade()
    {
        return mFTMade;
    }

    public int getOffRebound()
    {
        return mOffRebound;
    }

    public int getDefRebound()
    {
        return mDefRebound;
    }

    public int getAssist()
    {
        return mAssist;
    }

    public int getTurnover()
    {
        return mTurnover;
    }

    public int getSteal()
    {
        return mSteal;
    }

    public int getBlock()
    {
        return mBlock;
    }

    public int getFoulCount()
    {
        return mFoulCount;
    }

    public int getPlayingTimeSec()
    {
        return mPlayingTimeSec;
    }

    public void incrementPlayingTime()
    {
        mPlayingTimeSec++;
    }
}
