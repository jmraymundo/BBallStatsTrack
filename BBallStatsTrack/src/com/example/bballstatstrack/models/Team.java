package com.example.bballstatstrack.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.UUID;

import com.example.bballstatstrack.models.Player.PlayerStats;

import android.os.Parcel;
import android.os.Parcelable;

public class Team extends Observable implements Parcelable
{
    public static final Parcelable.Creator< Team > CREATOR = new Parcelable.Creator< Team >()
    {
        @Override
        public Team createFromParcel( Parcel source )
        {
            return new Team( source );
        }

        @Override
        public Team[] newArray( int size )
        {
            return new Team[size];
        }
    };

    private UUID mID;

    private String mName;

    private PlayerList mPlayerList;

    private int mTeamFouls;

    private int mTeamRebound;

    private int mTimeOuts;

    private int mPossessionTime;

    public Team()
    {
        mPlayerList = new PlayerList();
        mTeamFouls = 0;
        mTeamRebound = 0;
        mTimeOuts = 0;
        mPossessionTime = 0;
    }

    public Team( Parcel source )
    {
        mID = UUID.fromString( source.readString() );
        mName = source.readString();
        mPlayerList = source.readParcelable( PlayerList.class.getClassLoader() );
        mTeamFouls = source.readInt();
        mTeamRebound = source.readInt();
        mTimeOuts = source.readInt();
        mPossessionTime = source.readInt();
    }

    public Team( UUID id, String name, PlayerList playerList, int teamFouls, int teamRebounds, int timeouts,
            int possessionTimeSec )
    {
        mID = id;
        mName = name;
        mPlayerList = playerList;
        mTeamFouls = teamFouls;
        mTeamRebound = teamRebounds;
        mTimeOuts = timeouts;
        mPossessionTime = possessionTimeSec;
    }

    public void addFoul()
    {
        mTeamFouls++;
    }

    public void addPlayer( Player player )
    {
        mPlayerList.addPlayer( player );
        setChanged();
        notifyObservers();
    }

    @Override
    public int describeContents()
    {
        return 0;
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

    public ArrayList< Player > getInGamePlayers()
    {
        return mPlayerList.getInGame();
    }

    public String getName()
    {
        return mName;
    }

    public int getOffRebounds()
    {
        return getStats( PlayerStats.OFFENSIVE_REBOUND );
    }

    public PlayerList getPlayers()
    {
        return mPlayerList;
    }

    public int getPossessionTimeSec()
    {
        return mPossessionTime;
    }

    public int getSize()
    {
        return mPlayerList.getSize();
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

    public boolean isNumberAlreadyIn( int playerNumber )
    {
        for( Player player : mPlayerList )
        {
            if( player.getNumber() == playerNumber )
            {
                return true;
            }
        }
        return false;
    }

    public void makeTeamRebound()
    {
        mTeamRebound++;
    }

    public void removePlayer( Player player )
    {
        mPlayerList.removePlayer( player );
        setChanged();
        notifyObservers();
    }

    public void setName( String name )
    {
        mName = name;
        mID = UUID.nameUUIDFromBytes( name.getBytes() );
    }

    public void setTimeOuts( int timeOuts )
    {
        mTimeOuts = timeOuts;
    }

    public void substitutePlayer( Player in, Player out )
    {
        mPlayerList.substitute( in, out );
    }

    public void updatePlayingTime()
    {
        mPlayerList.updatePlayingTime();
    }

    public void updatePossessionTime()
    {
        mPossessionTime++;
    }

    public void useTimeOut()
    {
        mTimeOuts--;
    }

    @Override
    public void writeToParcel( Parcel dest, int flags )
    {
        dest.writeString( mID.toString() );
        dest.writeString( mName );
        dest.writeParcelable( mPlayerList, flags );
        dest.writeInt( mTeamFouls );
        dest.writeInt( mTeamRebound );
        dest.writeInt( mTimeOuts );
        dest.writeInt( mPossessionTime );
    }

    private int getStats( PlayerStats stat )
    {
        int total = 0;
        Player player;
        for( int index = 0; index < mPlayerList.getSize(); index++ )
        {
            player = mPlayerList.playerAt( index );
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

    public enum TeamStats
    {
        NAME( "name" ), PLAYER_LIST( "playerList" ), TOTAL_FOULS( "totalFouls" ), TEAM_REBOUNDS(
                "teamRebound" ), TIMEOUTS( "timeOuts" ), POSSESSION_TIME( "possessionTime" ), TEAM_ID( "signature" );

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

    public boolean isStartersInvalid()
    {
        return mPlayerList.isStartersInvalid();
    }
}