package com.qslib.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;

import java.util.List;

@SuppressWarnings("ALL")
public class DeviceUtils {
    private static final float ALPHA_DIM_VALUE = 0.1f;

    /**
     * get device id
     *
     * @param act
     * @return device id
     */
    public static String getDeviceId(Context act) {
        String deviceId = "";

        try {
            TelephonyManager tm = (TelephonyManager) act
                    .getSystemService(Context.TELEPHONY_SERVICE);
            deviceId = tm.getDeviceId();

            if (TextUtils.isEmpty(deviceId)) {
                deviceId = tm.getSimSerialNumber();

                if (TextUtils.isEmpty(deviceId)) {
                    deviceId = android.provider.Settings.Secure.getString(
                            act.getContentResolver(),
                            android.provider.Settings.Secure.ANDROID_ID);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return deviceId;
    }

    /**
     * check device support camera
     *
     * @param context
     * @return
     */
    public static boolean isDeviceSupportCamera(Context context) {
        try {
            if (context.getPackageManager().hasSystemFeature(
                    PackageManager.FEATURE_CAMERA)) {
                return true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    /**
     * check device has flash
     *
     * @param camera
     * @return
     */
    public static boolean deviceCameraHasFlash(Camera camera) {
        try {
            if (camera == null) return false;

            Camera.Parameters parameters = camera.getParameters();
            if (parameters.getFlashMode() == null) return false;

            List<String> supportedFlashModes = parameters
                    .getSupportedFlashModes();
            return !(supportedFlashModes == null
                    || supportedFlashModes.isEmpty()
                    || supportedFlashModes.size() == 1
                    && supportedFlashModes.get(0).equals(
                    Camera.Parameters.FLASH_MODE_OFF));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * don't rotate screen
     *
     * @param activity
     */
    public static void lockScreenOrientation(Activity activity) {
        try {
            int currentOrientation = activity.getResources().getConfiguration().orientation;
            if (currentOrientation == Configuration.ORIENTATION_PORTRAIT) {
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            } else {
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param activity
     */
    public static void unlockScreenOrientation(Activity activity) {
        try {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @return isHoneycombOrAbove
     */
    public static boolean isHoneycombOrAbove() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    /**
     * @param view
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void dimView(View view) {
        try {
            if (isHoneycombOrAbove()) {
                view.setAlpha(ALPHA_DIM_VALUE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param alpha
     * @param views
     */
    public static void setAlpha(float alpha, View... views) {
        try {
            if (isHoneycombOrAbove()) {
                for (View view : views) {
                    view.setAlpha(alpha);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
