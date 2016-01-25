package com.example.bballstatstrack.dialogs;

import android.widget.Button;

import com.example.bballstatstrack.R;
import com.example.bballstatstrack.activities.GameActivity;
import com.example.bballstatstrack.listeners.ShootButtonDialogListener;
import com.example.bballstatstrack.listeners.cancel.GameEventOnCancelListener;
import com.example.bballstatstrack.models.Player;
import com.example.bballstatstrack.models.Team;
import com.example.bballstatstrack.models.gameevents.ShootEvent;
import com.example.bballstatstrack.models.gameevents.foulevents.ShootingFoulEvent;

public class ShootButtonDialog extends MultipleButtonsDialog {
    public ShootButtonDialog(GameActivity activity) {
        this(activity, null);
    }

    public ShootButtonDialog(GameActivity activity, ShootingFoulEvent shootingFoulEvent) {
        super(activity, R.string.shoot_dialog_player_question);
        setOnCancelListener(new GameEventOnCancelListener(activity));

        Team team = activity.getGame().getTeamWithPossession();
        for (Player player : team.getInGamePlayers()) {
            ShootEvent shootEvent = new ShootEvent(player, team);
            Button button = new Button(getContext());
            button.setText(player.toString());
            button.setOnClickListener(
                    new ShootButtonDialogListener(activity, ShootButtonDialog.this, shootEvent, shootingFoulEvent));
            mDialogView.addView(button);
        }
    }
}
