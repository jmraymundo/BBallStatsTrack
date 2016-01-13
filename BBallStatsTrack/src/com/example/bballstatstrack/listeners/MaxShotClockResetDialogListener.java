package com.example.bballstatstrack.listeners;

import com.example.bballstatstrack.R;
import com.example.bballstatstrack.activities.GameActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

public class MaxShotClockResetDialogListener implements OnClickListener
{
    private GameActivity mActivity;

    private EditText mClockField;

    public MaxShotClockResetDialogListener( GameActivity activity, EditText clockField )
    {
        mActivity = activity;
        mClockField = clockField;
    }

    @Override
    public void onClick( DialogInterface dialog, int which )
    {
        String clockValue = mClockField.getText().toString();
        if( clockValue.isEmpty() )
        {
            Toast.makeText( mActivity, ( ( Dialog ) dialog ).getContext().getText( R.string.clock_input_error ),
                    Toast.LENGTH_SHORT ).show();
            return;
        }
        mActivity.setShotClockReset( Integer.parseInt( clockValue ) );
        dialog.dismiss();
        mActivity.fetchHomeStarters();
    }
}