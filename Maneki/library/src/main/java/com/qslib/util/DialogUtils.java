package com.qslib.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface.OnClickListener;

@SuppressWarnings("ALL")
public class DialogUtils {
    /**
     * @param context
     * @param title
     * @param message
     * @param labelActionPosition
     */
    public static void messageBox(Activity context, String title,
                                  String message, String labelActionPosition) {
        messageBox(context, 0, title, message, labelActionPosition);
    }

    /**
     * @param context
     * @param title
     * @param message
     * @param labelActionPosition
     */
    public static void messageBox(Activity context, int iconId, String title,
                                  String message, String labelActionPosition) {
        try {
            if (ActivityUtils.isFinish(context)) return;
            AlertDialog.Builder builder = new AlertDialog.Builder(context)
                    .setTitle(title)
                    .setMessage(message)
                    .setPositiveButton(labelActionPosition,
                            (dialog, which) -> {
                                dialog.dismiss();
                            }).setCancelable(false);

            // set icon notification
            if (iconId > 0) builder.setIcon(iconId);
            builder.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param context
     * @param title
     * @param message
     * @param labelActionPosition
     * @param callbackActionPosition
     */
    public static void messageBox(Activity context, String title,
                                  String message, String labelActionPosition,
                                  OnClickListener callbackActionPosition) {
        messageBox(context, 0, title, message, labelActionPosition, callbackActionPosition);
    }

    /**
     * @param context
     * @param title
     * @param message
     * @param labelActionPosition
     * @param callbackActionPosition
     */
    public static void messageBox(Activity context, int iconId, String title,
                                  String message, String labelActionPosition,
                                  OnClickListener callbackActionPosition) {
        try {
            if (ActivityUtils.isFinish(context)) return;
            AlertDialog.Builder builder = new AlertDialog.Builder(context)
                    .setTitle(title)
                    .setMessage(message)
                    .setPositiveButton(labelActionPosition,
                            callbackActionPosition).setCancelable(false);

            // set icon notification
            if (iconId > 0) builder.setIcon(iconId);
            builder.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * @param context
     * @param title
     * @param message
     * @param labelActionPosition
     * @param labelActionCancel
     * @param callbackActionPosition
     */
    public static void confirmBox(Activity context, String title,
                                  String message, String labelActionPosition,
                                  String labelActionCancel,
                                  OnClickListener callbackActionPosition) {
        confirmBox(context, 0, title, message, labelActionPosition, labelActionCancel, callbackActionPosition);
    }

    /**
     * @param context
     * @param title
     * @param message
     * @param labelActionPosition
     * @param labelActionCancel
     * @param callbackActionPosition
     */
    public static void confirmBox(Activity context, int iconId, String title,
                                  String message, String labelActionPosition,
                                  String labelActionCancel,
                                  OnClickListener callbackActionPosition) {
        try {
            if (ActivityUtils.isFinish(context)) return;
            AlertDialog.Builder builder = new AlertDialog.Builder(context)
                    .setTitle(title)
                    .setMessage(message)
                    .setPositiveButton(labelActionPosition,
                            callbackActionPosition)
                    .setNegativeButton(labelActionCancel,
                            (dialog, which) -> {
                                dialog.dismiss();
                            }).setCancelable(false);

            // set icon notification
            if (iconId > 0) builder.setIcon(iconId);
            builder.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param context
     * @param title
     * @param message
     * @param labelActionPosition
     * @param labelActionNegative
     * @param callback
     * @param cancel
     */
    public static void confirmBox(Activity context, String title,
                                  String message, String labelActionPosition,
                                  String labelActionNegative,
                                  OnClickListener callback,
                                  OnClickListener cancel) {
        confirmBox(context, 0, title, message, labelActionPosition, labelActionNegative, callback, cancel);
    }

    /**
     * @param context
     * @param title
     * @param message
     * @param labelActionPosition
     * @param labelActionNegative
     * @param callback
     * @param cancel
     */
    public static void confirmBox(Activity context, int iconId, String title,
                                  String message, String labelActionPosition,
                                  String labelActionNegative,
                                  OnClickListener callback,
                                  OnClickListener cancel) {
        try {
            if (ActivityUtils.isFinish(context)) return;
            AlertDialog.Builder builder = new AlertDialog.Builder(context)
                    .setTitle(title).setMessage(message)
                    .setPositiveButton(labelActionPosition, callback)
                    .setNegativeButton(labelActionNegative, cancel)
                    .setCancelable(false);

            // set icon notification
            if (iconId > 0) builder.setIcon(iconId);
            builder.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
