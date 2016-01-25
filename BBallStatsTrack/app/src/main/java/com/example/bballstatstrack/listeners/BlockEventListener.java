package com.example.bballstatstrack.listeners;

import android.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.bballstatstrack.activities.GameActivity;
import com.example.bballstatstrack.dialogs.ReboundDialog;
import com.example.bballstatstrack.models.gameevents.BlockEvent;
import com.example.bballstatstrack.models.gameevents.ShootEvent;

public class BlockEventListener implements OnClickListener {
    private GameActivity mActivity;

    private AlertDialog mDialog;

    private BlockEvent mBlockEvent;

    public BlockEventListener(GameActivity activity, AlertDialog dialog, BlockEvent blockEvent) {
        mActivity = activity;
        mDialog = dialog;
        mBlockEvent = blockEvent;
    }

    @Override
    public void onClick(View v) {
        mDialog.dismiss();
        ShootEvent shootEvent = (ShootEvent) v.getTag();
        shootEvent.append(mBlockEvent);
        AlertDialog dialog = new ReboundDialog(mActivity, shootEvent);
        dialog.show();
    }

}
