package com.example.bballstatstrack.listeners;

import android.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.bballstatstrack.R;
import com.example.bballstatstrack.activities.GameActivity;
import com.example.bballstatstrack.dialogs.SubstitutionButton;
import com.example.bballstatstrack.dialogs.TimeoutTeamDeciderDialog;
import com.example.bballstatstrack.models.gameevents.TimeoutEvent;

public class CoachButtonDialogListener implements OnClickListener {
    private GameActivity mActivity;

    private AlertDialog mParentDialog;

    public CoachButtonDialogListener(GameActivity activity, AlertDialog parentDialog) {
        mActivity = activity;
        mParentDialog = parentDialog;
    }

    @Override
    public void onClick(View v) {
        Button button = (Button) v;
        int buttonId = button.getId();
        switch (buttonId) {
            case R.id.time_out_button:
                if (mActivity.isTimerStopped()) {
                    showTimeoutTeamDeciderDialog();
                } else {
                    TimeoutEvent event = new TimeoutEvent(mActivity.getGame().getTeamWithPossession());
                    mActivity.addNewEvent(event);
                    mActivity.timerStop();
                }
                break;
            case R.id.substitution_button:
                showSubstitutionDialog();
                break;
        }
        mParentDialog.dismiss();
    }

    private void showSubstitutionDialog() {
        AlertDialog dialog = new SubstitutionButton(mActivity);
        dialog.show();
    }

    private void showTimeoutTeamDeciderDialog() {
        AlertDialog dialog = new TimeoutTeamDeciderDialog(mActivity);
        dialog.show();
    }

}
