package com.example.bballstatstrack.dialogs;

import android.widget.Button;

import com.example.bballstatstrack.R;
import com.example.bballstatstrack.activities.GameActivity;
import com.example.bballstatstrack.listeners.TurnoverTypeDialogListener;
import com.example.bballstatstrack.listeners.cancel.TurnoverTypeDialogOnCancelListener;
import com.example.bballstatstrack.models.gameevents.GameEvent.TurnoverType;
import com.example.bballstatstrack.models.gameevents.TurnoverEvent;

public class TurnoverTypeDialog extends MultipleButtonsDialog {
    public TurnoverTypeDialog(GameActivity activity, TurnoverEvent turnoverEvent) {
        super(activity, R.string.turnover_dialog_type_question);
        setOnCancelListener(new TurnoverTypeDialogOnCancelListener(activity));

        for (TurnoverType type : TurnoverType.values()) {
            Button button = new Button(activity);
            button.setTag(type);
            button.setText(type.getTextResId());
            button.setOnClickListener(
                    new TurnoverTypeDialogListener(activity, TurnoverTypeDialog.this, turnoverEvent, type));
            mDialogView.addView(button);
        }
    }
}
