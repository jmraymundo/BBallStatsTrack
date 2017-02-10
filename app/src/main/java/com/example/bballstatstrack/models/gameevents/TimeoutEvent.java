package com.example.bballstatstrack.models.gameevents;

import com.example.bballstatstrack.models.Team;

public class TimeoutEvent extends GameEvent {

    public TimeoutEvent(Team team) {
        super(EventType.TIME_OUT, null, team);
    }

    @Override
    public void resolve() {
        mTeam.useTimeOut();
        if (mAppended != null) {
            mAppended.resolve();
        }
    }

    @Override
    public String toString() {
        return "Timeout called by " + mTeam.getName() + ".";
    }

}
