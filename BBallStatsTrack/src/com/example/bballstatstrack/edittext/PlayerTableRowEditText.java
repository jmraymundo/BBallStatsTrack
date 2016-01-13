package com.example.bballstatstrack.edittext;

import com.example.bballstatstrack.R;
import com.example.bballstatstrack.models.utils.DimensionUtils;

import android.content.Context;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

public class PlayerTableRowEditText extends TextView
{
    public PlayerTableRowEditText( Context context, float weight, String text )
    {
        super( context );
        LayoutParams layoutParams = new LayoutParams( LayoutParams.WRAP_CONTENT,
                DimensionUtils.getPixelHeight( getResources() ), weight );
        setLayoutParams( layoutParams );
        setBackgroundResource( R.drawable.cell_border );
        setText( text );
    }
}
