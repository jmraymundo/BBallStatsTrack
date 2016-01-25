package com.example.bballstatstrack.models.gameevents.foulevents;

import com.example.bballstatstrack.models.Player;
import com.example.bballstatstrack.models.Team;
import com.example.bballstatstrack.models.gameevents.FoulEvent;

public class ShootingFoulEvent extends FoulEvent {
    public static final String SHOOTER = "shooter";
    public static final String SHOOTER_TEAM = "shooter_team";
    public static final String FT_COUNT = "free_throw_count";
    private static final String THREE_FREE_THROWS = "3 free throws. ";
    private static final String TWO_FREE_THROWS = "2 free throws. ";
    private static final String ONE_FREE_THROW = "1 free throw. ";
    private Player mShooter;

    private Team mShooterTeam;

    private int mFTCount;

    public ShootingFoulEvent(Player player, Team team) {
        super(FoulType.SHOOTING, player, team);
    }

    public ShootingFoulEvent(Player player, Team team, Player shooter, Team shooterTeam, int ftCount) {
        this(player, team);
        mShooter = shooter;
        mShooterTeam = shooterTeam;
        mFTCount = ftCount;
    }

    public int getFTCount() {
        return mFTCount;
    }

    public void setFTCount(int ftCount) {
        mFTCount = ftCount;
    }

    public Player getShooter() {
        return mShooter;
    }

    public void setShooter(Player shooter) {
        mShooter = shooter;
    }

    public Team getShooterTeam() {
        return mShooterTeam;
    }

    public void setShooterTeam(Team shooterTeam) {
        mShooterTeam = shooterTeam;
    }

    @Override
    public void resolve() {
        super.resolve();
        mTeam.addFoul();
    }

    @Override
    public String toString() {
        String output = mPlayer.getFullName() + " committed a shooting foul. ";
        if (mAppended != null) {
            String additional = mShooter.getFullName() + " to shoot ";
            switch (mFTCount) {
                case 1:
                    additional = additional.concat(ONE_FREE_THROW);
                    break;
                case 2:
                    additional = additional.concat(TWO_FREE_THROWS);
                    break;
                case 3:
                    additional = additional.concat(THREE_FREE_THROWS);
                    break;
                default:
                    break;
            }
            output = output.concat(additional);
        }
        return output.trim();
    }
}
