package com.example.bballstatstrack.listeners;

import com.example.bballstatstrack.activities.GameActivity;
import com.example.bballstatstrack.models.Team;
import com.example.bballstatstrack.models.gameevents.TimeoutEvent;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

public class TimeoutEventListener implements OnClickListener
{
    private GameActivity mActivity;

    private Team mTeam;

    public TimeoutEventListener( GameActivity activity, Team team )
    {
        mActivity = activity;
        mTeam = team;
    }

    @Override
    public void onClick( DialogInterface dialog, int which )
    {
        TimeoutEvent event = new TimeoutEvent( mTeam );
        mActivity.addNewEvent( event );
    }

}
