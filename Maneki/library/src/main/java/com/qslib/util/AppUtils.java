package com.qslib.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import java.util.List;

/**
 * Created by Dang on 11/25/2015.
 */
public class AppUtils {
    /**
     * get version code of app
     *
     * @param context
     * @return
     */
    public static int getVerCode(Context context) {
        int verCode = -1;

        try {
            verCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return verCode;
    }

    /**
     * get version name of app
     *
     * @param context
     * @return
     */
    public static String getVerName(Context context) {
        String verName = "";

        try {
            verName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return verName;
    }

    /**
     * check service is running
     *
     * @param context
     * @param className
     * @return
     */
    public static boolean isServiceRunning(Context context, String className) {
        boolean isRunning = false;

        try {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<RunningServiceInfo> servicesList = activityManager.getRunningServices(Integer.MAX_VALUE);

            for (RunningServiceInfo si : servicesList) {
                if (className.equals(si.service.getClassName())) {
                    isRunning = true;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return isRunning;
    }

    /**
     * stop service
     *
     * @param context
     * @param className
     * @return
     */
    public static boolean stopRunningService(Context context, String className) {
        Intent intentService = null;
        boolean ret = false;

        try {
            intentService = new Intent(context, Class.forName(className));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (intentService != null) ret = context.stopService(intentService);

        return ret;
    }

    /**
     * get to play tore to rating...
     *
     * @param activity
     */
    public static void goPlayStoreApp(Activity activity) {
        final String appPackageName = activity.getPackageName(); // getPackageName() from Context or Activity object
        try {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    /**
     * @param context
     * @param uri
     */
    public static void onpenWebView(Context context, Uri uri) {
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, uri));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
