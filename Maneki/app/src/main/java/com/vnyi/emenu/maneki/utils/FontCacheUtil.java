package com.vnyi.emenu.maneki.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;

import java.util.Hashtable;

/**
 * Created by Hungnd on 11/2/17.
 */

public class FontCacheUtil {

    public static final String TAG= FontCacheUtil.class.getSimpleName();

    public static final String DEFAULT_FONT_COUR = "cour.ttf";

    /** The font cache. */
    private static Hashtable<String, Typeface> fontCache = new Hashtable<String, Typeface>();

    /**
     * Load DEFAULT Font
     */
    public static void init(Context context) {
        get(DEFAULT_FONT_COUR, context);
    }

    public static Typeface get(String name, Context context) {
        Typeface tf = fontCache.get(name);
        if (tf == null) {
            try {
                tf = Typeface.createFromAsset(context.getAssets(), "fonts/" + name);
            } catch (Exception e) {
                Log.e(TAG,"Font's not found " + name + "");
                return null;
            }
            fontCache.put(name, tf);
        }else{
            Log.d(TAG,"Load font from memory: " + name + "");
        }
        return tf;
    }
}
