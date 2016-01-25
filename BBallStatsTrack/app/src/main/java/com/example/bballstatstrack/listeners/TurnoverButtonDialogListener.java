package com.example.bballstatstrack.listeners;

import android.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.bballstatstrack.activities.GameActivity;
import com.example.bballstatstrack.dialogs.TurnoverTypeDialog;
import com.example.bballstatstrack.models.gameevents.TurnoverEvent;

public class TurnoverButtonDialogListener implements OnClickListener {
    private GameActivity mActivity;

    private AlertDialog mDialog;

    private TurnoverEvent mTurnoverEvent;

    public TurnoverButtonDialogListener(GameActivity activity, AlertDialog dialog, TurnoverEvent turnoverEvent) {
        mActivity = activity;
        mDialog = dialog;
        mTurnoverEvent = turnoverEvent;
    }

    @Override
    public void onClick(View v) {
        mDialog.dismiss();
        AlertDialog dialog = new TurnoverTypeDialog(mActivity, mTurnoverEvent);
        dialog.show();
    }
}
