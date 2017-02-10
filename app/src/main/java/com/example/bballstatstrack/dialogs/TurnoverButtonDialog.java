package com.example.bballstatstrack.dialogs;

import android.widget.Button;

import com.example.bballstatstrack.R;
import com.example.bballstatstrack.activities.GameActivity;
import com.example.bballstatstrack.listeners.TurnoverButtonDialogListener;
import com.example.bballstatstrack.listeners.cancel.GameEventOnCancelListener;
import com.example.bballstatstrack.models.Player;
import com.example.bballstatstrack.models.Team;
import com.example.bballstatstrack.models.gameevents.TurnoverEvent;

public class TurnoverButtonDialog extends MultipleButtonsDialog {
    public TurnoverButtonDialog(GameActivity activity) {
        super(activity, R.string.turnover_dialog_player_question);
        setOnCancelListener(new GameEventOnCancelListener(activity));
        Team team = activity.getGame().getTeamWithPossession();
        for (Player player : team.getInGamePlayers()) {
            Button button = new Button(activity);
            button.setText(player.toString());
            TurnoverEvent turnoverEvent = new TurnoverEvent(player, team);
            button.setOnClickListener(
                    new TurnoverButtonDialogListener(activity, TurnoverButtonDialog.this, turnoverEvent));
            mDialogView.addView(button);
        }

        activity.startEvent();
    }
}
