package com.example.bballstatstrack.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Player implements Parcelable
{
    public static final Parcelable.Creator< Player > CREATOR = new Parcelable.Creator< Player >()
    {
        @Override
        public Player createFromParcel( Parcel source )
        {
            return new Player( source );
        }

        @Override
        public Player[] newArray( int size )
        {
            return new Player[size];
        }
    };

    private int mNumber;

    private String mFullName;

    private int m2ptFGMiss;

    private int m2ptFGMade;

    private int m3ptFGMiss;

    private int m3ptFGMade;

    private int mFTMiss;

    private int mFTMade;

    private int mOffRebound;

    private int mDefRebound;

    private int mAssist;

    private int mTurnover;

    private int mSteal;

    private int mBlock;

    private int mFoul;

    private int mPlayingTimeSec;

    public Player()
    {
        setNumber( Integer.MIN_VALUE );
        setFullName( null );
        m2ptFGMiss = 0;
        m2ptFGMade = 0;
        m3ptFGMiss = 0;
        m3ptFGMade = 0;
        mFTMiss = 0;
        mFTMade = 0;
        mOffRebound = 0;
        mDefRebound = 0;
        mAssist = 0;
        mTurnover = 0;
        mSteal = 0;
        mBlock = 0;
        mFoul = 0;
        mPlayingTimeSec = 0;
    }

    public Player( int number, String fullName )
    {
        setNumber( number );
        setFullName( fullName );
    }

    public Player( int number, String fullName, int miss1pt, int miss2pt, int miss3pt, int made1pt, int made2pt,
            int made3pt, int offReb, int defReb, int assist, int to, int stl, int blk, int foul, int playingTimeSec )
    {
        this( number, fullName );
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

    public Player( Parcel source )
    {
        mNumber = source.readInt();
        mFullName = source.readString();
        m2ptFGMiss = source.readInt();
        m2ptFGMade = source.readInt();
        m3ptFGMiss = source.readInt();
        m3ptFGMade = source.readInt();
        mFTMiss = source.readInt();
        mFTMade = source.readInt();
        mOffRebound = source.readInt();
        mDefRebound = source.readInt();
        mAssist = source.readInt();
        mTurnover = source.readInt();
        mSteal = source.readInt();
        mBlock = source.readInt();
        mFoul = source.readInt();
        mPlayingTimeSec = source.readInt();
    }

    @Override
    public int describeContents()
    {
        return 0;
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

    public int getTotalScore()
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

    @Override
    public void writeToParcel( Parcel dest, int flags )
    {
        dest.writeInt( mNumber );
        dest.writeString( mFullName );
        dest.writeInt( m2ptFGMiss );
        dest.writeInt( m2ptFGMade );
        dest.writeInt( m3ptFGMiss );
        dest.writeInt( m3ptFGMade );
        dest.writeInt( mFTMiss );
        dest.writeInt( mFTMade );
        dest.writeInt( mOffRebound );
        dest.writeInt( mDefRebound );
        dest.writeInt( mAssist );
        dest.writeInt( mTurnover );
        dest.writeInt( mSteal );
        dest.writeInt( mBlock );
        dest.writeInt( mFoul );
        dest.writeInt( mPlayingTimeSec );
    }

    public enum PlayerStats
    {
        NUMBER( "number" ), NAME( "fullName" ), MISS_1PT( "ftMiss" ), MADE_1PT( "ftMade" ), MISS_2PT(
                "2ptFGMiss" ), MADE_2PT( "2ptFGMade" ), MISS_3PT( "3ptFGMiss" ), MADE_3PT(
                        "3ptFGMade" ), OFFENSIVE_REBOUND( "offRebound" ), DEFENSIVE_REBOUND( "defRebound" ), ASSIST(
                                "assist" ), TURNOVER( "turnover" ), STEAL( "steal" ), BLOCK( "block" ), FOUL(
                                        "foul" ), PLAYING_TIME( "playingTimeSec" );

        private final String mString;

        private PlayerStats( String string )
        {
            mString = string;
        }

        public String getString()
        {
            return mString;
        }
    }
}
