package com.btxdev.tmdb.tools;

import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

public class Util {

    public static PointF getScreenSizeDp(Context context){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        return new PointF(dpWidth, dpHeight);
    }

    public static Point getScreenSizePx(Context context){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return new Point(displayMetrics.widthPixels, displayMetrics.heightPixels);
    }


    public static void setViewSizeDp(Context context, View view, float width, float height){
        float wPixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, context.getResources().getDisplayMetrics());
        float hPixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, height, context.getResources().getDisplayMetrics());
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = (int) wPixels;
        params.height = (int) hPixels;
        view.setLayoutParams(params);
    }
}
