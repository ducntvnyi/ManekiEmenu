package com.qslib.util;

import android.app.Activity;
import android.util.Log;

/**
 * Created by Dang on 5/11/2016.
 */
class ActivityUtils {
    private static final String TAG = ActivityUtils.class.getSimpleName();

    /**
     * @param activity
     * @return
     */
    public static boolean isFinish(Activity activity) {
        try {
            if (activity == null || activity.isFinishing()) {
                Log.e(TAG, "Activity is finished!");
                return true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }
}
