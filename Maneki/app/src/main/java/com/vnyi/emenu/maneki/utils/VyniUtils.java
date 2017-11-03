package com.vnyi.emenu.maneki.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.qslib.logger.Logger;
import com.qslib.permission.PermissionUtils;
import com.qslib.sharepreferences.AppPreferences;
import com.qslib.util.StringUtils;
import com.vnyi.emenu.maneki.services.VnyiApiServices;

/**
 * Created by Hungnd on 11/1/17.
 */

public class VyniUtils {

    private static final String TAG = "Maneki";

    /**
     * get language code from cache
     *
     * @param context
     * @return
     */
    public static String getLanguageApp(Context context) {
        int langId = AppPreferences.getInstance(context).getInt(VnyiApiServices.LANG_ID);
        String language = getLanguage(langId);
        Log.d(TAG, "==> VyniUtils langId::" + langId);
        Log.d(TAG, "==> VyniUtils language::" + language);
        if (StringUtils.isEmpty(language)) language = Constant.LOCALE_VI;
        return language;
    }

    /**
     * get language code from langId
     *
     * @param langId
     * @return
     */
    private static String getLanguage(int langId) {
        if (langId == 1) {
            return Constant.LOCALE_VI;
        } else if (langId == 2) {
            return Constant.LOCALE_EN;
        } else if (langId == 11) {
            return Constant.LOCALE_JP;
        }
        return "";
    }

    /**
     * log error message to file, server
     *
     * @param tag
     * @param ex
     */
    public static void LogException(String tag, Exception ex) {
        try {
            // log to file
            Logger.e(tag, ex);
            // TODO: log to server
        } catch (Exception e) {
            LogException(TAG, e);
        }
    }

    /**
     * @param tag
     * @param ex
     */
    public static void LogException(String tag, String ex) {
        try {
            // log to file
            Logger.e(tag, ex);
            // TODO: log to server
        } catch (Exception e) {
            LogException(TAG, e);
        }
    }

    /**
     *  request permission
     *
     * @param activity
     * @return
     */
    public static boolean requestPermission(Activity activity) {
        String[] perms = {android.Manifest.permission.INTERNET, android.Manifest.permission.ACCESS_NETWORK_STATE,
                android.Manifest.permission.ACCESS_WIFI_STATE, android.Manifest.permission.CHANGE_NETWORK_STATE,
                android.Manifest.permission.CHANGE_WIFI_STATE, Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};
        return PermissionUtils.requestPermission(activity, PermissionUtils.REQUEST_CODE_PERMISSION, perms);
    }


}
