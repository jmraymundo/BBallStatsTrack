package com.example.bballstatstrack.listeners;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

public class BackConfirmationListener implements OnClickListener {
    private Activity mActivity;

    public BackConfirmationListener(Activity activity) {
        mActivity = activity;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (which == DialogInterface.BUTTON_POSITIVE) {
            mActivity.finish();
        } else {
            dialog.dismiss();
        }
    }
}
