package com.example.bballstatstrack.fragments;

import com.example.bballstatstrack.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class GameMainStatsFragment extends Fragment
{
    private TextView mHomeName;

    private TextView mAwayName;

    private TextView mHomeScore;

    private TextView mAwayScore;

    private TextView mGameClock;

    private TextView mShotClock;

    private TextView mPeriod;

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState )
    {
        View view = inflater.inflate( R.layout.fragment_game_main_stats, container, false );
        mHomeName = ( TextView ) view.findViewById( R.id.game_homeTeamName );
        mAwayName = ( TextView ) view.findViewById( R.id.game_awayTeamName );
        mHomeScore = ( TextView ) view.findViewById( R.id.game_homeTeamScore );
        mAwayScore = ( TextView ) view.findViewById( R.id.game_awayTeamScore );
        mGameClock = ( TextView ) view.findViewById( R.id.game_gameClock );
        mShotClock = ( TextView ) view.findViewById( R.id.game_shotClock );
        mPeriod = ( TextView ) view.findViewById( R.id.game_gamePeriod );
        return view;
    }

    public void initialize( String homeName, String awayName, int gameClock, int shotClock, int period )
    {
        mHomeName.setText( homeName );
        mAwayName.setText( awayName );
        setGameClock( gameClock );
        setShotClock( shotClock );
        setPeriod( period );
    }

    public void setPeriod( int period )
    {
        mPeriod.setText( getPeriodString( period ) );
    }

    public void setShotClock( int shotClock )
    {
        mShotClock.setText( getLeadZeroFormattedString( shotClock ) );
    }

    public void setGameClock( int gameClock )
    {
        mGameClock.setText( getMinSecFormattedString( gameClock ) );
    }

    public void setHomeScore( int score )
    {
        mHomeScore.setText( score );
    }

    public void setAwayScore( int score )
    {
        mAwayScore.setText( score );
    }

    private String getMinSecFormattedString( int millis )
    {
        int seconds = millis / 1000;
        int minutes = seconds % 60;
        seconds = seconds - ( minutes * 60 );
        return getLeadZeroFormattedString( minutes ) + ":" + getLeadZeroFormattedString( seconds );
    }

    private String getLeadZeroFormattedString( int number )
    {
        if( number < 10 )
        {
            return "0" + number;
        }
        return "" + number;
    }

    private String getPeriodString( int period )
    {
        switch( period )
        {
            case 1:
                return "1st";
            case 2:
                return "2nd";
            case 3:
                return "3rd";
            case 4:
                return "4th";
            case 5:
                return "OT";
            default:
                return ( period - 4 ) + " OT";
        }
    }
}
