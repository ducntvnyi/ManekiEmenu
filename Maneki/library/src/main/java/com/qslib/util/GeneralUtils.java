package com.qslib.util;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;

public class GeneralUtils {
    /**
     * convertObjectToMapString dp to pixel
     *
     * @param context
     * @param demenId
     * @return Pixel
     */
    public static int convertDpMeansureToPixel(Context context, int demenId) {
        Resources r;
        int px = 0;

        try {
            r = context.getResources();
            px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    r.getDimension(demenId), r.getDisplayMetrics());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return px;
    }

    /**
     * the check device is tablet or phone
     *
     * @param context
     * @return
     */
    public static boolean isTablet(Context context) {
        try {
            return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    /**
     * @param context
     * @return DisplayMetrics
     */
    public static DisplayMetrics getDisplayMetrics(Context context) {
        WindowManager windowManager;
        DisplayMetrics displayMetrics;

        try {
            displayMetrics = new DisplayMetrics();
            windowManager = (WindowManager) context
                    .getSystemService(Context.WINDOW_SERVICE);
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        } catch (Exception e) {
            e.printStackTrace();
            displayMetrics = null;
        }

        return displayMetrics;
    }

    /**
     * @param context
     * @return Configuration.ORIENTATION_SQUARE,
     * Configuration.ORIENTATION_PORTRAIT,
     * Configuration.ORIENTATION_LANDSCAPE
     */
    @SuppressWarnings("deprecation")
    public static int getScreenOrientation(Context context) {
        int orientation = Configuration.ORIENTATION_UNDEFINED;

        try {
            DisplayMetrics metrics = getDisplayMetrics(context);
            if (metrics.widthPixels == metrics.heightPixels) {
                orientation = Configuration.ORIENTATION_SQUARE;
            } else {
                if (metrics.widthPixels < metrics.heightPixels) {
                    orientation = Configuration.ORIENTATION_PORTRAIT;
                } else {
                    orientation = Configuration.ORIENTATION_LANDSCAPE;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return orientation;
    }
}
