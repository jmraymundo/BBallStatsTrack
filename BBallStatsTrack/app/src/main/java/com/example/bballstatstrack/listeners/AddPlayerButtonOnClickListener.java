package com.example.bballstatstrack.listeners;

import com.example.bballstatstrack.dialogs.NewPlayerDialog;
import com.example.bballstatstrack.fragments.TeamFragment;

import android.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;

public class AddPlayerButtonOnClickListener implements OnClickListener
{
    private TeamFragment mFragment;

    public AddPlayerButtonOnClickListener( TeamFragment fragment )
    {
        mFragment = fragment;
    }

    @Override
    public void onClick( View v )
    {
        AlertDialog dialog = new NewPlayerDialog( mFragment );
        dialog.show();
    }
}
