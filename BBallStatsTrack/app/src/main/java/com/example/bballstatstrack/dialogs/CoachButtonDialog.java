package com.example.bballstatstrack.dialogs;

import android.app.AlertDialog;
import android.view.View;
import android.widget.Button;

import com.example.bballstatstrack.R;
import com.example.bballstatstrack.activities.GameActivity;
import com.example.bballstatstrack.listeners.CoachButtonDialogListener;
import com.example.bballstatstrack.listeners.cancel.GameEventOnCancelListener;

public class CoachButtonDialog extends AlertDialog {
    public CoachButtonDialog(GameActivity activity) {
        super(activity);
        View view = View.inflate(getContext(), R.layout.dialog_coach_button, null);
        setView(view);
        setOnCancelListener(new GameEventOnCancelListener(activity));

        Button timeOutButton = (Button) view.findViewById(R.id.time_out_button);
        Button substitutionButton = (Button) view.findViewById(R.id.substitution_button);

        CoachButtonDialogListener listener = new CoachButtonDialogListener(activity, CoachButtonDialog.this);
        timeOutButton.setOnClickListener(listener);
        substitutionButton.setOnClickListener(listener);
        substitutionButton.setEnabled(activity.isTimerStopped());

        activity.startEvent();
    }
}
