package com.example.bballstatstrack.listeners;

import android.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.bballstatstrack.activities.GameActivity;
import com.example.bballstatstrack.dialogs.StealDialog;
import com.example.bballstatstrack.models.gameevents.GameEvent.TurnoverType;
import com.example.bballstatstrack.models.gameevents.TurnoverEvent;
import com.example.bballstatstrack.models.gameevents.foulevents.OffensiveFoulEvent;

public class TurnoverTypeDialogListener implements OnClickListener {
    private GameActivity mActivity;

    private AlertDialog mDialog;

    private TurnoverEvent mTurnoverEvent;

    private TurnoverType mTurnoverType;

    public TurnoverTypeDialogListener(GameActivity activity, AlertDialog dialog, TurnoverEvent turnoverEvent,
                                      TurnoverType type) {
        mActivity = activity;
        mDialog = dialog;
        mTurnoverEvent = turnoverEvent;
        mTurnoverType = type;
    }

    @Override
    public void onClick(View v) {
        mDialog.dismiss();
        mTurnoverEvent.setTurnoverType(mTurnoverType);
        switch (mTurnoverType) {
            case STEAL:
                AlertDialog dialog = new StealDialog(mActivity, mTurnoverEvent);
                dialog.show();
                return;
            case OFFENSIVE_FOUL:
                mTurnoverEvent.append(new OffensiveFoulEvent(mTurnoverEvent.getPlayer(), mTurnoverEvent.getTeam()));
            default:
                mActivity.addNewEvent(mTurnoverEvent);
                mActivity.swapBallPossession();
                mActivity.timerStop();
                return;
        }
    }

}
