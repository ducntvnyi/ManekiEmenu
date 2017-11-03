package com.qslib.util;

import android.app.Activity;
import android.util.Log;

import com.qslib.library.R;

/**
 * Created by Dang on 5/11/2016.
 */
public class ActivityUtils {
    private static final String TAG = ActivityUtils.class.getSimpleName();

    /**
     * @param activity
     * @return
     */
    public static boolean isFinish(Activity activity) {
        try {
            if (activity == null || activity.isFinishing()) {
                Log.d(TAG, "Activity is finished!");
                return true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    /**
     * Intent intent = new Intent(mContext, Account.class);
     * intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
     * intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
     * mContext.startActivity(intent);
     * <p>
     * Util.animationActivity(mContext, false);
     *
     * @param activity
     */
    public static void animationActivity(Activity activity) {
        activity.overridePendingTransition(R.anim.activity_open_right_to_left,
                R.anim.activity_close_right_to_left);
    }
}
