package com.qslib.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

/**
 * Created by Dang on 11/5/2015.
 */
public class BroadcastManagerUtils {
    /**
     * register broadcast receiver
     *
     * @param context
     * @param broadcastReceiver
     * @param intentFilter
     */
    public static void registerReceiver(Context context, BroadcastReceiver broadcastReceiver, IntentFilter intentFilter) {
        try {
            LocalBroadcastManager.getInstance(context).registerReceiver(broadcastReceiver, intentFilter);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * unregister broadcast receiver
     *
     * @param context
     * @param broadcastReceiver
     */
    public static void unregisterReceiver(Context context, BroadcastReceiver broadcastReceiver) {
        try {
            LocalBroadcastManager.getInstance(context).unregisterReceiver(broadcastReceiver);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * send broad cast
     *
     * @param context
     * @param intent
     */
    public static void sendBroadcast(Context context, Intent intent) {
        try {
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
