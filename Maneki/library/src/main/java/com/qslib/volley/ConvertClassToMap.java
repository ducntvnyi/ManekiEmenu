package com.qslib.volley;

import android.util.Log;

import com.qslib.util.StringUtils;
import com.qslib.volley.annotations.ColumnProperty;
import com.qslib.volley.annotations.FileProperty;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dang on 5/16/2016.
 */
@SuppressWarnings("ALL")
public class ConvertClassToMap {
    /**
     * convert object to Map<String, String>
     *
     * @param object
     * @return
     */
    public static Map<String, String> convertToMapString(Object object) {
        Map<String, String> result = new HashMap<>();

        try {
            Field[] declaredFields = object.getClass().getDeclaredFields();
            if (declaredFields == null || declaredFields.length <= 0) return result;

            for (Field field : declaredFields) {
                // allow to receive value of field
                field.setAccessible(true);

                // check exists annotation Ignore, File
                if (!field.isAnnotationPresent(ColumnProperty.class)) continue;

                // get value of property
                Object value = field.get(object);
                if (value == null) continue;

                // get field name
                String fieldName = field.getName();

                // check label of property
                ColumnProperty columnProperty = field.getAnnotation(ColumnProperty.class);
                if (columnProperty != null && !StringUtils.isEmpty(columnProperty.value()))
                    fieldName = columnProperty.value();

                result.put(fieldName, String.valueOf(value));
            }

            Log.e("ObjectToMapString", "ObjectToMapString:: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * convert object to Map<String, File>
     *
     * @param object
     * @return
     */
    public static Map<String, java.io.File> convertToMapFile(Object object) {
        Map<String, File> result = new HashMap<>();

        try {
            Field[] declaredFields = object.getClass().getDeclaredFields();
            if (declaredFields == null || declaredFields.length <= 0) return result;

            for (Field field : declaredFields) {
                // allow to receive value of field
                field.setAccessible(true);

                // check exists annotation Ignore, File
                if (!field.isAnnotationPresent(FileProperty.class)) continue;

                // get value of property
                String value = String.valueOf(field.get(object));
                if (StringUtils.isEmpty(value)) continue;

                // get field name
                String fieldName = field.getName();

                // check label of property
                FileProperty fileProperty = field.getAnnotation(FileProperty.class);
                if (fileProperty != null && !StringUtils.isEmpty(fileProperty.value()))
                    fieldName = fileProperty.value();

                File file = new File(value);
                if (!file.exists()) continue;

                result.put(fieldName, new File(value));
            }

            Log.e("ObjectToMapFile", "ObjectToMapFile:: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
