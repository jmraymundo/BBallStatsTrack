package com.example.bballstatstrack.dialogs;

import android.widget.Button;

import com.example.bballstatstrack.R;
import com.example.bballstatstrack.activities.GameActivity;
import com.example.bballstatstrack.listeners.StealEventListener;
import com.example.bballstatstrack.listeners.cancel.StealDialogOnCancelListener;
import com.example.bballstatstrack.models.Player;
import com.example.bballstatstrack.models.Team;
import com.example.bballstatstrack.models.gameevents.StealEvent;
import com.example.bballstatstrack.models.gameevents.TurnoverEvent;

public class StealDialog extends MultipleButtonsDialog {
    public StealDialog(GameActivity activity, TurnoverEvent turnoverEvent) {
        super(activity, R.string.steal_dialog_player_question);
        setOnCancelListener(new StealDialogOnCancelListener(activity, turnoverEvent));
        Team team = activity.getGame().getTeamWithoutPossession();
        for (Player player : team.getInGamePlayers()) {
            StealEvent stealEvent = new StealEvent(player, team);
            Button button = new Button(activity);
            button.setText(player.toString());
            button.setTag(turnoverEvent);
            button.setOnClickListener(new StealEventListener(activity, StealDialog.this, stealEvent));
            mDialogView.addView(button);
        }
    }
}
