package com.example.bballstatstrack.listeners;

import com.example.bballstatstrack.fragments.TeamFragment;
import com.example.bballstatstrack.models.Player;
import com.example.bballstatstrack.models.Team;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class DeleteButtonOnClickListener implements OnClickListener
{
    private int mRowNumber;

    private TeamFragment mFragment;

    public DeleteButtonOnClickListener( TeamFragment fragment, int rowNumber )
    {
        mFragment = fragment;
        mRowNumber = rowNumber;
    }

    @Override
    public void onClick( View v )
    {
        Team team = mFragment.getTeam();
        Player player = team.getPlayers().playerAt( mRowNumber );
        int number = player.getNumber();
        String name = player.getFullName();
        team.removePlayer( player );
        Toast.makeText( mFragment.getActivity(), "Player No. " + number + " " + name + " removed.", Toast.LENGTH_SHORT )
                .show();
        mFragment.updateTableView();
    }
}