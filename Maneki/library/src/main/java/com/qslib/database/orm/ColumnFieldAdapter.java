package com.qslib.database.orm;

import android.content.ContentValues;
import android.database.Cursor;

import com.qslib.database.orm.annotations.Column;

import java.lang.reflect.Field;

/**
 * Created by Dang on 4/4/2016.
 */
public class ColumnFieldAdapter extends FieldAdapter {
    private static final String[] EMPTY_ARRAY = new String[0];

    private String mColumnName;
    private String[] mColumnNames;
    private TypeAdapter<?> mTypeAdapter;
    private boolean mTreatNullAsDefault;
    private boolean mReadonly;

    public ColumnFieldAdapter(Field field, TypeAdapter<?> typeAdapter) {
        super(field);

        try {
            mTypeAdapter = typeAdapter;
            Column columnAnnotation = field.getAnnotation(Column.class);
            mColumnName = columnAnnotation.value();
            mColumnNames = new String[]{mColumnName};
            mTreatNullAsDefault = columnAnnotation.treatNullAsDefault();
            mReadonly = columnAnnotation.readonly();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setValueFromCursor(Cursor inCursor, Object outTarget) throws IllegalArgumentException {
        try {
            mField.set(outTarget, mTypeAdapter.fromCursor(inCursor, mColumnName));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void putValueToContentValues(Object fieldValue, ContentValues outValues) {
        try {
            boolean skipColumn = mReadonly || (mTreatNullAsDefault && fieldValue == null);
            if (!skipColumn) {
                ((TypeAdapter<Object>) mTypeAdapter).toContentValues(outValues, mColumnName, fieldValue);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public String[] getColumnNames() {
        try {
            return mColumnNames;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    @Override
    public String[] getWritableColumnNames() {
        try {
            return mReadonly ? EMPTY_ARRAY : getColumnNames();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
}