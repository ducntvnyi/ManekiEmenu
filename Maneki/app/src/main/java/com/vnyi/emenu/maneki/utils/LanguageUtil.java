package com.vnyi.emenu.maneki.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;


import com.vnyi.emenu.maneki.activities.MainActivity;
import com.vnyi.emenu.maneki.applications.VnyiApplication;

import java.util.Locale;

/**
 * Created by turbo on 2017/2/16.
 */

public class LanguageUtil {
    public static void changeLanguageType(Context context, Locale localelanguage) {
        Resources resources = VnyiApplication.getInstance().getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            config.setLocale(localelanguage);
        } else {
            config.locale = localelanguage;
            resources.updateConfiguration(config, dm);
        }

//        Intent refresh = new Intent(context, MainPedometerActivity.class);
        Intent refresh = new Intent(context, MainActivity.class);
        context.startActivity(refresh);
        ((Activity) context).finish();
    }

    public static Locale getLanguageType(Context context) {
        Resources resources = VnyiApplication.getInstance().getResources();
        Configuration config = resources.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return config.getLocales().get(0);
        } else {
            return config.locale;
        }
    }
}
