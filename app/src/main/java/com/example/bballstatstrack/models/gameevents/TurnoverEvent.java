package com.example.bballstatstrack.models.gameevents;

import com.example.bballstatstrack.models.Player;
import com.example.bballstatstrack.models.Team;
import com.example.bballstatstrack.models.gameevents.foulevents.OffensiveFoulEvent;

public class TurnoverEvent extends GameEvent {
    public static final String TURNOVER_TYPE = "turnoverType";

    private TurnoverType mTurnoverType;

    public TurnoverEvent(Player player, Team team) {
        super(EventType.TURNOVER, player, team);
        mTurnoverType = null;
    }

    public TurnoverEvent(TurnoverType type, Player player, Team team) {
        this(player, team);
        setTurnoverType(type);
    }

    @Override
    public void append(GameEvent appendedEvent) {
        if (mAppended != null) {
            mAppended.append(appendedEvent);
            return;
        }
        if (appendedEvent instanceof StealEvent && mTurnoverType == TurnoverType.STEAL) {
            mAppended = appendedEvent;
            return;
        } else if (appendedEvent instanceof OffensiveFoulEvent && mTurnoverType == TurnoverType.OFFENSIVE_FOUL) {
            FoulType foulType = ((FoulEvent) appendedEvent).getFoulType();
            if (foulType == FoulType.OFFENSIVE) {
                mAppended = appendedEvent;
            }
        }
    }

    public TurnoverType getTurnoverType() {
        return mTurnoverType;
    }

    public void setTurnoverType(TurnoverType turnoverType) {
        mTurnoverType = turnoverType;
    }

    @Override
    public void resolve() {
        mPlayer.makeTurnover();
        if (mAppended != null) {
            mAppended.resolve();
        }
    }

    @Override
    public String toString() {
        String output = "Turnover made by " + mPlayer.getFullName() + ". ";
        if (mAppended != null) {
            output = output.concat(mAppended.toString());
        }
        return output.trim();
    }
}
