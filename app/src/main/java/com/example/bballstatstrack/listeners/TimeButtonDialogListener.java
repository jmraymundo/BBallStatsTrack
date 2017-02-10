package com.example.bballstatstrack.listeners;

import android.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.bballstatstrack.R;
import com.example.bballstatstrack.activities.GameActivity;
import com.example.bballstatstrack.dialogs.BallPossessionDeciderDialog;
import com.example.bballstatstrack.dialogs.ResetPeriodClockDialog;
import com.example.bballstatstrack.dialogs.ResetShotClockDialog;
import com.example.bballstatstrack.dialogs.SaveGameDialog;
import com.example.bballstatstrack.models.Game;

public class TimeButtonDialogListener implements OnClickListener {
    private Game mGame;

    private AlertDialog mDialog;

    private GameActivity mActivity;

    public TimeButtonDialogListener(AlertDialog dialog, Game game, GameActivity activity) {
        mDialog = dialog;
        mGame = game;
        mActivity = activity;
    }

    @Override
    public void onClick(View v) {
        Button button = (Button) v;
        int buttonId = button.getId();
        switch (buttonId) {
            case R.id.save_game_button:
                showSaveGameDialog();
                break;
            case R.id.next_period_button:
                goToNextPeriod();
                break;
            case R.id.reset_period_clock_button:
                showResetPeriodClockDialog();
                break;
            case R.id.reset_shot_clock_button:
                showResetShotClockDialog();
                break;
            case R.id.pauseResume_time_button:
                pauseUnpauseGame();
                break;
        }
        mDialog.dismiss();
    }

    private void goToNextPeriod() {
        AlertDialog dialog = new BallPossessionDeciderDialog(mActivity);
        dialog.show();
        mActivity.nextPeriod();
    }

    private void pauseUnpauseGame() {
        if (mActivity.isTimerStopped()) {
            mActivity.timerStart();
        } else {
            mActivity.timerStop();
        }
    }

    private void showResetPeriodClockDialog() {
        AlertDialog dialog = new ResetPeriodClockDialog(mActivity);
        dialog.show();
    }

    private void showResetShotClockDialog() {
        AlertDialog dialog = new ResetShotClockDialog(mActivity);
        dialog.show();
    }

    private void showSaveGameDialog() {
        AlertDialog dialog = new SaveGameDialog(mActivity);
        dialog.show();
    }

}
