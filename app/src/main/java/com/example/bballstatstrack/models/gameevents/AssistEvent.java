package com.example.bballstatstrack.models.gameevents;

import com.example.bballstatstrack.models.Player;
import com.example.bballstatstrack.models.Team;

public class AssistEvent extends GameEvent {

    public AssistEvent(Player player, Team team) {
        super(EventType.ASSIST, player, team);
    }

    @Override
    public void resolve() {
        mPlayer.makeAssist();
    }

    @Override
    public String toString() {
        return "Assist by " + mPlayer.getFullName() + ".";
    }
}
