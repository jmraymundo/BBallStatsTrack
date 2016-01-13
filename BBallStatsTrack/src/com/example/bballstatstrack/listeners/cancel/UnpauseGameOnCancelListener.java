package com.example.bballstatstrack.listeners.cancel;

import com.example.bballstatstrack.activities.GameActivity;

import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;

public class UnpauseGameOnCancelListener implements OnCancelListener
{
    private GameActivity mActivity;

    public UnpauseGameOnCancelListener( GameActivity activity )
    {
        mActivity = activity;
    }

    @Override
    public void onCancel( DialogInterface dialog )
    {
        dialog.dismiss();
        mActivity.timerStart();
    }
}
