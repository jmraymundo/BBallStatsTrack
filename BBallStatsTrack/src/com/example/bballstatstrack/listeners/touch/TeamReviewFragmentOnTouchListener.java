package com.example.bballstatstrack.listeners.touch;

import com.example.bballstatstrack.activities.DetailedTeamReviewActivity;
import com.example.bballstatstrack.models.Team;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class TeamReviewFragmentOnTouchListener implements OnTouchListener
{
    private Activity mActivity;

    private Rect mRect = null;

    public TeamReviewFragmentOnTouchListener( Activity activity )
    {
        mActivity = activity;
    }

    @Override
    public boolean onTouch( View v, MotionEvent event )
    {
        if( event.getAction() == MotionEvent.ACTION_DOWN )
        {
            mRect = new Rect( v.getLeft(), v.getTop(), v.getRight(), v.getBottom() );
        }
        if( event.getAction() == MotionEvent.ACTION_UP )
        {
            Team team = ( Team ) v.getTag();
            Intent intent = new Intent( mActivity, DetailedTeamReviewActivity.class );
            intent.putExtra( DetailedTeamReviewActivity.TEAM_ID, team );
            mActivity.startActivity( intent );
        }
        return true;
    }
}