package com.qslib.download.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Dang
 */
public abstract class AbstractDao<T> {
    private DbOpenHelper mHelper;

    public AbstractDao(Context context) {
        mHelper = new DbOpenHelper(context);
    }

    protected SQLiteDatabase getWritableDatabase() {
        return mHelper.getWritableDatabase();
    }

    protected SQLiteDatabase getReadableDatabase() {
        return mHelper.getReadableDatabase();
    }

    public void close() {
        mHelper.close();
    }
}
