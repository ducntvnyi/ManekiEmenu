package com.qslib.database.orm;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by Dang on 4/4/2016.
 */
public class OptionalTypeAdapter<T> implements TypeAdapter<T> {

    private final TypeAdapter<T> mWrappedAdapter;

    public OptionalTypeAdapter(TypeAdapter<T> wrappedAdapter) {
        mWrappedAdapter = wrappedAdapter;
    }

    @Override
    public T fromCursor(Cursor c, String columnName) {
        try {
            return c.isNull(c.getColumnIndexOrThrow(columnName))
                    ? null
                    : mWrappedAdapter.fromCursor(c, columnName);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    @Override
    public void toContentValues(ContentValues values, String columnName, T object) {
        try {
            if (object != null) {
                mWrappedAdapter.toContentValues(values, columnName, object);
            } else {
                values.putNull(columnName);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}