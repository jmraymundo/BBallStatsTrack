package com.example.bballstatstrack.fragments;

import java.util.Observable;
import java.util.Observer;

import com.example.bballstatstrack.R;
import com.example.bballstatstrack.models.Game;
import com.example.bballstatstrack.models.Team;
import com.example.bballstatstrack.models.utils.StringUtils;

import android.app.Fragment;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class GameScoreBoardFragment extends Fragment implements Observer
{
    private TextView mHomeName;

    private TextView mAwayName;

    private TextView mHomeScore;

    private TextView mAwayScore;

    private TextView mPeriodClock;

    private TextView mShotClock;

    private TextView mPeriod;

    public void initialize( Game game )
    {
        mHomeName.setText( game.getHomeTeam().getName() );
        mAwayName.setText( game.getAwayTeam().getName() );
        updateUI( game );
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState )
    {
        View view = inflater.inflate( R.layout.fragment_game_main_stats, container, false );
        mHomeName = ( TextView ) view.findViewById( R.id.game_homeTeamName );
        mAwayName = ( TextView ) view.findViewById( R.id.game_awayTeamName );
        mHomeScore = ( TextView ) view.findViewById( R.id.game_homeTeamScore );
        mAwayScore = ( TextView ) view.findViewById( R.id.game_awayTeamScore );
        mPeriodClock = ( TextView ) view.findViewById( R.id.game_periodClock );
        mShotClock = ( TextView ) view.findViewById( R.id.game_shotClock );
        mPeriod = ( TextView ) view.findViewById( R.id.game_gamePeriod );
        return view;
    }

    public void setAwayScore( int score )
    {
        mAwayScore.setText( StringUtils.getLeadZeroFormattedString( score ) );
    }

    public void setHomeScore( int score )
    {
        mHomeScore.setText( StringUtils.getLeadZeroFormattedString( score ) );
    }

    public void setPeriod( int period )
    {
        mPeriod.setText( StringUtils.getPeriodString( period ) );
    }

    public void setPeriodClock( int periodClock )
    {
        mPeriodClock.setText( StringUtils.getMinSecFormattedString( periodClock ) );
    }

    public void setShotClock( int shotClock )
    {
        mShotClock.setText( StringUtils.getLeadZeroFormattedString( shotClock ) );
    }

    @Override
    public void update( Observable observable, Object data )
    {
        updateUI( ( Game ) observable );
    }

    public void updateUI( Game game )
    {
        setBallPossession( game );
        setHomeScore( game.getHomeTeam().getTotalScore() );
        setAwayScore( game.getAwayTeam().getTotalScore() );
        setPeriodClock( game.getCurrentPeriodClock() );
        setShotClock( game.getCurrentShotClock() );
        setPeriod( game.getPeriod() );
    }

    private Drawable getResizedBasketball( int textViewHeight )
    {
        Resources resources = getResources();
        Bitmap bitmap = BitmapFactory.decodeResource( resources, R.drawable.basketball );
        Bitmap scaledBitmap = Bitmap.createScaledBitmap( bitmap, textViewHeight, textViewHeight, true );
        return new BitmapDrawable( resources, scaledBitmap );
    }

    private void setBallPossession( Game game )
    {
        Team inPossession = game.getTeamWithPossession();
        Team home = game.getHomeTeam();
        Team away = game.getAwayTeam();
        if( inPossession == null )
        {
            return;
        }
        if( inPossession.equals( home ) )
        {
            mHomeName.setCompoundDrawablesWithIntrinsicBounds( null, null,
                    getResizedBasketball( mHomeName.getHeight() ), null );
            mAwayName.setCompoundDrawablesWithIntrinsicBounds( 0, 0, 0, 0 );
        }
        else if( inPossession.equals( away ) )
        {
            mHomeName.setCompoundDrawablesWithIntrinsicBounds( 0, 0, 0, 0 );
            mAwayName.setCompoundDrawablesWithIntrinsicBounds( null, null,
                    getResizedBasketball( mAwayName.getHeight() ), null );
        }
        else
        {
            mHomeName.setCompoundDrawablesWithIntrinsicBounds( 0, 0, 0, 0 );
            mAwayName.setCompoundDrawablesWithIntrinsicBounds( 0, 0, 0, 0 );
        }
    }
}
