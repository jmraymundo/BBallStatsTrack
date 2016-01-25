package com.example.bballstatstrack.models.gameevents.foulevents;

import com.example.bballstatstrack.models.Player;
import com.example.bballstatstrack.models.Team;
import com.example.bballstatstrack.models.gameevents.FoulEvent;

public class NonShootingFoulEvent extends FoulEvent {

    public static final String NON_SHOOTING_FOUL_TYPE = "nonShootingFoulType";

    private NonShootingFoulType mNonShootingFoulType;

    public NonShootingFoulEvent(NonShootingFoulType type, Player player, Team team) {
        super(FoulType.NON_SHOOTING, player, team);
        mNonShootingFoulType = type;
    }

    public NonShootingFoulType getNonShootingFoulType() {
        return mNonShootingFoulType;
    }

    @Override
    public void resolve() {
        super.resolve();
        mTeam.addFoul();
    }

    @Override
    public String toString() {
        return mPlayer.getFullName() + " committed a foul.";
    }
}
