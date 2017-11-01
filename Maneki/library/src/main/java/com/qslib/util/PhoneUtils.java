package com.qslib.util;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;

/**
 * Created by Dang on 12/30/2015.
 */
public class PhoneUtils {
    /**
     * go to call screen of phone
     *
     * @param context
     * @param phoneNumber
     */
    public static void call(Context context, String phoneNumber) {
        try {
            if (StringUtils.isEmpty(phoneNumber)) return;

            // check permission for location with android
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                    ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }

            Intent intent = new Intent(Intent.ACTION_CALL);
            String phNum = "tel:" + phoneNumber;
            intent.setData(Uri.parse(phNum));
            context.startActivity(intent);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * check device has support
     *
     * @param context
     * @return
     */
    public static boolean isTelephonyEnabled(Context context) {
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return tm != null && tm.getSimState() == TelephonyManager.SIM_STATE_READY;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }
}
