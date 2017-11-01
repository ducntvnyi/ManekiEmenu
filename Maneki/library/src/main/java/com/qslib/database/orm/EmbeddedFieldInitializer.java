package com.qslib.database.orm;

import java.lang.reflect.Field;

/**
 * Created by Dang on 4/4/2016.
 */
public class EmbeddedFieldInitializer {
    private  Field mField;
    private  DaoAdapter<Object> mDaoAdapter;

    @SuppressWarnings("unchecked")
    public EmbeddedFieldInitializer(Field field, DaoAdapter<?> daoAdapter) {
        try {
            mField = field;
            mDaoAdapter = ((DaoAdapter<Object>) daoAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initEmbeddedField(Object obj) {
        try {
            mField.set(obj, mDaoAdapter.createInstance());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}