package com.qslib.util;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

public class LanguageUtils {
    /**
     * change language for app
     *
     * @param activity
     * @param languageCode
     */
    public static void configLanguage(Activity activity, String languageCode) {
        try {
            Resources res = activity.getResources();
            // Change locale settings in the app.
            Configuration conf = res.getConfiguration();
            conf.locale = new Locale(languageCode);
            res.updateConfiguration(conf, res.getDisplayMetrics());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
