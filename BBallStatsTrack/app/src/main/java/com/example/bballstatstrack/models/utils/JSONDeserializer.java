package com.example.bballstatstrack.models.utils;

import android.util.JsonReader;
import android.util.JsonToken;

import com.example.bballstatstrack.models.Game;
import com.example.bballstatstrack.models.Game.GameStats;
import com.example.bballstatstrack.models.Player;
import com.example.bballstatstrack.models.PlayerList;
import com.example.bballstatstrack.models.Team;
import com.example.bballstatstrack.models.Team.TeamStats;
import com.example.bballstatstrack.models.game.GameLog;
import com.example.bballstatstrack.models.gameevents.AssistEvent;
import com.example.bballstatstrack.models.gameevents.BlockEvent;
import com.example.bballstatstrack.models.gameevents.FoulEvent;
import com.example.bballstatstrack.models.gameevents.GameEvent;
import com.example.bballstatstrack.models.gameevents.GameEvent.EventType;
import com.example.bballstatstrack.models.gameevents.GameEvent.FoulType;
import com.example.bballstatstrack.models.gameevents.GameEvent.NonShootingFoulType;
import com.example.bballstatstrack.models.gameevents.GameEvent.ReboundType;
import com.example.bballstatstrack.models.gameevents.GameEvent.ShotClass;
import com.example.bballstatstrack.models.gameevents.GameEvent.TurnoverType;
import com.example.bballstatstrack.models.gameevents.ReboundEvent;
import com.example.bballstatstrack.models.gameevents.ShootEvent;
import com.example.bballstatstrack.models.gameevents.StealEvent;
import com.example.bballstatstrack.models.gameevents.SubstitutionEvent;
import com.example.bballstatstrack.models.gameevents.TimeoutEvent;
import com.example.bballstatstrack.models.gameevents.TurnoverEvent;
import com.example.bballstatstrack.models.gameevents.foulevents.NonShootingFoulEvent;
import com.example.bballstatstrack.models.gameevents.foulevents.OffensiveFoulEvent;
import com.example.bballstatstrack.models.gameevents.foulevents.ShootingFoulEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JSONDeserializer {
    private static Team mHomeTeam = null;

    private static Team mAwayTeam = null;

    public static Game readGame(JsonReader reader) throws IOException {
        UUID id = null;
        long longDate = Long.MIN_VALUE;
        GameLog gameLog = null;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals(GameStats.ID.toString())) {
                id = UUID.fromString(reader.nextString());
            } else if (name.equals(GameStats.DATE.toString())) {
                longDate = reader.nextLong();
            } else if (name.equals(GameStats.HOME_TEAM.toString())) {
                mHomeTeam = readTeam(reader);
            } else if (name.equals(GameStats.AWAY_TEAM.toString())) {
                mAwayTeam = readTeam(reader);
            } else if (name.equals(GameStats.GAME_LOG.toString())) {
                gameLog = readGameLog(reader);
            }
        }
        reader.endObject();
        return new Game(id, longDate, mHomeTeam, mAwayTeam, gameLog);
    }

    private static Team getOtherTeam(Team team) {
        if (team.equals(mHomeTeam)) {
            return mAwayTeam;
        } else if (team.equals(mAwayTeam)) {
            return mHomeTeam;
        }
        return null;
    }

    private static Player getPlayerFromTeam(Team team, int playerNumber) {
        if (playerNumber == -1) {
            return null;
        }
        return team.getPlayers().getPlayer(playerNumber);
    }

    private static int getPlayerNumber(JsonReader reader) throws IOException {
        if (reader.peek() == JsonToken.NULL) {
            reader.nextNull();
            return -1;
        } else {
            return reader.nextInt();
        }
    }

    private static Player getPlayerNumberFromOtherTeam(Team team, int number) {
        Team otherTeam = getOtherTeam(team);
        return otherTeam.getPlayers().getPlayer(number);
    }

    private static Team getTeamFromUUID(UUID teamID) {
        if (teamID.equals(mHomeTeam.getID())) {
            return mHomeTeam;
        } else if (teamID.equals(mAwayTeam.getID())) {
            return mAwayTeam;
        }
        return null;
    }

    private static GameEvent readEvent(JsonReader reader) throws IOException {
        if (reader.peek() == JsonToken.NULL) {
            reader.nextNull();
            return null;
        }
        GameEvent thisEvent = null;
        reader.beginObject();
        reader.nextName(); // GameEvent.EVENT_TYPE
        EventType type = EventType.valueOf(reader.nextString());
        reader.nextName(); // GameEvent.PLAYER_NUMBER
        int playerNumber = getPlayerNumber(reader);
        reader.nextName();// GameEvent.TEAM_ID
        UUID teamID = UUID.fromString(reader.nextString());
        Team team = getTeamFromUUID(teamID);
        Player player = getPlayerFromTeam(team, playerNumber);
        reader.nextName();// GameEvent.TIME
        int time = reader.nextInt();
        reader.nextName();// GameEvent.APPENDED
        GameEvent appended = readEvent(reader);
        switch (type) {
            // cases where GameEvent.ADDITIONAL_DETAILS has a value
            case SHOOT:
                thisEvent = readEventShoot(reader, player, team);
                break;
            case FOUL:
                thisEvent = readEventFoul(reader, player, team);
                break;
            case TURNOVER:
                thisEvent = readEventTurnover(reader, team, player);
                break;
            case REBOUND:
                thisEvent = readEventRebound(reader, team, player);
                break;
            case SUBSTITUTION:
                thisEvent = readEventSubstitution(reader, team, player);
                break;
            // cases where GameEvent.ADDITIONAL_DETAILS has a null value
            case ASSIST:
                thisEvent = new AssistEvent(player, team);
                break;
            case BLOCK:
                thisEvent = new BlockEvent(player, team);
                break;
            case STEAL:
                thisEvent = new StealEvent(player, team);
                break;
            case TIME_OUT:
                thisEvent = new TimeoutEvent(team);
                break;
        }
        thisEvent.setTime(time);
        reader.endObject();
        if (thisEvent != null) {
            thisEvent.append(appended);
        }
        return thisEvent;
    }

    private static FoulEvent readEventFoul(JsonReader reader, Player player, Team team) throws IOException {
        reader.nextName();
        FoulType foulType = FoulType.valueOf(reader.nextString());
        switch (foulType) {
            case OFFENSIVE:
                return new OffensiveFoulEvent(player, team);
            case NON_SHOOTING:
                reader.nextName();
                return new NonShootingFoulEvent(NonShootingFoulType.valueOf(reader.nextString()), player, team);
            case SHOOTING:
                reader.nextName();
                int otherPlayerNumber = reader.nextInt();
                reader.nextName();
                String otherTeamUUID = reader.nextString();
                reader.nextName();
                int ftCount = reader.nextInt();
                return new ShootingFoulEvent(player, team, getPlayerNumberFromOtherTeam(team, otherPlayerNumber),
                        getTeamFromUUID(UUID.fromString(otherTeamUUID)), ftCount);
            default:
                return null;
        }
    }

    private static ReboundEvent readEventRebound(JsonReader reader, Team team, Player player) throws IOException {
        reader.nextName();// ReboundEvent.REBOUND_TYPE
        return new ReboundEvent(ReboundType.valueOf(reader.nextString()), player, team);
    }

    private static ShootEvent readEventShoot(JsonReader reader, Player player, Team team) throws IOException {
        reader.nextName();// ShootEvent.SHOT_CLASS
        ShotClass shotClass = ShotClass.valueOf(reader.nextString());
        reader.nextName();// ShootEvent.IS_SHOT_MADE
        boolean isShotMade = reader.nextBoolean();
        return new ShootEvent(shotClass, isShotMade, player, team);
    }

    private static SubstitutionEvent readEventSubstitution(JsonReader reader, Team team, Player player)
            throws IOException {
        reader.nextName();// SubstitutionEvent.NEW_PLAYER_NUMBER
        return new SubstitutionEvent(player, getPlayerFromTeam(team, reader.nextInt()), team);
    }

    private static TurnoverEvent readEventTurnover(JsonReader reader, Team team, Player player) throws IOException {
        reader.nextName();// TurnoverEvent.TURNOVER_TYPE
        return new TurnoverEvent(TurnoverType.valueOf(reader.nextString()), player, team);
    }

    private static GameLog readGameLog(JsonReader reader) throws IOException {
        GameLog gameLog = new GameLog();
        reader.beginArray();
        int index = 0;
        while (reader.hasNext()) {
            gameLog.append(index, readPeriodLog(reader));
            index++;
        }
        reader.endArray();
        return gameLog;
    }

    private static List<GameEvent> readPeriodLog(JsonReader reader) throws IOException {
        List<GameEvent> periodLog = new ArrayList<GameEvent>();
        reader.beginArray();
        while (reader.hasNext()) {
            reader.beginObject();
            reader.nextName();
            GameEvent event = readEvent(reader);
            periodLog.add(event);
            reader.endObject();
        }
        reader.endArray();
        return periodLog;
    }

    private static Player readPlayer(JsonReader reader) throws IOException {
        reader.beginObject();
        reader.nextName();// PlayerStats.NUMBER
        int number = reader.nextInt();
        reader.nextName();// PlayerStats.NAME
        String fullName = reader.nextString();
        reader.nextName();// PlayerStats.MISS_1PT
        int miss1pt = reader.nextInt();
        reader.nextName();// PlayerStats.MISS_2PT
        int miss2pt = reader.nextInt();
        reader.nextName();// PlayerStats.MISS_3PT
        int miss3pt = reader.nextInt();
        reader.nextName();// PlayerStats.MADE_1PT
        int made1pt = reader.nextInt();
        reader.nextName();// PlayerStats.MADE_2PT
        int made2pt = reader.nextInt();
        reader.nextName();// PlayerStats.MADE_3PT
        int made3pt = reader.nextInt();
        reader.nextName();// PlayerStats.OFFENSIVE_REBOUND
        int offReb = reader.nextInt();
        reader.nextName();// PlayerStats.DEFENSIVE_REBOUND
        int defReb = reader.nextInt();
        reader.nextName();// PlayerStats.ASSIST
        int assist = reader.nextInt();
        reader.nextName();// PlayerStats.TURNOVER
        int to = reader.nextInt();
        reader.nextName();// PlayerStats.STEAL
        int stl = reader.nextInt();
        reader.nextName();// PlayerStats.BLOCK
        int blk = reader.nextInt();
        reader.nextName();// PlayerStats.FOUL
        int foul = reader.nextInt();
        reader.nextName();// PlayerStats.PLAYING_TIME
        int playingTimeSec = reader.nextInt();
        reader.endObject();
        return new Player(number, fullName, miss1pt, miss2pt, miss3pt, made1pt, made2pt, made3pt, offReb, defReb,
                assist, to, stl, blk, foul, playingTimeSec);
    }

    private static PlayerList readPlayerList(JsonReader reader) throws IOException {
        PlayerList list = new PlayerList();
        Player player;
        reader.beginArray();
        while (reader.hasNext()) {
            player = readPlayer(reader);
            list.addPlayer(player);
        }
        reader.endArray();
        return list;
    }

    private static Team readTeam(JsonReader reader) throws IOException {
        UUID id = null;
        String teamName = null;
        PlayerList playerList = null;
        int teamFouls = Integer.MIN_VALUE;
        int teamRebounds = Integer.MIN_VALUE;
        int timeouts = Integer.MIN_VALUE;
        int possessionTimeSec = Integer.MIN_VALUE;
        reader.beginObject();
        String name;
        while (reader.hasNext()) {
            name = reader.nextName();
            if (name.equals(TeamStats.TEAM_ID.toString())) {
                id = UUID.fromString(reader.nextString());
            } else if (name.equals(TeamStats.NAME.toString())) {
                teamName = reader.nextString();
            } else if (name.equals(TeamStats.PLAYER_LIST.toString())) {
                playerList = readPlayerList(reader);
            } else if (name.equals(TeamStats.TOTAL_FOULS.toString())) {
                teamFouls = reader.nextInt();
            } else if (name.equals(TeamStats.TEAM_REBOUNDS.toString())) {
                teamRebounds = reader.nextInt();
            } else if (name.equals(TeamStats.TIMEOUTS.toString())) {
                timeouts = reader.nextInt();
            } else if (name.equals(TeamStats.POSSESSION_TIME.toString())) {
                possessionTimeSec = reader.nextInt();
            }
        }
        reader.endObject();
        return new Team(id, teamName, playerList, teamFouls, teamRebounds, timeouts, possessionTimeSec);
    }
}
