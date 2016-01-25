package com.example.bballstatstrack.models.gameevents;

import com.example.bballstatstrack.models.Player;
import com.example.bballstatstrack.models.Team;

public class ReboundEvent extends GameEvent {
    public static final String REBOUND_TYPE = "reboundType";

    ReboundType mReboundType;

    public ReboundEvent(ReboundType type, Player player, Team team) {
        super(EventType.REBOUND, player, team);
        mReboundType = type;
    }

    public ReboundType getReboundType() {
        return mReboundType;
    }

    @Override
    public void resolve() {
        if (mPlayer == null) {
            mTeam.makeTeamRebound();
        } else {
            switch (mReboundType) {
                case OFFENSIVE:
                    mPlayer.makeRebound(true);
                    break;
                case DEFENSIVE:
                    mPlayer.makeRebound(false);
                    break;
            }
        }
        if (mAppended != null) {
            mAppended.resolve();
        }
    }

    @Override
    public String toString() {
        String type = "";
        String rebounder = "";
        if (mPlayer == null) {
            type = "Team";
            rebounder = mTeam.getName();
        } else {
            switch (mReboundType) {
                case OFFENSIVE:
                    type = "Offensive";
                    rebounder = mPlayer.getFullName();
                    break;
                case DEFENSIVE:
                    type = "Defensive";
                    rebounder = mPlayer.getFullName();
                    break;
            }
        }
        return type + " rebound by " + rebounder + ".";
    }
}
