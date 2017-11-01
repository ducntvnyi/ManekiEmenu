package com.qslib.textstyle;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;

import java.util.Hashtable;

public class Typefaces {
    private static final String TAG = Typefaces.class.getSimpleName();
    private static final Hashtable<String, Typeface> caches = new Hashtable<>();

    /**
     * get typeface
     *
     * @param context
     * @param assetPath
     * @return
     */
    public static Typeface get(Context context, String assetPath) {
        try {
            synchronized (caches) {
                if (!caches.containsKey(assetPath)) {
                    try {
                        Typeface tf = Typeface.createFromAsset(context.getAssets(),
                                assetPath);
                        caches.put(assetPath, tf);
                    } catch (Exception e) {
                        Log.e(TAG, "Could not get typeface '" + assetPath + "' because " + e.getMessage());
                        return null;
                    }
                }

                return caches.get(assetPath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
