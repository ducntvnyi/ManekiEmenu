package com.qslib.util;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.qslib.library.R;

public class ToastUtils {

    public static void showToastNetworkUnAvailable(Context context) {
        try {
            Toast toast = Toast.makeText(context, context.getText(R.string.network_unavailable), Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param context
     * @param message
     */
    public static void showToast(Context context, String message) {
        try {
            Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param context
     * @param message
     * @param gravity
     */
    public static void showToast(Context context, String message, int gravity) {
        try {
            Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
            toast.setGravity(gravity, 0, 0);
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showToast(Context context, String message, int gravity,
                                 int xOffset, int yOffset) {
        try {
            Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
            toast.setGravity(gravity, xOffset, yOffset);
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param context
     * @param view
     */
    public static void showToast(Context context, View view) {
        try {
            Toast toast = new Toast(context);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(view);
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param context
     * @param view
     * @param gravity
     */
    public static void showToast(Context context, View view, int gravity) {
        try {
            Toast toast = new Toast(context);
            toast.setGravity(gravity, 0, 0);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(view);
            toast.setMargin(0, -GeneralUtils.getStatusBarHeight(context));
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
