package com.example.bballstatstrack.listeners.cancel;

import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;

public class DismissDialogOnListener implements OnCancelListener
{

    @Override
    public void onCancel( DialogInterface dialog )
    {
        dialog.dismiss();
    }

}
