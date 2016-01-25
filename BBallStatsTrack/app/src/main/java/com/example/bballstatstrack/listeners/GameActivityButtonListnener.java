package com.example.bballstatstrack.listeners;

import android.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.bballstatstrack.R;
import com.example.bballstatstrack.activities.GameActivity;
import com.example.bballstatstrack.dialogs.CoachButtonDialog;
import com.example.bballstatstrack.dialogs.FoulButtonDialog;
import com.example.bballstatstrack.dialogs.ShootButtonDialog;
import com.example.bballstatstrack.dialogs.TimeButtonDialog;
import com.example.bballstatstrack.dialogs.TurnoverButtonDialog;

public class GameActivityButtonListnener implements OnClickListener {
    private GameActivity mActivity;

    public GameActivityButtonListnener(GameActivity activity) {
        mActivity = activity;
    }

    @Override
    public void onClick(View v) {
        AlertDialog dialog;
        switch (v.getId()) {
            case R.id.timeRelatedButton:
                dialog = new TimeButtonDialog(mActivity);
                break;
            case R.id.coachCommandsButton:
                dialog = new CoachButtonDialog(mActivity);
                break;
            case R.id.foulButton:
                dialog = new FoulButtonDialog(mActivity);
                break;
            case R.id.turnoverButton:
                dialog = new TurnoverButtonDialog(mActivity);
                break;
            case R.id.shootRelatedButton:
                dialog = new ShootButtonDialog(mActivity);
                break;
            default:
                dialog = null;
        }
        if (dialog != null) {
            dialog.show();
        }
    }
}
