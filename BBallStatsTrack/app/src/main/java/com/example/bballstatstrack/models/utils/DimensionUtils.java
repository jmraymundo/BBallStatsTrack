package com.example.bballstatstrack.models.utils;

import android.content.res.Resources;
import android.util.TypedValue;

public class DimensionUtils {
    public static int getPixelHeight(Resources resources) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, resources.getDisplayMetrics());
    }
}
