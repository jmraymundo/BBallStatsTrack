package com.example.bballstatstrack.dialogs;

import android.widget.Button;

import com.example.bballstatstrack.R;
import com.example.bballstatstrack.activities.GameActivity;
import com.example.bballstatstrack.listeners.AssistDialogListener;
import com.example.bballstatstrack.listeners.cancel.AssistDialogOnCancelListener;
import com.example.bballstatstrack.models.Player;
import com.example.bballstatstrack.models.Team;
import com.example.bballstatstrack.models.gameevents.AssistEvent;
import com.example.bballstatstrack.models.gameevents.ShootEvent;
import com.example.bballstatstrack.models.gameevents.foulevents.ShootingFoulEvent;

public class AssistDialog extends MultipleButtonsDialog {
    public AssistDialog(GameActivity activity, ShootEvent shootEvent, ShootingFoulEvent shootingFoulEvent) {
        super(activity, R.string.assist_dialog_player_question);
        setOnCancelListener(new AssistDialogOnCancelListener(activity, shootEvent, shootingFoulEvent));
        Team team = activity.getGame().getTeamWithPossession();
        for (Player player : team.getInGamePlayers()) {
            if (player.equals(shootEvent.getPlayer())) {
                continue;
            }
            AssistEvent assistEvent = new AssistEvent(player, team);

            Button button = new Button(activity);
            button.setText(player.toString());
            button.setTag(shootEvent);
            button.setOnClickListener(
                    new AssistDialogListener(activity, AssistDialog.this, assistEvent, shootingFoulEvent));
            mDialogView.addView(button);
        }
    }
}
