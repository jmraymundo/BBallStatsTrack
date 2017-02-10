package com.example.bballstatstrack.views;

import android.widget.ImageButton;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.bballstatstrack.buttons.DeletePlayerButton;
import com.example.bballstatstrack.edittext.PlayerTableRowEditText;
import com.example.bballstatstrack.fragments.TeamFragment;
import com.example.bballstatstrack.models.Player;

public class PlayerTableRow extends TableRow {
    public PlayerTableRow(TeamFragment fragment, int rowNumber) {
        super(fragment.getActivity());
        Player player = fragment.getTeam().getPlayers().playerAt(rowNumber);
        setId(rowNumber);
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        setWeightSum(16);
        TextView leftEditText = new PlayerTableRowEditText(fragment.getActivity(), 1,
                String.valueOf(player.getNumber()));
        TextView rightEditText = new PlayerTableRowEditText(fragment.getActivity(), 10, player.getFullName());
        ImageButton deleteButton = new DeletePlayerButton(fragment, rowNumber);
        addView(leftEditText);
        addView(rightEditText);
        addView(deleteButton);
    }

}
