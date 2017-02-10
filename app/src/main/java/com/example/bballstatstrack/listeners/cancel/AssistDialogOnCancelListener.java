package com.example.bballstatstrack.listeners.cancel;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;

import com.example.bballstatstrack.activities.GameActivity;
import com.example.bballstatstrack.dialogs.FreeThrowDialog;
import com.example.bballstatstrack.models.gameevents.ShootEvent;
import com.example.bballstatstrack.models.gameevents.foulevents.ShootingFoulEvent;

public class AssistDialogOnCancelListener implements OnCancelListener {
    private GameActivity mActivity;

    private ShootingFoulEvent mShootingFoulEvent;

    private ShootEvent mShootEvent;

    public AssistDialogOnCancelListener(GameActivity activity, ShootEvent shootEvent,
                                        ShootingFoulEvent shootingFoulEvent) {
        mActivity = activity;
        mShootEvent = shootEvent;
        mShootingFoulEvent = shootingFoulEvent;
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        if (mShootingFoulEvent != null) {
            AlertDialog returnDialog = new FreeThrowDialog(mActivity, mShootEvent, mShootingFoulEvent.getFTCount());
            returnDialog.show();
        }
        dialog.dismiss();
    }
}