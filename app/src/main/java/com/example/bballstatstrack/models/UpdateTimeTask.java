package com.example.bballstatstrack.models;

import com.example.bballstatstrack.activities.GameActivity;

import java.util.TimerTask;

public class UpdateTimeTask extends TimerTask {
    GameActivity mActivity;

    public UpdateTimeTask(GameActivity activity) {
        mActivity = activity;
    }

    @Override
    public void run() {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mActivity.getGame().updateTime();
            }
        });
    }
}
