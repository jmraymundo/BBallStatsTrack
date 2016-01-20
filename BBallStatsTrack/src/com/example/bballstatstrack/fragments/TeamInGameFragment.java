package com.example.bballstatstrack.fragments;

import com.example.bballstatstrack.R;
import com.example.bballstatstrack.models.Player;
import com.example.bballstatstrack.models.Team;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class TeamInGameFragment extends Fragment
{
    private Team mTeam;

    private TableLayout mTableLayout;

    public TeamInGameFragment( Team team )
    {
        mTeam = team;
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState )
    {
        View view = inflater.inflate( R.layout.fragment_team_ingame_stats, container, false );
        mTableLayout = ( TableLayout ) view.findViewById( R.id.inGameTable );
        updateUI();
        return view;
    }

    public void setInGamePlayerStats( Player player, TextView number, TextView name, TextView score )
    {
        number.setText( String.valueOf( player.getNumber() ) );
        name.setText( player.getFullName() );
        score.setText( String.valueOf( player.getTotalScore() ) );
    }

    public void updateUI()
    {
        if( mTableLayout == null )
        {
            return;
        }
        TableRow child;
        for( int i = 1; i < mTableLayout.getChildCount(); i++ )
        {
            child = ( TableRow ) mTableLayout.getChildAt( i );
            TextView number = ( TextView ) child.findViewById( R.id.game_ingame_playerNumber );
            TextView name = ( TextView ) child.findViewById( R.id.game_ingame_playerName );
            TextView score = ( TextView ) child.findViewById( R.id.game_ingame_playerScore );
            Player player = mTeam.getInGamePlayers().get( i - 1 );
            setInGamePlayerStats( player, number, name, score );
        }
    }
}
