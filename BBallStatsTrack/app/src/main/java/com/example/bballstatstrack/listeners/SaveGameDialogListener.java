package com.example.bballstatstrack.listeners;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

import com.example.bballstatstrack.activities.GameActivity;
import com.example.bballstatstrack.models.Game;
import com.example.bballstatstrack.models.GameDirectory;

public final class SaveGameDialogListener implements OnClickListener {
    private final GameActivity myMActivity;

    private final Game myMGame;

    public SaveGameDialogListener(GameActivity mActivity, Game mGame) {
        myMActivity = mActivity;
        myMGame = mGame;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        dialog.dismiss();
        GameDirectory.get(myMActivity).addGame(myMGame);
        myMActivity.timerStop();
        myMActivity.finish();
    }
}
