package com.example.bballstatstrack.listeners;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

public class DismissDialogListener implements OnClickListener {
    @Override
    public void onClick(DialogInterface dialog, int which) {
        dialog.dismiss();
    }
}
