package com.qslib.util;

import android.app.ActivityManager;
import android.content.Context;

/**
 * Created by hungnd on 08/24/2017.
 */

public class ServiceUtils {

    public static boolean isMyServiceRunning(Context context, Class<?> serviceClass) {

        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

}
