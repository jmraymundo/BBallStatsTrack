package com.example.bballstatstrack.models.gameevents;

import com.example.bballstatstrack.R;
import com.example.bballstatstrack.models.Player;
import com.example.bballstatstrack.models.Team;

public abstract class GameEvent {
    public static final String TEAM_ID = "teamID";

    public static final String APPENDED = "appended";

    public static final String EVENT_TYPE = "eventType";

    public static final String PLAYER_NUMBER = "playerNumber";

    public static final String TIME = "time";

    public static final String ADDITIONAL_DETAILS = "additionalDetails";

    protected EventType mEvent;

    protected Player mPlayer;

    protected Team mTeam;

    protected GameEvent mAppended = null;

    protected int mTime;

    protected GameEvent(EventType event, Player player, Team team) {
        mEvent = event;
        mPlayer = player;
        mTeam = team;
    }

    public void append(GameEvent appendedEvent) {
        return;
    }

    public GameEvent getAppended() {
        return mAppended;
    }

    public EventType getEventType() {
        return mEvent;
    }

    public Player getPlayer() {
        return mPlayer;
    }

    public Team getTeam() {
        return mTeam;
    }

    public int getTime() {
        return mTime;
    }

    public void setTime(int time) {
        mTime = time;
    }

    public abstract void resolve();

    @Override
    public abstract String toString();

    public enum EventType {
        FOUL, SHOOT, REBOUND, TURNOVER, ASSIST, SUBSTITUTION, TIME_OUT, BLOCK, STEAL;
    }

    public enum FoulType {
        SHOOTING, NON_SHOOTING, OFFENSIVE;
    }

    public enum NonShootingFoulType {
        PENALTY, NON_PENALTY;
    }

    public enum ReboundType {
        OFFENSIVE, DEFENSIVE;
    }

    public enum ShotClass {
        FT("FT shot"), FG_2PT("2pt shot"), FG_3PT("3pt shot");

        private final String mText;

        private ShotClass(String text) {
            mText = text;
        }

        public String getText() {
            return mText;
        }
    }

    public enum TurnoverType {
        STEAL(R.string.turnover_dialog_type_steal_text), OFFENSIVE_FOUL(
                R.string.turnover_dialog_type_offensivefoul_text), OTHER(R.string.turnover_dialog_type_other_text);

        private final int mTextResId;

        private TurnoverType(int textRedId) {
            mTextResId = textRedId;
        }

        public int getTextResId() {
            return mTextResId;
        }
    }
}
