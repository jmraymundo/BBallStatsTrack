package com.example.bballstatstrack.listeners;

import com.example.bballstatstrack.R;
import com.example.bballstatstrack.dialogs.NewPlayerDialog;
import com.example.bballstatstrack.fragments.TeamFragment;
import com.example.bballstatstrack.models.Player;
import com.example.bballstatstrack.models.Team;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

public class NewPlayerDialogListener implements OnClickListener
{
    private TeamFragment mFragment;

    private NewPlayerDialog mDialog;

    public NewPlayerDialogListener( TeamFragment fragment, NewPlayerDialog dialog )
    {
        mFragment = fragment;
        mDialog = dialog;
    }

    @Override
    public void onClick( View v )
    {
        EditText playerNumberText = ( EditText ) mDialog.findViewById( R.id.player_number_editText );
        EditText playerNameText = ( EditText ) mDialog.findViewById( R.id.player_name_editText );
        String playerNumberString = playerNumberText.getText().toString();
        String playerNameString = playerNameText.getText().toString();
        if( playerNumberString.isEmpty() || playerNameString.isEmpty() )
        {
            Toast.makeText( mFragment.getActivity(), mFragment.getResources().getString( R.string.player_input_error ),
                    Toast.LENGTH_SHORT ).show();
            return;
        }
        int playerNumber = Integer.parseInt( playerNumberString );
        String playerName = playerNameString;
        Team team = mFragment.getTeam();
        if( team.isNumberAlreadyIn( playerNumber ) )
        {
            Toast.makeText( mFragment.getActivity(),
                    mFragment.getResources().getString( R.string.player_number_dulpicate_error ), Toast.LENGTH_SHORT )
                    .show();
            return;
        }
        team.addPlayer( new Player( playerNumber, playerName ) );
        mDialog.dismiss();
    }

}
