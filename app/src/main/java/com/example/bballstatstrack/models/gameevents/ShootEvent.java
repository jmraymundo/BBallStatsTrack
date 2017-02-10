package com.example.bballstatstrack.models.gameevents;

import com.example.bballstatstrack.models.Player;
import com.example.bballstatstrack.models.Team;

public class ShootEvent extends GameEvent {
    public static final String IS_SHOT_MADE = "isShotMade";

    public static final String SHOT_CLASS = "shotClass";

    private ShotClass mShotClass;

    private boolean mIsShotMade;

    public ShootEvent(Player player, Team team) {
        super(EventType.SHOOT, player, team);
        mShotClass = null;
        mIsShotMade = false;
    }

    public ShootEvent(ShotClass shotClass, boolean isShotMade, Player player, Team team) {
        super(EventType.SHOOT, player, team);
        mShotClass = shotClass;
        mIsShotMade = isShotMade;
    }

    @Override
    public void append(GameEvent appendedEvent) {
        if (mAppended != null) {
            mAppended.append(appendedEvent);
            return;
        }
        if (mIsShotMade) {
            if (appendedEvent instanceof AssistEvent) {
                mAppended = appendedEvent;
                return;
            } else if (appendedEvent instanceof BlockEvent || appendedEvent instanceof ReboundEvent) {
                return;
            }
        } else {
            if (appendedEvent instanceof BlockEvent || appendedEvent instanceof ReboundEvent) {
                mAppended = appendedEvent;
                return;
            }
        }
    }

    public ShotClass getShotClass() {
        return mShotClass;
    }

    public void setShotClass(ShotClass shotClass) {
        mShotClass = shotClass;
    }

    public boolean isShotMade() {
        return mIsShotMade;
    }

    public void setShotMade(boolean isShotMade) {
        mIsShotMade = isShotMade;
    }

    @Override
    public void resolve() {
        if (mIsShotMade) {
            handleShot(true);
        } else {
            handleShot(false);
        }
        if (mAppended != null) {
            mAppended.resolve();
        }
    }

    @Override
    public String toString() {
        String shotType = mIsShotMade ? "made" : "missed";
        String shotClass = mShotClass.getText();
        String output = mPlayer.getFullName() + " " + shotType + " a " + shotClass + ". ";
        if (mAppended != null) {
            output = output.concat(mAppended.toString());
        }
        return output.trim();
    }

    private void handleShot(boolean shotMade) {
        switch (mShotClass) {
            case FT:
                mPlayer.shootFT(shotMade);
                return;
            case FG_2PT:
                mPlayer.shoot2pt(shotMade);
                return;
            case FG_3PT:
                mPlayer.shoot3pt(shotMade);
                return;
        }
    }
}
