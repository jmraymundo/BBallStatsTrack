package com.example.bballstatstrack.dialogs;

import android.widget.Button;

import com.example.bballstatstrack.R;
import com.example.bballstatstrack.activities.GameActivity;
import com.example.bballstatstrack.listeners.BlockEventListener;
import com.example.bballstatstrack.listeners.cancel.BlockDialogOnCancelListener;
import com.example.bballstatstrack.models.Player;
import com.example.bballstatstrack.models.Team;
import com.example.bballstatstrack.models.gameevents.BlockEvent;
import com.example.bballstatstrack.models.gameevents.ShootEvent;

public class BlockDialog extends MultipleButtonsDialog {
    public BlockDialog(GameActivity activity, ShootEvent shootEvent) {
        super(activity, R.string.block_dialog_player_question);
        setOnCancelListener(new BlockDialogOnCancelListener(activity, shootEvent));
        Team team = activity.getGame().getTeamWithoutPossession();
        for (Player player : team.getInGamePlayers()) {
            Button button = new Button(activity);
            button.setText(player.toString());
            button.setTag(shootEvent);
            BlockEvent blockEvent = new BlockEvent(player, team);
            button.setOnClickListener(new BlockEventListener(activity, BlockDialog.this, blockEvent));
            mDialogView.addView(button);
        }
    }
}
