package com.example.bballstatstrack.activities;

import java.util.ArrayList;

import com.example.bballstatstrack.R;
import com.example.bballstatstrack.adapter.GameAdapter;
import com.example.bballstatstrack.model.GameDirectory;
import com.example.bballstatstrack.models.Game;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;

public class ListGamesFragment extends ListFragment
{
    private ArrayList< Game > mGames;

    @Override
    public void onActivityResult( int requestCode, int resultCode, Intent data )
    {
        ( ( GameAdapter ) getListAdapter() ).notifyDataSetChanged();
    }

    @Override
    public void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setHasOptionsMenu( true );
        mGames = GameDirectory.get( getActivity() ).getGames();
        GameAdapter adapter = new GameAdapter( mGames, getActivity() );
        setListAdapter( adapter );
    }

    @Override
    public void onCreateOptionsMenu( Menu menu, MenuInflater inflater )
    {
        super.onCreateOptionsMenu( menu, inflater );
        inflater.inflate( R.menu.list_games, menu );
    }

    public void onListItemClick( ListView l, View v, int position, long id )
    {
        // get the Game from the adapter
        Game c = ( ( GameAdapter ) getListAdapter() ).getItem( position );
        // start an instance of GameReviewActivity
        Intent i = new Intent( getActivity(), GameReviewActivity.class );
        i.putExtra( GameActivity.EXTRA_GAME_ID, c.getId() );
        startActivityForResult( i, 0 );
    }

    @Override
    public boolean onContextItemSelected( MenuItem item )
    {
        AdapterContextMenuInfo info = ( AdapterContextMenuInfo ) item.getMenuInfo();
        int position = info.position;
        GameAdapter adapter = ( GameAdapter ) getListAdapter();
        Game game = adapter.getItem( position );

        switch( item.getItemId() )
        {
            case R.id.menu_item_delete_game:
                GameDirectory.get( getActivity() ).deleteGame( game );
                adapter.notifyDataSetChanged();
                return true;
        }
        return super.onContextItemSelected( item );
    }

    public boolean onOptionsItemSelected( MenuItem item )
    {
        switch( item.getItemId() )
        {
            case R.id.menu_item_new_game:
                Intent i = new Intent( getActivity(), NewTeamActivity.class );
                startActivityForResult( i, 0 );
                return true;
            default:
                return super.onOptionsItemSelected( item );
        }
    }
}
