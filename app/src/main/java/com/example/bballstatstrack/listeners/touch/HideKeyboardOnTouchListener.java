package com.example.bballstatstrack.listeners.touch;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;

public class HideKeyboardOnTouchListener implements OnTouchListener {
    private Activity mActivity;

    public HideKeyboardOnTouchListener(Activity activity) {
        mActivity = activity;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        hideKeyboard();
        return false;
    }

    private void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) mActivity
                .getSystemService(Activity.INPUT_METHOD_SERVICE);
        View focusedView = mActivity.getCurrentFocus();
        if (focusedView == null) {
            return;
        }
        inputMethodManager.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
    }
}