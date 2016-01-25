package com.example.bballstatstrack.listeners.cancel;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;

import com.example.bballstatstrack.activities.GameActivity;
import com.example.bballstatstrack.dialogs.SubstitutionButton;

public class SubstitutionOutDialogCancelListener implements OnCancelListener {
    private GameActivity mActivity;

    public SubstitutionOutDialogCancelListener(GameActivity activity) {
        mActivity = activity;
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        dialog.dismiss();
        AlertDialog returnDialog = new SubstitutionButton(mActivity);
        returnDialog.show();
    }
}
