package com.example.bballstatstrack.buttons;

import com.example.bballstatstrack.R;
import com.example.bballstatstrack.fragments.TeamFragment;
import com.example.bballstatstrack.listeners.DeleteButtonOnClickListener;
import com.example.bballstatstrack.models.utils.DimensionUtils;

import android.annotation.TargetApi;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.widget.ImageButton;
import android.widget.TableRow.LayoutParams;

public class DeletePlayerButton extends ImageButton
{

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressWarnings("deprecation")
    public DeletePlayerButton( TeamFragment fragment, int rowNumber )
    {
        super( fragment.getActivity() );
        setLayoutParams( new LayoutParams( LayoutParams.WRAP_CONTENT,
                DimensionUtils.getPixelHeight( fragment.getResources() ), 5 ) );
        setBackgroundResource( R.drawable.cell_border );
        Resources resource = getResources();
        Drawable drawable;
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP )
        {
            drawable = resource.getDrawable( android.R.drawable.ic_delete, fragment.getActivity().getTheme() );
        }
        else
        {
            drawable = resource.getDrawable( android.R.drawable.ic_delete );
        }
        setImageDrawable( drawable );
        setScaleType( ScaleType.FIT_CENTER );
        setOnClickListener( new DeleteButtonOnClickListener( fragment, rowNumber ) );
    }

}
