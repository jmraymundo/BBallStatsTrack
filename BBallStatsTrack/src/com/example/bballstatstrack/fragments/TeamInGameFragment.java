package com.example.bballstatstrack.fragments;

import java.util.ArrayList;
import java.util.List;

import com.example.bballstatstrack.R;
import com.example.bballstatstrack.models.Player;

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
    List< Player > mInGameList = new ArrayList< Player >();

    private TableLayout mTableLayout;

    public TeamInGameFragment( List< Player > inGameList )
    {
        updateInGamePlayers( inGameList );
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState )
    {
        View view = inflater.inflate( R.layout.fragment_team_ingame_stats, container, false );
        mTableLayout = ( TableLayout ) view.findViewById( R.id.inGameTable );
        updateUI();
        return view;
    }

    public void updateUI()
    {
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
            Player player = mInGameList.get( i + 1 );
            setInGamePlayerStats( player, number, name, score );
        }
    }

    public void setInGamePlayerStats( Player player, TextView number, TextView name, TextView score )
    {
        number.setText( player.getNumber() );
        name.setText( player.getFullName() );
        score.setText( player.getTotalscore() );
    }

    public void updateInGamePlayers( List< Player > inGameList )
    {
        mInGameList = inGameList;
    }
}
