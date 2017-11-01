package com.vnyi.emenu.maneki.utils;

import android.content.Context;
import android.util.Log;

import com.qslib.sharepreferences.AppPreferences;
import com.qslib.util.StringUtils;
import com.vnyi.emenu.maneki.services.ManekiApiServices;

/**
 * Created by Hungnd on 11/1/17.
 */

public class ManekiUtils {

    private static final String TAG = ManekiUtils.class.getSimpleName();

    /**
     * get language code from cache
     *
     * @param context
     * @return
     */
    public static String getLanguageApp(Context context) {
        int langId = AppPreferences.getInstance(context).getInt(ManekiApiServices.LANG_ID);
        String language = getLanguage(langId);
        Log.d(TAG, "==> ManekiUtils langId::" + langId);
        Log.d(TAG, "==> ManekiUtils language::" + language);
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
}
