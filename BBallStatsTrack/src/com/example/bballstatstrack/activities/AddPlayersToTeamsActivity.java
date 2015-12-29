package com.example.bballstatstrack.activities;

import com.example.bballstatstrack.R;
import com.example.bballstatstrack.fragments.TeamFragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class AddPlayersToTeamsActivity extends Activity
{

    protected static final String HOME_TEAM_NAMES = "HomeTeamNames";

    protected static final String HOME_TEAM_NUMBERS = "HomeTeamNumbers";

    protected static final String AWAY_TEAM_NAMES = "AwayTeamNames";

    protected static final String AWAY_TEAM_NUMBERS = "AwayTeamNumbers";

    SparseArray< String > mHomeTeam;

    SparseArray< String > mAwayTeam;

    TeamFragment mHomeTeamFragment;

    TeamFragment mAwayTeamFragment;

    Button mHomeButton;

    Button mAwayButton;

    Button mProceedButton;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_new_team );
        mHomeTeamFragment = new TeamFragment( true );
        mAwayTeamFragment = new TeamFragment( false );
        mHomeButton = ( Button ) findViewById( R.id.homeTeamButton );
        mHomeButton.setOnClickListener( new FragmentButtonListener( mHomeTeamFragment ) );
        mAwayButton = ( Button ) findViewById( R.id.awayTeamButton );
        mAwayButton.setOnClickListener( new FragmentButtonListener( mAwayTeamFragment ) );
        mProceedButton = ( Button ) findViewById( R.id.proceedButton );
        mProceedButton.setOnClickListener( new ProceedButtonListener() );
        replaceCurrentFragment( mHomeTeamFragment );
        updateView( mHomeTeamFragment );
    }

    private void updateView( Fragment current )
    {
        if( mHomeTeamFragment.equals( current ) )
        {
            mHomeButton.setEnabled( false );
            mAwayButton.setEnabled( true );
        }
        else if( mAwayTeamFragment.equals( current ) )
        {
            mHomeButton.setEnabled( true );
            mAwayButton.setEnabled( false );
        }
        else
        {
            mHomeButton.setEnabled( true );
            mAwayButton.setEnabled( true );
        }
    }

    private void replaceCurrentFragment( Fragment fragment )
    {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace( R.id.fragmentContainer, fragment );
        transaction.commit();
    }

    private Fragment getCurrentFragment()
    {
        FragmentManager manager = getFragmentManager();
        return manager.findFragmentById( R.id.fragmentContainer );
    }

    private class FragmentButtonListener implements OnClickListener
    {
        Fragment mFragment;

        public FragmentButtonListener( Fragment fragment )
        {
            mFragment = fragment;
        }

        @Override
        public void onClick( View v )
        {
            if( mFragment.equals( getCurrentFragment() ) )
            {
                return;
            }
            replaceCurrentFragment( mFragment );
            updateView( mFragment );
        }
    }

    @Override
    public void onBackPressed()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder( AddPlayersToTeamsActivity.this );
        builder.setTitle( R.string.on_back_button_title );
        builder.setMessage( R.string.on_back_button_message );
        builder.setPositiveButton( R.string.yes, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick( DialogInterface dialog, int which )
            {
                cancelActivity();
            }
        } );
        builder.setNegativeButton( R.string.no, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick( DialogInterface dialog, int which )
            {
                dialog.dismiss();
            }
        } );
        builder.show();
    }

    protected void cancelActivity()
    {
        super.onBackPressed();
    }

    private class ProceedButtonListener implements OnClickListener
    {
        @Override
        public void onClick( View v )
        {
            mHomeTeam = mHomeTeamFragment.getPlayerList();
            mAwayTeam = mAwayTeamFragment.getPlayerList();
            if( mHomeTeam.size() < 5 || mAwayTeam.size() < 5 )
            {
                Toast.makeText( AddPlayersToTeamsActivity.this, "Insuffecient players. Cannot proceed.",
                        Toast.LENGTH_SHORT ).show();
                return;
            }
            Intent intent = new Intent( AddPlayersToTeamsActivity.this, GameActivity.class );
            setIntentExtras( intent, mHomeTeam, HOME_TEAM_NUMBERS, HOME_TEAM_NAMES );
            setIntentExtras( intent, mAwayTeam, AWAY_TEAM_NUMBERS, AWAY_TEAM_NAMES );
            startActivity( intent );
        }

        private void setIntentExtras( Intent intent, SparseArray< String > team, String extraTeamNumbers,
                String extraTeamNames )
        {
            int size = team.size();
            int[] numbers = new int[size];
            String[] names = new String[size];
            for( int i = 0; i < size; i++ )
            {
                numbers[i] = team.keyAt( i );
                names[i] = team.valueAt( i );
            }
            intent.putExtra( extraTeamNumbers, numbers );
            intent.putExtra( extraTeamNames, names );
        }
    }
}