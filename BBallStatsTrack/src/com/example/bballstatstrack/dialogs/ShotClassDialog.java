package com.example.bballstatstrack.dialogs;

import com.example.bballstatstrack.R;
import com.example.bballstatstrack.activities.GameActivity;
import com.example.bballstatstrack.listeners.ShotClassDialogListener;
import com.example.bballstatstrack.models.gameevents.GameEvent.ShotClass;
import com.example.bballstatstrack.models.gameevents.ShootEvent;
import com.example.bballstatstrack.models.gameevents.foulevents.ShootingFoulEvent;

import android.app.AlertDialog;
import android.view.View;
import android.widget.Button;

public class ShotClassDialog extends AlertDialog
{
    public ShotClassDialog( GameActivity activity, ShootEvent shootEvent, ShootingFoulEvent shootingFoulEvent )
    {
        super( activity );
        View view = View.inflate( getContext(), R.layout.dialog_shoot_class, null );
        setView( view );
        setCancelable( false );

        Button pts2 = ( Button ) view.findViewById( R.id.shoot_dialog_class_2pt );
        pts2.setOnClickListener(
                new ShotClassDialogListener( activity, ShotClassDialog.this, shootEvent, shootingFoulEvent ) );
        pts2.setTag( ShotClass.FG_2PT );

        Button pts3 = ( Button ) view.findViewById( R.id.shoot_dialog_class_3pt );
        pts3.setOnClickListener(
                new ShotClassDialogListener( activity, ShotClassDialog.this, shootEvent, shootingFoulEvent ) );
        pts3.setTag( ShotClass.FG_3PT );

        activity.startEvent();
    }
}
