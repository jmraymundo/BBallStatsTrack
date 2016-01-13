package com.example.bballstatstrack.fragments;

import java.util.Observable;
import java.util.Observer;

import com.example.bballstatstrack.R;
import com.example.bballstatstrack.models.Player;
import com.example.bballstatstrack.models.PlayerList;
import com.example.bballstatstrack.models.Team;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class TeamInGameFragment extends Fragment implements Observer
{
    private PlayerList mInGameList = new PlayerList();

    private TableLayout mTableLayout;

    public TeamInGameFragment( Team team )
    {
        updateInGamePlayers( team.getInGamePlayers() );
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
        score.setText( String.valueOf( player.getTotalscore() ) );
    }

    @Override
    public void update( Observable observable, Object data )
    {
        updateUI();
    }

    public void updateInGamePlayers( PlayerList inGameList )
    {
        mInGameList = inGameList;
    }

    public void updateUI()
    {
        if( mTableLayout == null )
        {
            return;
        }
        TableRow child;
        for( int i = 0; i < mTableLayout.getChildCount(); i++ )
        {
            child = ( TableRow ) mTableLayout.getChildAt( i );
            if( child.getId() == R.id.inGameHeader )
            {
                continue;
            }
            TextView number = ( TextView ) child.getChildAt( 0 );
            TextView name = ( TextView ) child.getChildAt( 1 );
            TextView score = ( TextView ) child.getChildAt( 2 );
            Player player = mInGameList.playerAt( i - 1 );
            setInGamePlayerStats( player, number, name, score );
        }
    }
}
