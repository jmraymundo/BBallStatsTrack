package com.example.bballstatstrack.models.gameevents;

import com.example.bballstatstrack.models.Player;
import com.example.bballstatstrack.models.Team;

public class StealEvent extends GameEvent {

    public StealEvent(Player player, Team team) {
        super(EventType.STEAL, player, team);
    }

    @Override
    public void resolve() {
        mPlayer.makeSteal();
        if (mAppended != null) {
            mAppended.resolve();
        }
    }

    @Override
    public String toString() {
        return "Steal by " + mPlayer.getFullName() + ".";
    }

}
