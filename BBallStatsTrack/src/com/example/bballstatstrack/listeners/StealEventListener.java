package com.example.bballstatstrack.listeners;

import com.example.bballstatstrack.activities.GameActivity;
import com.example.bballstatstrack.models.gameevents.StealEvent;
import com.example.bballstatstrack.models.gameevents.TurnoverEvent;

import android.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;

public class StealEventListener implements OnClickListener
{
    private GameActivity mActivity;

    private AlertDialog mDialog;

    private StealEvent mStealEvent;

    public StealEventListener( GameActivity activity, AlertDialog dialog, StealEvent stealEvent )
    {
        mActivity = activity;
        mDialog = dialog;
        mStealEvent = stealEvent;
    }

    @Override
    public void onClick( View v )
    {
        mDialog.dismiss();
        TurnoverEvent turnoverEvent = ( TurnoverEvent ) v.getTag();
        turnoverEvent.append( mStealEvent );
        mActivity.addNewEvent( turnoverEvent );
        mActivity.swapBallPossession();
    }

}
