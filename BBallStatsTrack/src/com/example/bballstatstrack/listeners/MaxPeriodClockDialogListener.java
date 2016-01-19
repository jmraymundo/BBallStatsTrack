package com.example.bballstatstrack.listeners;

import com.example.bballstatstrack.R;
import com.example.bballstatstrack.activities.GameActivity;
import com.example.bballstatstrack.dialogs.MaxShotClockResetDialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

public class MaxPeriodClockDialogListener implements OnClickListener
{
    private GameActivity mActivity;

    private EditText mClockField;

    private Dialog mDialog;

    public MaxPeriodClockDialogListener( GameActivity activity, Dialog dialog, EditText clockField )
    {
        mActivity = activity;
        mDialog = dialog;
        mClockField = clockField;
    }

    @Override
    public void onClick( View v )
    {
        String clockValue = mClockField.getText().toString();
        if( clockValue.isEmpty() )
        {
            Toast.makeText( mActivity, mDialog.getContext().getText( R.string.clock_input_error ), Toast.LENGTH_SHORT )
                    .show();
            return;
        }
        mActivity.setMaxPeriodClock( Integer.parseInt( clockValue ) );
        mDialog.dismiss();
        AlertDialog nextDialog = new MaxShotClockResetDialog( mActivity );
        nextDialog.show();
    }
}