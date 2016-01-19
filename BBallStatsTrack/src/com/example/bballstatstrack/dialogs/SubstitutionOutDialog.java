package com.example.bballstatstrack.dialogs;

import com.example.bballstatstrack.R;
import com.example.bballstatstrack.activities.GameActivity;
import com.example.bballstatstrack.listeners.SubstitutionOutListener;
import com.example.bballstatstrack.listeners.cancel.SubstitutionOutDialogCancelListener;
import com.example.bballstatstrack.models.Player;
import com.example.bballstatstrack.models.Team;

import android.widget.Button;

public class SubstitutionOutDialog extends MultipleButtonsDialog
{
    public SubstitutionOutDialog( GameActivity activity, Team team )
    {
        super( activity, R.string.substitution_dialog_player_out_question );
        setOnCancelListener( new SubstitutionOutDialogCancelListener( activity ) );
        for( Player player : team.getInGamePlayers() )
        {
            Button button = new Button( activity );
            button.setText( player.toString() );
            button.setOnClickListener(
                    new SubstitutionOutListener( activity, SubstitutionOutDialog.this, team, player ) );
            mDialogView.addView( button );
        }
    }
}
