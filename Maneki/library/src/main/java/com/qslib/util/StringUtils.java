package com.qslib.util;

import android.annotation.SuppressLint;
import android.text.TextUtils;

public class StringUtils {
    /**
     * check string empty, null...
     *
     * @param value
     * @return true or false
     */
    @SuppressLint("DefaultLocale")
    public static boolean isEmpty(String value) {
        try {
            if (value == null || value.equals("") || TextUtils.isEmpty(value)
                    || value.equalsIgnoreCase("null")) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
