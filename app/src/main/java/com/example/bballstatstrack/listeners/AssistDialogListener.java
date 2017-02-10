package com.example.bballstatstrack.listeners;

import android.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.bballstatstrack.activities.GameActivity;
import com.example.bballstatstrack.dialogs.FreeThrowDialog;
import com.example.bballstatstrack.models.gameevents.AssistEvent;
import com.example.bballstatstrack.models.gameevents.ShootEvent;
import com.example.bballstatstrack.models.gameevents.foulevents.ShootingFoulEvent;

public class AssistDialogListener implements OnClickListener {
    private GameActivity mActivity;

    private AlertDialog mDialog;

    private AssistEvent mAssistEvent;

    private ShootingFoulEvent mShootingFoulEvent;

    public AssistDialogListener(GameActivity activity, AlertDialog dialog, AssistEvent assistEvent,
                                ShootingFoulEvent shootingFoulEvent) {
        mActivity = activity;
        mDialog = dialog;
        mAssistEvent = assistEvent;
        mShootingFoulEvent = shootingFoulEvent;
    }

    @Override
    public void onClick(View v) {
        ShootEvent shootEvent = (ShootEvent) v.getTag();
        shootEvent.append(mAssistEvent);
        mActivity.addNewEvent(shootEvent);
        if (mShootingFoulEvent == null) {
            mActivity.swapBallPossession();
        } else {
            AlertDialog dialog = new FreeThrowDialog(mActivity, shootEvent, mShootingFoulEvent.getFTCount());
            dialog.show();
        }
        mDialog.dismiss();
    }

}
