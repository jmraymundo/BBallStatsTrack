package com.example.bballstatstrack.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.bballstatstrack.R;
import com.example.bballstatstrack.listeners.AddPlayerButtonOnClickListener;
import com.example.bballstatstrack.models.Team;
import com.example.bballstatstrack.models.utils.TeamNameWatcher;
import com.example.bballstatstrack.models.utils.ViewUtils;
import com.example.bballstatstrack.views.PlayerTableRow;

import java.util.Observable;
import java.util.Observer;

public class TeamFragment extends Fragment implements Observer {
    private EditText mTeamNameEditText;

    private Team mTeam = new Team();

    private TableLayout mPlayerTable;

    private Button mAddPlayerButton;

    private int mGameSideStringResId;

    private TextView mGameSide;

    public TeamFragment(int gameSideStringResId) {
        super();
        mGameSideStringResId = gameSideStringResId;
        mTeam.addObserver(TeamFragment.this);
    }

    public Team getTeam() {
        mTeam.setName(getTeamName());
        return mTeam;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_team, container, false);
        if (mTeam == null) {
            mTeam = new Team();
        }
        if (view == null) {
            view = super.onCreateView(inflater, container, savedInstanceState);
        }
        setupView(view);
        return view;
    }

    public void setupView(View view) {
        mGameSide = (TextView) view.findViewById(R.id.team_location_text);
        mGameSide.setText(mGameSideStringResId);
        mPlayerTable = (TableLayout) view.findViewById(R.id.playerListTable);
        if (mTeam.getSize() > 0) {
            updateTableView();
        }
        mTeamNameEditText = (EditText) view.findViewById(R.id.team_name_editText);
        mTeamNameEditText.setMaxLines(1);
        Activity activity = getActivity();
        mTeamNameEditText.addTextChangedListener(new TeamNameWatcher(activity, mTeam));
        mAddPlayerButton = (Button) view.findViewById(R.id.addNewPlayerButton);
        mAddPlayerButton.setOnClickListener(new AddPlayerButtonOnClickListener(TeamFragment.this));
        ViewUtils.setupKeyboardHiding(activity, view);
    }

    @Override
    public void update(Observable observable, Object data) {
        updateTableView();
    }

    public void updateTableView() {
        mPlayerTable.removeViews(1, mPlayerTable.getChildCount() - 1);
        for (int index = 0; index < mTeam.getSize(); index++) {
            TableRow row = new PlayerTableRow(TeamFragment.this, index);
            mPlayerTable.addView(row, index + 1);
        }
    }

    private String getTeamName() {
        if (mTeamNameEditText == null) {
            return new String();
        }
        return mTeamNameEditText.getText().toString();
    }
}
