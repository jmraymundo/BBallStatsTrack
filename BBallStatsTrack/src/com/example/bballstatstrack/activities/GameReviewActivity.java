package com.example.bballstatstrack.activities;

import java.util.ArrayList;
import java.util.UUID;

import com.example.bballstatstrack.R;
import com.example.bballstatstrack.fragments.TeamReviewFragment;
import com.example.bballstatstrack.listeners.touch.TeamReviewFragmentOnTouchListener;
import com.example.bballstatstrack.models.Game;
import com.example.bballstatstrack.models.GameDirectory;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;

public class GameReviewActivity extends Activity
{
    private Game mGame;

    private TeamReviewFragment mHomeFragment;

    private TeamReviewFragment mAwayFragment;

    public Game getGame()
    {
        ArrayList< Game > games = GameDirectory.get( GameReviewActivity.this ).getGames();
        String id = getIntent().getStringExtra( GameActivity.EXTRA_GAME_ID );
        UUID uuid = UUID.fromString( id );
        for( Game game : games )
        {
            if( game.getId().equals( uuid ) )
            {
                return game;
            }
        }
        return null;
    }

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_game_review );
        mGame = getGame();
        mHomeFragment = new TeamReviewFragment( mGame.getHomeTeam() );
        mAwayFragment = new TeamReviewFragment( mGame.getAwayTeam() );
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace( R.id.game_review_home_container, mHomeFragment );
        transaction.replace( R.id.game_review_away_container, mAwayFragment );
        transaction.commit();
        View homeView = findViewById( R.id.game_review_home_container );
        homeView.setTag( mGame.getHomeTeam() );
        TeamReviewFragmentOnTouchListener listener = new TeamReviewFragmentOnTouchListener( GameReviewActivity.this );
        homeView.setOnTouchListener( listener );
        View awayView = findViewById( R.id.game_review_away_container );
        awayView.setTag( mGame.getAwayTeam() );
        awayView.setOnTouchListener( listener );
    }
}
