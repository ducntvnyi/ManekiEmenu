package com.qslib.sharepreferences;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.qslib.jackson.JacksonUtils;
import com.qslib.util.StringUtils;

import java.util.List;

public class AppPreferences {
    // instance
    private static AppPreferences instance = null;

    // variable
    private SharedPreferences appSharedPrefs = null;
    private Editor prefsEditor = null;

    /**
     * getInstance
     *
     * @param context
     */
    public static AppPreferences getInstance(Context context) {
        if (instance == null) instance = new AppPreferences(context);
        return instance;
    }

    /**
     * @param context
     */
    @SuppressLint("CommitPrefEdits")
    public AppPreferences(Context context) {
        try {
            appSharedPrefs = context.getSharedPreferences(context.getPackageName(),
                    Context.MODE_PRIVATE);
            prefsEditor = appSharedPrefs.edit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * put value int
     *
     * @param key
     * @param value
     */
    public AppPreferences putInt(String key, int value) {
        try {
            prefsEditor.putInt(key, value);
            prefsEditor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    /**
     * get value int
     *
     * @param key
     * @return
     */
    public int getInt(String key) {
        try {
            return appSharedPrefs.getInt(key, 0);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * put value long
     *
     * @param key
     * @param value
     */
    public AppPreferences putLong(String key, long value) {
        try {
            prefsEditor.putLong(key, value);
            prefsEditor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    /**
     * get value long
     *
     * @param key
     * @return
     */
    public long getLong(String key) {
        try {
            return appSharedPrefs.getLong(key, 0);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * put value boolean
     *
     * @param key
     * @param value
     */
    public AppPreferences putBoolean(String key, boolean value) {
        try {
            prefsEditor.putBoolean(key, value);
            prefsEditor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    /**
     * get value boolean
     *
     * @param key
     * @return
     */
    public boolean getBoolean(String key) {
        try {
            return appSharedPrefs.getBoolean(key, false);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * put value String
     *
     * @param key
     * @param value
     */
    public AppPreferences putString(String key, String value) {
        try {
            prefsEditor.putString(key, value);
            prefsEditor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    /**
     * get value String
     *
     * @param key
     * @return
     */
    public String getString(String key) {
        try {
            return appSharedPrefs.getString(key, null);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * put object
     *
     * @param key
     * @param object
     * @param <T>
     * @return
     */
    public <T> AppPreferences putObject(String key, T object) {
        try {
            prefsEditor.putString(key, JacksonUtils.writeValueToString(object));
            prefsEditor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    /**
     * get object
     *
     * @param key
     * @param typeReference
     * @param <T>
     * @return
     */
    public <T> T getObject(String key, TypeReference<T> typeReference) {
        try {
            String value = appSharedPrefs.getString(key, null);
            if (StringUtils.isEmpty(value)) return null;

            return JacksonUtils.convertJsonToObject(value, typeReference);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T getObject(String key, Class<T> clazz) {
        try {
            String value = appSharedPrefs.getString(key, null);
            if (StringUtils.isEmpty(value)) return null;

            return JacksonUtils.convertJsonToObject(value, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * get list object
     *
     * @param key
     * @param typeReference
     * @param <T>
     * @return
     */
    public <T> List<T> getListObject(String key, TypeReference<List<T>> typeReference) {
        try {
            String value = appSharedPrefs.getString(key, null);
            if (StringUtils.isEmpty(value)) return null;

            return JacksonUtils.convertJsonToObject(value, typeReference);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    public <T> AppPreferences putListObject(String key, List<T> object) {
        try {
            prefsEditor.putString(key, JacksonUtils.writeValueToString(object));
            prefsEditor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    /**
     * clear all cache in SharedPreference
     */
    public AppPreferences clearCache() {
        try {
            prefsEditor.clear().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }
}
