package com.example.bballstatstrack.models;

import java.util.TimerTask;

import com.example.bballstatstrack.activities.GameActivity;

public class UpdateTimeTask extends TimerTask
{
    GameActivity mActivity;

    public UpdateTimeTask( GameActivity activity )
    {
        mActivity = activity;
    }

    @Override
    public void run()
    {
        mActivity.runOnUiThread( new Runnable()
        {
            @Override
            public void run()
            {
                mActivity.getGame().updateTime();
            }
        } );
    }
}
