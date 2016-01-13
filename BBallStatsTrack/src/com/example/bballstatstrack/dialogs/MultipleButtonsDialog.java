package com.example.bballstatstrack.dialogs;

import com.example.bballstatstrack.R;
import com.example.bballstatstrack.activities.GameActivity;

import android.app.AlertDialog;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MultipleButtonsDialog extends AlertDialog
{
    protected LinearLayout mDialogView;

    public MultipleButtonsDialog( GameActivity activity, int questionID )
    {
        super( activity );
        mDialogView = ( LinearLayout ) View.inflate( getContext(), R.layout.dialog_container, null );
        TextView question = ( TextView ) mDialogView.findViewById( R.id.dialog_container_question_textview );
        question.setText( questionID );
        setView( mDialogView );
    }
}
