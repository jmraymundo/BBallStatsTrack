package com.example.bballstatstrack.fragments;

import com.example.bballstatstrack.R;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

public class AwayTeamFragment extends Fragment
{
    private static final String MY_PLAYER_NUMBERS = "myPlayerNumbers";

    private static final String MY_PLAYERS_NAMES = "myPlayerNames";

    String mTeamName;

    SparseArray< String > playerList;

    TableLayout mPlayerTable;

    Button mAddPlayerButton;

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState )
    {
        setRetainInstance( true );
        View view = inflater.inflate( R.layout.fragment_new_team, container, false );
        if( view == null )
        {
            view = super.onCreateView( inflater, container, savedInstanceState );
        }
        if( savedInstanceState != null )
        {
            loadPlayers( savedInstanceState );
        }
        setupView( view );
        return view;
    }

    private void loadPlayers( Bundle bundle )
    {
    }

    public void setupView( View view )
    {
        mPlayerTable = ( TableLayout ) view.findViewById( R.id.playerListTable );
        mAddPlayerButton = ( Button ) view.findViewById( R.id.addNewPlayerButton );
        mAddPlayerButton.setOnClickListener( new OnClickListener()
        {
            @Override
            public void onClick( View v )
            {
                int childCount = mPlayerTable.getChildCount();
                if( mPlayerTable.getChildCount() < 1 || lastEntryValid( mPlayerTable ) )
                {
                    savePlayers( mPlayerTable );
                    TableRow playerEntry = createTableRow( childCount );
                    mPlayerTable.addView( playerEntry );
                }
                Toast.makeText( getActivity(), "Button clicked!", Toast.LENGTH_SHORT ).show();
            }
        } );
    }

    private boolean lastEntryValid( TableLayout table )
    {
        int rowCount = table.getChildCount();
        TableRow lastRow = ( TableRow ) table.getChildAt( rowCount - 1 );
        EditText left = ( EditText ) lastRow.getChildAt( 0 );
        EditText right = ( EditText ) lastRow.getChildAt( 1 );
        if( left.getText().toString().isEmpty() || right.getText().toString().isEmpty() )
        {
            return false;
        }
        return true;
    }

    private TableRow createTableRow( int rowNumber )
    {
        TableRow row = new TableRow( getActivity() );
        row.setId( rowNumber );
        row.setLayoutParams(
                new TableRow.LayoutParams( TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT ) );
        EditText leftEditText = createEditText( getActivity() );
        leftEditText.setInputType( EditorInfo.TYPE_CLASS_NUMBER );
        EditText rightEditText = createEditText( getActivity() );
        row.addView( leftEditText, 0 );
        row.addView( rightEditText, 1 );
        return row;
    }

    private EditText createEditText( Context context )
    {
        EditText editText = new EditText( context );
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f );
        editText.setLayoutParams( layoutParams );
        return editText;
    }

    private void savePlayers( TableLayout table )
    {
        playerList.clear();
        for( int index = 0; index < table.getChildCount(); index++ )
        {
            TableRow row = ( TableRow ) table.getChildAt( index );
            EditText left = ( EditText ) row.getChildAt( 0 );
            EditText right = ( EditText ) row.getChildAt( 1 );
            int number = Integer.parseInt( left.getText().toString() );
            String name = right.getText().toString();
            playerList.put( number, name );

        }
    }

    @Override
    public void onDetach()
    {
        savePlayers( mPlayerTable );
        super.onDetach();
    }

    @Override
    public void onSaveInstanceState( Bundle outState )
    {
        super.onSaveInstanceState( outState );
        int max = playerList.size();
        int[] numberArray = new int[max];
        String[] nameArray = new String[max];
        for( int index = 0; index < max; index++ )
        {
            numberArray[index] = playerList.keyAt( index );
            nameArray[index] = playerList.valueAt( index );
        }
        outState.putIntArray( MY_PLAYER_NUMBERS, numberArray );
        outState.putStringArray( MY_PLAYERS_NAMES, nameArray );
    }
}
