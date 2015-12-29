package com.example.bballstatstrack.fragments;

import com.example.bballstatstrack.R;
import com.example.bballstatstrack.models.Game;

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

    public void initialize( Game game )
    {
        mHomeName.setText( game.getHomeTeam().getName() );
        mAwayName.setText( game.getAwayTeam().getName() );
        updateUI( game );
    }

    public void updateUI( Game game )
    {
        setHomeScore( game.getHomeTeam().getTotalScore() );
        setAwayScore( game.getAwayTeam().getTotalScore() );
        setGameClock( game.getCurrentGameClock() );
        setShotClock( game.getCurrentShotClock() );
        setPeriod( game.getPeriod() );
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
        mHomeScore.setText( getLeadZeroFormattedString( score ) );
    }

    public void setAwayScore( int score )
    {
        mAwayScore.setText( getLeadZeroFormattedString( score ) );
    }

    private String getMinSecFormattedString( int time )
    {
        int minutes = time / 60;
        int seconds = time - ( minutes * 60 );
        return getLeadZeroFormattedString( minutes ) + ":" + getLeadZeroFormattedString( seconds );
    }

    private String getLeadZeroFormattedString( int number )
    {
        if( number < 10 )
        {
            return "0" + number;
        }
        return String.valueOf( number );
    }

    private String getPeriodString( int period )
    {
        switch( period )
        {
            case 0:
                return "1st";
            case 1:
                return "2nd";
            case 2:
                return "3rd";
            case 3:
                return "4th";
            case 4:
                return "OT";
            default:
                return ( period - 4 ) + " OT";
        }
    }
}
