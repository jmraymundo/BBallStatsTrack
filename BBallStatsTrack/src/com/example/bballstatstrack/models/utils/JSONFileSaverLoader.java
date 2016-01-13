package com.example.bballstatstrack.models.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import org.json.JSONException;

import com.example.bballstatstrack.models.Game;

import android.content.Context;
import android.os.Environment;
import android.util.JsonReader;
import android.util.JsonWriter;

public class JSONFileSaverLoader
{
    private Context mContext;

    private String mFilename;

    public JSONFileSaverLoader( Context context, String filename )
    {
        mContext = context;
        mFilename = filename;
    }

    public ArrayList< Game > loadGames() throws IOException, JSONException
    {
        ArrayList< Game > games = new ArrayList< Game >();
        InputStream in = mContext.openFileInput( mFilename );
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader( in ) );
        JsonReader reader = new JsonReader( bufferedReader );
        reader.beginArray();
        while( reader.hasNext() )
        {
            games.add( JSONDeserializer.readGame( reader ) );
        }
        reader.endArray();
        reader.close();
        return games;
    }

    public void saveGames( ArrayList< Game > games ) throws JSONException, IOException
    {
        OutputStream out = mContext.openFileOutput( mFilename, Context.MODE_PRIVATE );
        OutputStreamWriter outStreamWriter = new OutputStreamWriter( out );
        JsonWriter writer = new JsonWriter( outStreamWriter );
        writer.beginArray();
        for( Game c : games )
        {
            JSONSerializer.writeGame( writer, c );
        }
        writer.endArray();
        writer.close();
    }
}
