package com.qslib.database.orm;

import com.google.common.collect.Lists;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

/**
 * Created by Dang on 4/4/2016.
 */
public final class Fields {
    private Fields() {
    }

    public static List<Field> allFieldsIncludingPrivateAndSuper(Class<?> tClass) {
        try {
            List<Field> fields = Lists.newArrayList();
            while (!tClass.equals(Object.class)) {
                Collections.addAll(fields, tClass.getDeclaredFields());
                tClass = tClass.getSuperclass();
            }

            return fields;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
