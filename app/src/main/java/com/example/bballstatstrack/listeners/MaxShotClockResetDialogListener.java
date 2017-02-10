package com.example.bballstatstrack.listeners;

import android.app.Dialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bballstatstrack.R;
import com.example.bballstatstrack.activities.GameActivity;

public class MaxShotClockResetDialogListener implements OnClickListener {
    private GameActivity mActivity;

    private EditText mClockField;

    private Dialog mDialog;

    public MaxShotClockResetDialogListener(GameActivity activity, Dialog dialog, EditText clockField) {
        mActivity = activity;
        mDialog = dialog;
        mClockField = clockField;
    }

    @Override
    public void onClick(View v) {
        String clockValue = mClockField.getText().toString();
        if (clockValue.isEmpty()) {
            Toast.makeText(mActivity, mDialog.getContext().getText(R.string.clock_input_error), Toast.LENGTH_SHORT)
                    .show();
            return;
        }
        mActivity.setShotClockReset(Integer.parseInt(clockValue));
        mDialog.dismiss();
        mActivity.fetchHomeStarters();
    }
}