package com.example.bballstatstrack.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.example.bballstatstrack.R;
import com.example.bballstatstrack.fragments.TeamFragment;
import com.example.bballstatstrack.listeners.DismissDialogListener;
import com.example.bballstatstrack.listeners.NewPlayerDialogListener;
import com.example.bballstatstrack.models.utils.EditTextRangeNumberWatcher;

public class NewPlayerDialog extends YesNoDialog {
    private TeamFragment mFragment;

    public NewPlayerDialog(TeamFragment fragment) {
        super(fragment.getActivity(), R.string.add_new_player);
        mFragment = fragment;
        View dialogView = View.inflate(getContext(), R.layout.dialog_new_player, null);
        setView(dialogView);
        EditText playerNumberEditText = (EditText) dialogView.findViewById(R.id.player_number_editText);
        playerNumberEditText.addTextChangedListener(new EditTextRangeNumberWatcher(0, 99));
        setTitle(R.string.add_new_player);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        setButton(Dialog.BUTTON_POSITIVE, getContext().getText(android.R.string.ok), new DismissDialogListener());
        setButton(Dialog.BUTTON_NEGATIVE, getContext().getText(android.R.string.cancel),
                new DismissDialogListener());
        setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getButton(Dialog.BUTTON_POSITIVE)
                .setOnClickListener(new NewPlayerDialogListener(mFragment, NewPlayerDialog.this));
    }
}
