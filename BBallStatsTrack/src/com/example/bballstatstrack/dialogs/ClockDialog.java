package com.example.bballstatstrack.dialogs;

import com.example.bballstatstrack.R;
import com.example.bballstatstrack.activities.GameActivity;
import com.example.bballstatstrack.listeners.DismissDialogListener;
import com.example.bballstatstrack.models.utils.EditTextRangeNumberWatcher;

import android.app.AlertDialog;
import android.app.Dialog;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

public class ClockDialog extends AlertDialog
{
    protected EditText mClockField;

    protected GameActivity mActivity;

    public ClockDialog( GameActivity activity, int detailedTextResID, int maxValue )
    {
        super( activity );
        mActivity = activity;
        View mDialogView = View.inflate( getContext(), R.layout.dialog_get_clock, null );

        mClockField = ( EditText ) mDialogView.findViewById( R.id.requested_clock_field );
        mClockField.addTextChangedListener( new EditTextRangeNumberWatcher( 0, maxValue ) );
        TextView detailedQuestionView = ( TextView ) mDialogView.findViewById( R.id.requested_clock_text );
        detailedQuestionView.setText( detailedTextResID );

        setTitle( R.string.set_time_title );
        setView( mDialogView );
        setCancelable( false );
        setButton( Dialog.BUTTON_POSITIVE, getContext().getText( android.R.string.ok ), new DismissDialogListener() );
        getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE );
        setCanceledOnTouchOutside( false );
    }
}
