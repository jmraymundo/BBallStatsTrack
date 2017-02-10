package com.example.bballstatstrack.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;

import com.example.bballstatstrack.R;
import com.example.bballstatstrack.listeners.BackConfirmationListener;

public class BackConfirmationDialog extends AlertDialog {
    public BackConfirmationDialog(Activity activity) {
        super(activity);
        setTitle(R.string.on_back_button_title);
        setMessage(getContext().getText(R.string.on_back_button_message));
        BackConfirmationListener listener = new BackConfirmationListener(activity);
        setButton(Dialog.BUTTON_POSITIVE, getContext().getText(R.string.yes), listener);
        setButton(Dialog.BUTTON_NEGATIVE, getContext().getText(R.string.no), listener);
    }
}
