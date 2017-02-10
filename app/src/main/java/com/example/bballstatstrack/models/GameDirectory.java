package com.example.bballstatstrack.models;

import android.content.Context;

import com.example.bballstatstrack.models.utils.JSONFileSaverLoader;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class GameDirectory {
    private static final String FILENAME = "games.json";

    private static GameDirectory mInstance;

    private Context mContext;

    private ArrayList<Game> mGames;

    private JSONFileSaverLoader mFileSaverLoader;

    private GameDirectory(Context context) {
        mContext = context;
        mFileSaverLoader = new JSONFileSaverLoader(mContext, FILENAME);
        try {
            mGames = mFileSaverLoader.loadGames();
        } catch (IOException e) {
            e.printStackTrace();
            mGames = new ArrayList<Game>();
        } catch (JSONException e) {
            e.printStackTrace();
            mGames = new ArrayList<Game>();
        }
    }

    public static GameDirectory get(Context context) {
        if (mInstance == null) {
            mInstance = new GameDirectory(context);
        }
        return mInstance;
    }

    public void addGame(Game game) {
        mGames.add(game);
        saveGames();
    }

    public void deleteGame(Game game) {
        mGames.remove(game);
        saveGames();
    }

    public Game getGame(UUID id) {
        for (Game game : mGames) {
            if (game.getId().equals(id)) return game;
        }
        return null;
    }

    public ArrayList<Game> getGames() {
        return mGames;
    }

    private void saveGames() {
        try {
            mFileSaverLoader.saveGames(mGames);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
