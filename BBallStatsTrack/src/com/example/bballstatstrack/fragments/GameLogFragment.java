package com.example.bballstatstrack.fragments;

import com.example.bballstatstrack.R;
import com.example.bballstatstrack.models.game.GameLog;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class GameLogFragment extends Fragment
{
    private GameLog mGameLog;

    private ListView mView;

    private ArrayAdapter< String > myAdapter;

    public GameLogFragment( GameLog gameLog )
    {
        mGameLog = gameLog;
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState )
    {
        mView = ( ListView ) inflater.inflate( R.layout.fragment_game_log, null );
        myAdapter = new ArrayAdapter< String >( getActivity(), R.layout.adapter_gamelog_list_item,
                mGameLog.toStringList() );
        mView.setAdapter( myAdapter );
        return mView;
    }

    public void updateUI()
    {
        myAdapter.clear();
        myAdapter.addAll( mGameLog.toStringList() );
        myAdapter.notifyDataSetChanged();
    }
}
