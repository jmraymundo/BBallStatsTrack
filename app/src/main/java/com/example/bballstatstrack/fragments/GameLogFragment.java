package com.example.bballstatstrack.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.bballstatstrack.R;
import com.example.bballstatstrack.models.game.GameLog;

public class GameLogFragment extends Fragment {
    private GameLog mGameLog;

    private ListView mView;

    private ArrayAdapter<String> mAdapter;

    public GameLogFragment(GameLog gameLog) {
        mGameLog = gameLog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = (ListView) inflater.inflate(R.layout.fragment_game_log, container, false);
        mAdapter = new ArrayAdapter<String>(getActivity(), R.layout.adapter_gamelog_list_item,
                mGameLog.toStringList());
        mView.setAdapter(mAdapter);
        return mView;
    }

    public void updateUI() {
        mAdapter.clear();
        mAdapter.addAll(mGameLog.toStringList());
        mAdapter.notifyDataSetChanged();
    }
}
