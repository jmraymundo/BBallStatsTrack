package com.example.bballstatstrack.dialogs;

import android.app.AlertDialog;
import android.view.View;
import android.widget.Button;

import com.example.bballstatstrack.R;
import com.example.bballstatstrack.activities.GameActivity;
import com.example.bballstatstrack.listeners.TimeButtonDialogListener;
import com.example.bballstatstrack.models.Game;

public class TimeButtonDialog extends AlertDialog {
    public TimeButtonDialog(GameActivity activity) {
        super(activity);
        View view = View.inflate(getContext(), R.layout.dialog_time_button, null);
        setView(view);
        Game game = activity.getGame();

        TimeButtonDialogListener listener = new TimeButtonDialogListener(TimeButtonDialog.this, game, activity);

        Button saveGameButton = (Button) view.findViewById(R.id.save_game_button);
        Button nextPeriodButton = (Button) view.findViewById(R.id.next_period_button);
        Button resetPeriodClockButton = (Button) view.findViewById(R.id.reset_period_clock_button);
        Button resetShotClockButton = (Button) view.findViewById(R.id.reset_shot_clock_button);
        Button pauseResumeButton = (Button) view.findViewById(R.id.pauseResume_time_button);

        saveGameButton.setOnClickListener(listener);
        nextPeriodButton.setOnClickListener(listener);
        resetPeriodClockButton.setOnClickListener(listener);
        resetShotClockButton.setOnClickListener(listener);
        pauseResumeButton.setOnClickListener(listener);

        boolean isPeriodOngoing = game.isPeriodOngoing();
        saveGameButton.setEnabled(!isPeriodOngoing);
        nextPeriodButton.setEnabled(!isPeriodOngoing);

        boolean timerStopped = activity.isTimerStopped();
        resetPeriodClockButton.setEnabled(timerStopped);
        resetShotClockButton.setEnabled(timerStopped);

        pauseResumeButton.setText(timerStopped ? R.string.time_dialog_resume_text : R.string.time_dialog_pause_text);
        pauseResumeButton.setEnabled(isPeriodOngoing && game.isPossessionOngoing());
    }
}
