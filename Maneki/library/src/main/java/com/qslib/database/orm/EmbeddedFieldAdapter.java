package com.qslib.database.orm;

import android.content.ContentValues;
import android.database.Cursor;

import java.lang.reflect.Field;

/**
 * Created by Dang on 4/4/2016.
 */
public class EmbeddedFieldAdapter extends FieldAdapter {
    private DaoAdapter<Object> mDaoAdapter;

    @SuppressWarnings("unchecked")
    EmbeddedFieldAdapter(Field field, DaoAdapter<?> daoAdapter) {
        super(field);
        try {
            mDaoAdapter = ((DaoAdapter<Object>) daoAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setValueFromCursor(Cursor inCursor, Object outTarget) throws IllegalArgumentException {
        try {
            mField.set(outTarget, mDaoAdapter.fromCursor(inCursor, mDaoAdapter.createInstance()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void putValueToContentValues(Object value, ContentValues outValues) {
        try {
            mDaoAdapter.toContentValues(outValues, value);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public String[] getColumnNames() {
        try {
            return mDaoAdapter.getProjection();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    @Override
    public String[] getWritableColumnNames() {
        try {
            return mDaoAdapter.getWritableColumns();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
}