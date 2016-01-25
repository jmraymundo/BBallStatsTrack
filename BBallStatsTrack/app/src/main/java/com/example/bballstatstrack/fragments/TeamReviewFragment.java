package com.example.bballstatstrack.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bballstatstrack.R;
import com.example.bballstatstrack.models.Team;
import com.example.bballstatstrack.models.utils.StringUtils;

public class TeamReviewFragment extends Fragment {
    private Team mTeam;

    private TextView mTeamName;

    private TextView mTeamScore;

    private TextView mTeam2ptFG;

    private TextView mTeam2ptFGPercent;

    private TextView mTeam3ptFG;

    private TextView mTeam3ptFGPercent;

    private TextView mTeamFT;

    private TextView mTeamFTPercent;

    private TextView mTeamOffRebs;

    private TextView mTeamDefRebs;

    private TextView mTeamTotalRebs;

    private TextView mTeamAssists;

    private TextView mTeamSteals;

    private TextView mTeamBlocks;

    private TextView mTeamTOs;

    private TextView mTeamFouls;

    private TextView mTeamTotalBallPoss;

    public TeamReviewFragment(Team team) {
        mTeam = team;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_team_general_stats, container, false);
        mTeamName = (TextView) view.findViewById(R.id.general_stats_team_name);
        mTeamScore = (TextView) view.findViewById(R.id.general_stats_total_score);
        mTeam2ptFG = (TextView) view.findViewById(R.id.general_stats_2pt_fg);
        mTeam2ptFGPercent = (TextView) view.findViewById(R.id.general_stats_2pt_fg_percentage);
        mTeam3ptFG = (TextView) view.findViewById(R.id.general_stats_3pt_fg);
        mTeam3ptFGPercent = (TextView) view.findViewById(R.id.general_stats_3pt_fg_percentage);
        mTeamFT = (TextView) view.findViewById(R.id.general_stats_ft);
        mTeamFTPercent = (TextView) view.findViewById(R.id.general_stats_ft_percentage);
        mTeamOffRebs = (TextView) view.findViewById(R.id.general_stats_offensive_rebounds);
        mTeamDefRebs = (TextView) view.findViewById(R.id.general_stats_defensive_rebounds);
        mTeamTotalRebs = (TextView) view.findViewById(R.id.general_stats_total_rebounds);
        mTeamAssists = (TextView) view.findViewById(R.id.general_stats_assists);
        mTeamSteals = (TextView) view.findViewById(R.id.general_stats_steals);
        mTeamBlocks = (TextView) view.findViewById(R.id.general_stats_blocks);
        mTeamTOs = (TextView) view.findViewById(R.id.general_stats_turnovers);
        mTeamFouls = (TextView) view.findViewById(R.id.general_stats_team_fouls);
        mTeamTotalBallPoss = (TextView) view.findViewById(R.id.general_stats_total_ball_possession);
        setValues();
        return view;
    }

    private void setValues() {
        mTeamName.setText(mTeam.getName());
        mTeamScore.setText(String.valueOf(mTeam.getTotalScore()));
        mTeam2ptFG.setText(StringUtils.getShotFraction(mTeam.get2ptFGMade(), mTeam.get2ptFGMiss()));
        mTeam2ptFGPercent.setText(StringUtils.getShotPercentage(mTeam.get2ptFGMade(), mTeam.get2ptFGMiss()));
        mTeam3ptFG.setText(StringUtils.getShotFraction(mTeam.get3ptFGMade(), mTeam.get3ptFGMiss()));
        mTeam3ptFGPercent.setText(StringUtils.getShotPercentage(mTeam.get3ptFGMade(), mTeam.get3ptFGMiss()));
        mTeamFT.setText(StringUtils.getShotFraction(mTeam.getFTMade(), mTeam.getFTMiss()));
        mTeamFTPercent.setText(StringUtils.getShotPercentage(mTeam.getFTMade(), mTeam.getFTMiss()));
        mTeamOffRebs.setText(String.valueOf(mTeam.getOffRebounds()));
        mTeamDefRebs.setText(String.valueOf(mTeam.getDefRebounds()));
        mTeamTotalRebs.setText(String.valueOf(mTeam.getTotalRebounds()));
        mTeamAssists.setText(String.valueOf(mTeam.getAssists()));
        mTeamSteals.setText(String.valueOf(mTeam.getSteals()));
        mTeamBlocks.setText(String.valueOf(mTeam.getBlocks()));
        mTeamTOs.setText(String.valueOf(mTeam.getTurnovers()));
        mTeamFouls.setText(String.valueOf(mTeam.getTotalFouls()));
        mTeamTotalBallPoss.setText(StringUtils.getMinSecFormattedString(mTeam.getPossessionTimeSec()));
    }
}
