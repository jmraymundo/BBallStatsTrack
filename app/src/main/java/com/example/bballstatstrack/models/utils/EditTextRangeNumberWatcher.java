package com.example.bballstatstrack.models.utils;

import android.text.Editable;
import android.text.TextWatcher;

public class EditTextRangeNumberWatcher implements TextWatcher {
    private final int MIN;
    private final int MAX;
    String mCurrent = "";

    public EditTextRangeNumberWatcher(int min, int max) {
        MIN = min;
        MAX = max;
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s == null || s.length() == 0) {
            return;
        }
        int input = Integer.parseInt(s.toString());
        if (s.length() < 3 && inRange(input)) {
            return;
        }
        s.replace(0, s.length(), mCurrent);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        if (s == null || s.length() == 0) {
            mCurrent = "";
            return;
        }
        mCurrent = s.toString();
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // TODO Auto-generated method stub
    }

    private boolean inRange(int input) {
        return MIN <= input && input <= MAX;
    }
}