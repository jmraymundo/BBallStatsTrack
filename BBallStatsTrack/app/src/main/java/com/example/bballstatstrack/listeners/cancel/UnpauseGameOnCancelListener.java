package com.example.bballstatstrack.listeners.cancel;

import com.example.bballstatstrack.activities.GameActivity;

import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;

public class UnpauseGameOnCancelListener implements OnCancelListener
{
    private GameActivity mActivity;

    private boolean mAlreadyPaused;

    public UnpauseGameOnCancelListener( GameActivity activity, boolean isAlreadyPaused )
    {
        mActivity = activity;
        mAlreadyPaused = isAlreadyPaused;
    }

    @Override
    public void onCancel( DialogInterface dialog )
    {
        dialog.dismiss();
        if( mAlreadyPaused )
        {
            return;
        }
        mActivity.timerStart();
    }
}
