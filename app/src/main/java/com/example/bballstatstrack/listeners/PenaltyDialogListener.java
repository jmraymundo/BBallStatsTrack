package com.example.bballstatstrack.listeners;

import android.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.bballstatstrack.activities.GameActivity;
import com.example.bballstatstrack.dialogs.FreeThrowDialog;
import com.example.bballstatstrack.dialogs.PenaltyDialog;
import com.example.bballstatstrack.models.gameevents.ShootEvent;

public class PenaltyDialogListener implements OnClickListener {
    private GameActivity mActivity;

    private ShootEvent mShootEvent;

    private int mFTCount;

    private PenaltyDialog mDialog;

    public PenaltyDialogListener(GameActivity activity, PenaltyDialog dialog, ShootEvent shootEvent, int ftCount) {
        mActivity = activity;
        mDialog = dialog;
        mShootEvent = shootEvent;
        mFTCount = ftCount;
    }

    @Override
    public void onClick(View v) {
        mDialog.dismiss();
        AlertDialog dialog = new FreeThrowDialog(mActivity, mShootEvent, mFTCount);
        dialog.show();
    }

}
