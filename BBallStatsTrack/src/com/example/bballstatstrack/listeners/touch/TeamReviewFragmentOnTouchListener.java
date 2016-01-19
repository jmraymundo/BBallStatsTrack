package com.example.bballstatstrack.listeners.touch;

import com.example.bballstatstrack.activities.DetailedTeamReviewActivity;
import com.example.bballstatstrack.models.Game;
import com.example.bballstatstrack.models.Team;

import android.app.Activity;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class TeamReviewFragmentOnTouchListener implements OnTouchListener
{
    private Activity mActivity;

    private Game mGame;

    public TeamReviewFragmentOnTouchListener( Activity activity, Game game )
    {
        mActivity = activity;
        mGame = game;
    }

    @Override
    public boolean onTouch( View v, MotionEvent event )
    {
        Team team = ( Team ) v.getTag();
        Intent intent = new Intent( mActivity, DetailedTeamReviewActivity.class );
        intent.putExtra( DetailedTeamReviewActivity.TEAM_ID, team );
        mActivity.startActivity( intent );
        return false;
    }
}