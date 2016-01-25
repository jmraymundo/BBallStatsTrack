package com.example.bballstatstrack.dialogs;

import android.widget.Button;

import com.example.bballstatstrack.R;
import com.example.bballstatstrack.activities.GameActivity;
import com.example.bballstatstrack.listeners.PenaltyDialogListener;
import com.example.bballstatstrack.listeners.cancel.GameEventOnCancelListener;
import com.example.bballstatstrack.models.Player;
import com.example.bballstatstrack.models.Team;
import com.example.bballstatstrack.models.gameevents.ShootEvent;

public class PenaltyDialog extends MultipleButtonsDialog {
    public PenaltyDialog(GameActivity activity) {
        super(activity, R.string.free_throw_dialog_penalty);
        setOnCancelListener(new GameEventOnCancelListener(activity));
        Team team = activity.getGame().getTeamWithPossession();
        for (Player player : team.getInGamePlayers()) {
            ShootEvent shootEvent = new ShootEvent(player, team);
            Button button = new Button(getContext());
            button.setText(player.toString());
            button.setOnClickListener(new PenaltyDialogListener(activity, PenaltyDialog.this, shootEvent, 2));
            mDialogView.addView(button);
        }
    }
}
