package com.example.bballstatstrack.builders;

import com.example.bballstatstrack.R;
import com.example.bballstatstrack.activities.GameActivity;
import com.example.bballstatstrack.listeners.DismissDialogListener;
import com.example.bballstatstrack.listeners.multichoice.AddStartersMultiChoiceClickListener;
import com.example.bballstatstrack.models.PlayerList;
import com.example.bballstatstrack.models.Team;

import android.app.AlertDialog.Builder;

public class StartingFiveDialogBuilder extends Builder
{
    protected GameActivity mActivity;

    protected Team mTeam;

    protected PlayerList mSelectedPlayers;

    public StartingFiveDialogBuilder( GameActivity activity, Team team )
    {
        super( activity );
        mActivity = activity;
        mTeam = team;
        String title = getContext().getResources().getString( R.string.select_starters ) + " ";
        setTitle( title.concat( team.getName() ) );
        final PlayerList players = team.getPlayers();
        int size = players.getSize();
        String[] playersArray = new String[size];
        for( int i = 0; i < size; i++ )
        {
            playersArray[i] = players.playerAt( i ).toString();
        }
        mSelectedPlayers = new PlayerList();
        setMultiChoiceItems( playersArray, null, new AddStartersMultiChoiceClickListener( mSelectedPlayers, players ) );
        setPositiveButton( getContext().getResources().getString( R.string.proceed ), new DismissDialogListener() );
        setCancelable( false );
    }
}
