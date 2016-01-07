package com.example.bballstatstrack.models;

public class Player
{
    private static final String B_BALL_STAT_TRACK = "BBallStatTrack";

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

    public Player( int number, String fullName, int miss1pt, int miss2pt, int miss3pt, int made1pt, int made2pt,
            int made3pt, int offReb, int defReb, int assist, int to, int stl, int blk, int foul, int playingTimeSec )
    {
        mNumber = number;
        mFullName = fullName;
        mFTMiss = miss1pt;
        m2ptFGMiss = miss2pt;
        m3ptFGMiss = miss3pt;
        mFTMade = made1pt;
        m2ptFGMade = made2pt;
        m3ptFGMade = made3pt;
        mOffRebound = offReb;
        mDefRebound = defReb;
        mAssist = assist;
        mTurnover = to;
        mSteal = stl;
        mBlock = blk;
        mFoul = foul;
        mPlayingTimeSec = playingTimeSec;
    }

    public int get2ptFGMade()
    {
        return m2ptFGMade;
    }

    public int get2ptFGMiss()
    {
        return m2ptFGMiss;
    }

    public int get3ptFGMade()
    {
        return m3ptFGMade;
    }

    public int get3ptFGMiss()
    {
        return m3ptFGMiss;
    }

    public int getAssist()
    {
        return mAssist;
    }

    public int getBlock()
    {
        return mBlock;
    }

    public int getDefRebound()
    {
        return mDefRebound;
    }

    public int getFoulCount()
    {
        return mFoul;
    }

    public int getFTMade()
    {
        return mFTMade;
    }

    public int getFTMiss()
    {
        return mFTMiss;
    }

    public String getFullName()
    {
        return mFullName;
    }

    public int getNumber()
    {
        return mNumber;
    }

    public int getOffRebound()
    {
        return mOffRebound;
    }

    public int getPlayingTimeSec()
    {
        return mPlayingTimeSec;
    }

    public int getSteal()
    {
        return mSteal;
    }

    public int getTotalscore()
    {
        return getFTMade() + ( get2ptFGMade() * 2 ) + ( get3ptFGMade() * 3 );
    }

    public int getTurnover()
    {
        return mTurnover;
    }

    public void incrementPlayingTime()
    {
        mPlayingTimeSec++;
    }

    public void makeAssist()
    {
        mAssist++;
    }

    public void makeBlock()
    {
        mBlock++;
    }

    public void makeFoul()
    {
        mFoul++;
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

    public void makeSteal()
    {
        mSteal++;
    }

    public void makeTurnover()
    {
        mTurnover++;
    }

    public void setFullName( String fullName )
    {
        mFullName = fullName;
    }

    public void setNumber( int number )
    {
        mNumber = number;
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

    @Override
    public String toString()
    {
        return getNumber() + " - " + getFullName();
    }

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
}
