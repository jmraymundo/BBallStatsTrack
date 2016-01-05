package com.example.bballstatstrack.fragments;

import java.util.List;

import com.example.bballstatstrack.R;
import com.example.bballstatstrack.models.game.GameLog;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GameLogFragment extends Fragment
{
    GameLog mGameLog;

    LinearLayout mView;

    public GameLogFragment( GameLog gameLog )
    {
        mGameLog = gameLog;
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState )
    {
        mView = ( LinearLayout ) inflater.inflate( R.layout.fragment_game_log, null );
        return mView;
    }

    public void updateUI()
    {
        mView.removeAllViews();
        List< String > stringLog = mGameLog.toStringList();
        for( String gameEvent : stringLog )
        {
            TextView textView = new TextView( getActivity() );
            textView.setText( gameEvent );
            mView.addView( textView );
        }
    }
}
