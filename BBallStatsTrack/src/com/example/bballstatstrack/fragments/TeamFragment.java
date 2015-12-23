package com.example.bballstatstrack.fragments;

import com.example.bballstatstrack.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class TeamFragment extends Fragment
{
    String mTeamName;

    EditText mTeamNameEditText;

    SparseArray< String > mPlayerList;

    TableLayout mPlayerTable;

    Button mAddPlayerButton;

    boolean isHomeTeam;

    private TextView mLocation;

    public TeamFragment( boolean isHome )
    {
        super();
        isHomeTeam = isHome;
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState )
    {
        View view = inflater.inflate( R.layout.fragment_new_team, container, false );
        if( mPlayerList == null )
        {
            mPlayerList = new SparseArray< String >();
        }
        if( view == null )
        {
            view = super.onCreateView( inflater, container, savedInstanceState );
        }
        setupView( view );
        setupKeyboardHiding( view );
        return view;
    }

    public void setupView( View view )
    {
        mLocation = ( TextView ) view.findViewById( R.id.team_location_text );
        if( isHomeTeam )
        {
            mLocation.setText( R.string.home_team );
        }
        else
        {
            mLocation.setText( R.string.away_team );
        }
        mPlayerTable = ( TableLayout ) view.findViewById( R.id.playerListTable );
        if( mPlayerList.size() > 0 )
        {
            updateTableView();
        }
        mTeamNameEditText = ( EditText ) view.findViewById( R.id.team_name_editText );
        mAddPlayerButton = ( Button ) view.findViewById( R.id.addNewPlayerButton );
        mAddPlayerButton.setOnClickListener( new OnClickListener()
        {
            @Override
            public void onClick( View v )
            {
                showNewPlayerDialog();
            }
        } );
    }

    private void showNewPlayerDialog()
    {
        Builder builder = new AlertDialog.Builder( getActivity() );
        View dialogView = getActivity().getLayoutInflater().inflate( R.layout.dialog_new_player, null );
        EditText playerNumberText = ( EditText ) dialogView.findViewById( R.id.player_number_editText );
        playerNumberText.addTextChangedListener( new PlayerNumberWatcher() );
        builder.setView( dialogView );
        builder.setTitle( R.string.add_new_player );
        builder.setPositiveButton( android.R.string.ok, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick( DialogInterface dialog, int which )
            {
                EditText playerNumberText = ( EditText ) ( ( AlertDialog ) dialog )
                        .findViewById( R.id.player_number_editText );
                EditText playerNameText = ( EditText ) ( ( AlertDialog ) dialog )
                        .findViewById( R.id.player_name_editText );

                int playerNumber = Integer.parseInt( playerNumberText.getText().toString() );
                String playerName = playerNameText.getText().toString();

                mPlayerList.put( playerNumber, playerName );

                updateTableView();
            }
        } );
        builder.setNegativeButton( android.R.string.cancel, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick( DialogInterface dialog, int which )
            {
                dialog.cancel();
            }
        } );
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private class PlayerNumberWatcher implements TextWatcher
    {
        String mCurrent = "";

        @Override
        public void beforeTextChanged( CharSequence s, int start, int count, int after )
        {
            if( s == null || s.length() == 0 )
            {
                mCurrent = "";
                return;
            }
            mCurrent = s.toString();
        }

        @Override
        public void onTextChanged( CharSequence s, int start, int before, int count )
        {
            // TODO Auto-generated method stub
        }

        @Override
        public void afterTextChanged( Editable s )
        {
            if( s == null || s.length() == 0 )
            {
                return;
            }
            int input = Integer.parseInt( s.toString() );
            if( inRange( input ) )
            {
                return;
            }
            s.replace( 0, s.length(), mCurrent );
        }

        private boolean inRange( int input )
        {
            return 0 <= input && input <= 99;
        }
    }

    public void updateTableView()
    {
        mPlayerTable.removeViews( 1, mPlayerTable.getChildCount() - 1 );
        for( int index = 0; index < mPlayerList.size(); index++ )
        {
            TableRow row = createTableRow( index );
            mPlayerTable.addView( row, index + 1 );
        }
    }

    private TableRow createTableRow( int rowNumber )
    {
        TableRow row = new TableRow( getActivity() );
        row.setId( rowNumber );
        row.setLayoutParams(
                new TableRow.LayoutParams( TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT ) );
        TextView leftEditText = createEditText( getActivity() );
        leftEditText.setText( String.valueOf( mPlayerList.keyAt( rowNumber ) ) );
        TextView rightEditText = createEditText( getActivity() );
        rightEditText.setText( mPlayerList.valueAt( rowNumber ) );
        row.addView( leftEditText, 0 );
        row.addView( rightEditText, 1 );
        return row;
    }

    private TextView createEditText( Context context )
    {
        TextView editText = new TextView( context );
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f );
        editText.setLayoutParams( layoutParams );
        editText.setBackgroundResource( R.drawable.cell_border );
        return editText;
    }

    public void setupKeyboardHiding( View v )
    {
        if( v instanceof EditText )
        {
            return;
        }
        else if( v instanceof ViewGroup )
        {
            int childCount = ( ( ViewGroup ) v ).getChildCount();
            for( int i = 0; i < childCount; i++ )
            {
                View innerView = ( ( ViewGroup ) v ).getChildAt( i );
                setupKeyboardHiding( innerView );
                return;
            }
        }
        v.setOnTouchListener( new OnTouchListener()
        {
            public boolean onTouch( View v, MotionEvent event )
            {
                hideKeyboard( getActivity() );
                return false;
            }
        } );
    }

    private void hideKeyboard( Activity activity )
    {
        InputMethodManager inputMethodManager = ( InputMethodManager ) activity
                .getSystemService( Activity.INPUT_METHOD_SERVICE );
        inputMethodManager.hideSoftInputFromWindow( activity.getCurrentFocus().getWindowToken(), 0 );
    }
}
