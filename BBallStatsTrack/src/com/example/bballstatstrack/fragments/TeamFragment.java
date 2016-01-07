package com.example.bballstatstrack.fragments;

import com.example.bballstatstrack.R;
import com.example.bballstatstrack.models.utils.PlayerNumberWatcher;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView.ScaleType;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class TeamFragment extends Fragment
{
    EditText mTeamNameEditText;

    SparseArray< String > mPlayerList = new SparseArray< String >();

    TableLayout mPlayerTable;

    Button mAddPlayerButton;

    boolean isHomeTeam;

    private TextView mGameSide;

    public TeamFragment( boolean isHome )
    {
        super();
        isHomeTeam = isHome;
    }

    public SparseArray< String > getPlayerList()
    {
        return mPlayerList;
    }

    public String getTeamName()
    {
        if( mTeamNameEditText == null )
        {
            return new String();
        }
        return mTeamNameEditText.getText().toString();
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
        return view;
    }

    public void setupKeyboardHiding( View view )
    {
        if( view instanceof EditText )
        {
            return;
        }
        else if( view instanceof ViewGroup )
        {
            int childCount = ( ( ViewGroup ) view ).getChildCount();
            for( int i = 0; i < childCount; i++ )
            {
                View innerView = ( ( ViewGroup ) view ).getChildAt( i );
                setupKeyboardHiding( innerView );
            }
        }
        view.setOnTouchListener( new OnTouchListener()
        {
            @SuppressLint("ClickableViewAccessibility")
            public boolean onTouch( View v, MotionEvent event )
            {
                hideKeyboard();
                return false;
            }
        } );
    }

    public void setupView( View view )
    {
        mGameSide = ( TextView ) view.findViewById( R.id.team_location_text );
        if( isHomeTeam )
        {
            mGameSide.setText( R.string.home_team );
        }
        else
        {
            mGameSide.setText( R.string.away_team );
        }
        mPlayerTable = ( TableLayout ) view.findViewById( R.id.playerListTable );
        if( mPlayerList.size() > 0 )
        {
            updateTableView();
        }
        mTeamNameEditText = ( EditText ) view.findViewById( R.id.team_name_editText );
        mTeamNameEditText.setMaxLines( 1 );
        mAddPlayerButton = ( Button ) view.findViewById( R.id.addNewPlayerButton );
        mAddPlayerButton.setOnClickListener( new OnClickListener()
        {
            @Override
            public void onClick( View v )
            {
                showNewPlayerDialog();
            }
        } );
        setupKeyboardHiding( view );
        View activityView = getActivity().findViewById( R.id.newTeamContainer );
        setupKeyboardHiding( activityView );
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

    private void addNewPlayerFromDialog( DialogInterface dialog )
    {
        AlertDialog alertDialog = ( AlertDialog ) dialog;
        EditText playerNumberText = ( EditText ) alertDialog.findViewById( R.id.player_number_editText );
        EditText playerNameText = ( EditText ) alertDialog.findViewById( R.id.player_name_editText );
        String playerNumberString = playerNumberText.getText().toString();
        String playerNameString = playerNameText.getText().toString();
        if( playerNumberString.isEmpty() || playerNameString.isEmpty() )
        {
            Toast.makeText( getActivity(), getResources().getString( R.string.player_input_error ), Toast.LENGTH_SHORT )
                    .show();
            return;
        }
        int playerNumber = Integer.parseInt( playerNumberString );
        String playerName = playerNameString;
        mPlayerList.put( playerNumber, playerName );
        updateTableView();
        dialog.dismiss();
    }

    private AlertDialog buildDialog()
    {
        Builder builder = new AlertDialog.Builder( getActivity() );
        View dialogView = getActivity().getLayoutInflater().inflate( R.layout.dialog_new_player, null );
        builder.setView( dialogView );
        builder.setTitle( R.string.add_new_player );
        builder.setPositiveButton( android.R.string.ok, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick( DialogInterface dialog, int which )
            {
                // Will be overridden
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
        final AlertDialog dialog = builder.create();
        return dialog;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressWarnings("deprecation")
    private ImageButton createDeleteButton( final int rowNumber )
    {
        ImageButton button = new ImageButton( getActivity() );
        button.setLayoutParams( new LayoutParams( LayoutParams.WRAP_CONTENT, getPixelHeight(), 5 ) );
        button.setBackgroundResource( R.drawable.cell_border );
        Resources resource = getResources();
        Drawable drawable;
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP )
        {
            drawable = resource.getDrawable( android.R.drawable.ic_delete, getActivity().getTheme() );
        }
        else
        {
            drawable = resource.getDrawable( android.R.drawable.ic_delete );
        }
        button.setImageDrawable( drawable );
        button.setScaleType( ScaleType.FIT_CENTER );
        button.setOnClickListener( new OnClickListener()
        {
            @Override
            public void onClick( View v )
            {
                int number = mPlayerList.keyAt( rowNumber );
                String name = mPlayerList.valueAt( rowNumber );
                mPlayerList.removeAt( rowNumber );
                Toast.makeText( getActivity(), "Player No. " + number + " " + name + " removed.", Toast.LENGTH_SHORT )
                        .show();
                updateTableView();
            }
        } );
        return button;
    }

    private TextView createEditText( Context context, float weight, String text )
    {
        TextView editText = new TextView( context );
        LayoutParams layoutParams = new LayoutParams( LayoutParams.WRAP_CONTENT, getPixelHeight(), weight );
        editText.setLayoutParams( layoutParams );
        editText.setBackgroundResource( R.drawable.cell_border );
        editText.setText( text );
        return editText;
    }

    private TableRow createTableRow( int rowNumber )
    {
        TableRow row = new TableRow( getActivity() );
        row.setId( rowNumber );
        row.setLayoutParams( new LayoutParams( LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT ) );
        row.setWeightSum( 16 );
        TextView leftEditText = createEditText( getActivity(), 1, String.valueOf( mPlayerList.keyAt( rowNumber ) ) );
        TextView rightEditText = createEditText( getActivity(), 10, mPlayerList.valueAt( rowNumber ) );
        ImageButton deleteButton = createDeleteButton( rowNumber );
        row.addView( leftEditText );
        row.addView( rightEditText );
        row.addView( deleteButton );
        return row;
    }

    private int getPixelHeight()
    {
        Resources resources = getResources();
        return ( int ) TypedValue.applyDimension( TypedValue.COMPLEX_UNIT_DIP, 30, resources.getDisplayMetrics() );
    }

    private void hideKeyboard()
    {
        Activity activity = getActivity();
        InputMethodManager inputMethodManager = ( InputMethodManager ) activity
                .getSystemService( Activity.INPUT_METHOD_SERVICE );
        View focusedView = activity.getCurrentFocus();
        if( focusedView == null )
        {
            return;
        }
        inputMethodManager.hideSoftInputFromWindow( focusedView.getWindowToken(), 0 );
    }

    private void setPlayerNumberEditTextListeners( final AlertDialog dialog )
    {
        EditText playerNumberText = ( EditText ) dialog.findViewById( R.id.player_number_editText );
        playerNumberText.addTextChangedListener( new PlayerNumberWatcher( 0, 99 ) );
    }

    private void setPositiveButtonAction( final AlertDialog dialog )
    {
        dialog.getButton( DialogInterface.BUTTON_POSITIVE ).setOnClickListener( new OnClickListener()
        {
            @Override
            public void onClick( View v )
            {
                addNewPlayerFromDialog( dialog );
            }
        } );
    }

    private void showNewPlayerDialog()
    {
        AlertDialog dialog = buildDialog();
        dialog.getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE );
        dialog.show();
        dialog.setCanceledOnTouchOutside( false );
        setPositiveButtonAction( dialog );
        setPlayerNumberEditTextListeners( dialog );
    }
}
