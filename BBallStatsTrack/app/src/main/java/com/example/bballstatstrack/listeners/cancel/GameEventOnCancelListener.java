package com.example.bballstatstrack.listeners.cancel;

import com.example.bballstatstrack.activities.GameActivity;

import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;

public class GameEventOnCancelListener implements OnCancelListener
{
    private GameActivity mActivity;

    public GameEventOnCancelListener( GameActivity activity )
    {
        mActivity = activity;
    }

    @Override
    public void onCancel( DialogInterface dialog )
    {
        dialog.dismiss();
        mActivity.endEvent();
    }
}
