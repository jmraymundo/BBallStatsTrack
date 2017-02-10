package com.example.bballstatstrack.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bballstatstrack.R;
import com.example.bballstatstrack.models.Player;
import com.example.bballstatstrack.models.PlayerList;
import com.example.bballstatstrack.models.Team;
import com.example.bballstatstrack.models.utils.StringUtils;

public class DetailedTeamReviewActivity extends Activity {
    public static final String TEAM_ID = "TeamID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_team_review);
        setStats(getTeam());
    }

    private void setStats(Team team) {
        TextView teamName = (TextView) findViewById(R.id.detailed_team_review_teamName);
        teamName.setText("Team: " + team.getName());
        PlayerList players = team.getPlayers();
        LinearLayout container = (LinearLayout) findViewById(R.id.detailed_team_review_teamStats_container);
        for (Player player : players) {
            container.addView(createPlayerStatsView(player, container));
        }
    }

    private LinearLayout createPlayerStatsView(Player player, ViewGroup root) {
        LayoutInflater inflater = getLayoutInflater();
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.teamreview_playercontainer, root, false);
        TextView playerNumber = (TextView) inflater.inflate(R.layout.teamreview_defaultcell, layout, false);
        TextView playerName = (TextView) inflater.inflate(R.layout.teamreview_playernamecell, layout, false);
        playerNumber.setText(String.valueOf(player.getNumber()));
        playerName.setText(player.getFullName());
        int playerPlayingTime = player.getPlayingTimeSec();
        layout.addView(playerNumber);
        layout.addView(playerName);

        if (playerPlayingTime == 0) {
            TextView didNotPlay = (TextView) inflater.inflate(R.layout.teamreview_didnotplay, layout, false);
            layout.addView(didNotPlay);
            return layout;
        }

        TextView playerTime = (TextView) inflater.inflate(R.layout.teamreview_playertimecell, layout, false);
        TextView player2ptFG = (TextView) inflater.inflate(R.layout.teamreview_defaultcell, layout, false);
        TextView player3ptFG = (TextView) inflater.inflate(R.layout.teamreview_defaultcell, layout, false);
        TextView playerFT = (TextView) inflater.inflate(R.layout.teamreview_defaultcell, layout, false);
        TextView playerDefReb = (TextView) inflater.inflate(R.layout.teamreview_defaultcell, layout, false);
        TextView playerOffReb = (TextView) inflater.inflate(R.layout.teamreview_defaultcell, layout, false);
        TextView playerTotalReb = (TextView) inflater.inflate(R.layout.teamreview_defaultcell, layout, false);
        TextView playerAst = (TextView) inflater.inflate(R.layout.teamreview_defaultcell, layout, false);
        TextView playerTO = (TextView) inflater.inflate(R.layout.teamreview_defaultcell, layout, false);
        TextView playerStl = (TextView) inflater.inflate(R.layout.teamreview_defaultcell, layout, false);
        TextView playerBlk = (TextView) inflater.inflate(R.layout.teamreview_defaultcell, layout, false);
        TextView playerFoul = (TextView) inflater.inflate(R.layout.teamreview_defaultcell, layout, false);
        TextView playerPts = (TextView) inflater.inflate(R.layout.teamreview_defaultcell, layout, false);
        playerTime.setText(StringUtils.getMinSecFormattedString(playerPlayingTime));
        player2ptFG.setText(StringUtils.getShotFraction(player.get2ptFGMade(), player.get2ptFGMiss()));
        player3ptFG.setText(StringUtils.getShotFraction(player.get3ptFGMade(), player.get3ptFGMiss()));
        playerFT.setText(StringUtils.getShotFraction(player.getFTMade(), player.getFTMiss()));
        playerDefReb.setText(String.valueOf(player.getDefRebound()));
        playerOffReb.setText(String.valueOf(player.getOffRebound()));
        playerTotalReb.setText(String.valueOf(player.getDefRebound() + player.getOffRebound()));
        playerAst.setText(String.valueOf(player.getAssist()));
        playerTO.setText(String.valueOf(player.getTurnover()));
        playerStl.setText(String.valueOf(player.getSteal()));
        playerBlk.setText(String.valueOf(player.getBlock()));
        playerFoul.setText(String.valueOf(player.getFoulCount()));
        playerPts.setText(String.valueOf(player.getTotalScore()));
        layout.addView(playerTime);
        layout.addView(player2ptFG);
        layout.addView(player3ptFG);
        layout.addView(playerFT);
        layout.addView(playerOffReb);
        layout.addView(playerDefReb);
        layout.addView(playerTotalReb);
        layout.addView(playerAst);
        layout.addView(playerTO);
        layout.addView(playerStl);
        layout.addView(playerBlk);
        layout.addView(playerFoul);
        layout.addView(playerPts);
        return layout;
    }

    private Team getTeam() {
        return (Team) getIntent().getParcelableExtra(TEAM_ID);
    }
}
