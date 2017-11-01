package com.qslib.database.orm;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by Dang on 4/4/2016.
 */
public final class TypeAdapters {
    private TypeAdapters() {
    }

    public static class StringAdapter implements TypeAdapter<String> {
        @Override
        public String fromCursor(Cursor c, String columnName) {
            try {
                return c.getString(c.getColumnIndexOrThrow(columnName));
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return null;
        }

        @Override
        public void toContentValues(ContentValues values, String columnName, String object) {
            try {
                values.put(columnName, object);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static class ShortAdapter implements TypeAdapter<Short> {
        @Override
        public Short fromCursor(Cursor c, String columnName) {
            try {
                return c.getShort(c.getColumnIndexOrThrow(columnName));
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return null;
        }

        @Override
        public void toContentValues(ContentValues values, String columnName, Short object) {
            try {
                values.put(columnName, object);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static class IntegerAdapter implements TypeAdapter<Integer> {
        @Override
        public Integer fromCursor(Cursor c, String columnName) {
            try {
                return c.getInt(c.getColumnIndexOrThrow(columnName));
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return null;
        }

        @Override
        public void toContentValues(ContentValues values, String columnName, Integer object) {
            try {
                values.put(columnName, object);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static class LongAdapter implements TypeAdapter<Long> {
        @Override
        public Long fromCursor(Cursor c, String columnName) {
            try {
                return c.getLong(c.getColumnIndexOrThrow(columnName));
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return null;
        }

        @Override
        public void toContentValues(ContentValues values, String columnName, Long object) {
            try {
                values.put(columnName, object);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static class FloatAdapter implements TypeAdapter<Float> {
        @Override
        public Float fromCursor(Cursor c, String columnName) {
            try {
                return c.getFloat(c.getColumnIndexOrThrow(columnName));
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return null;
        }

        @Override
        public void toContentValues(ContentValues values, String columnName, Float object) {
            try {
                values.put(columnName, object);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static class DoubleAdapter implements TypeAdapter<Double> {
        @Override
        public Double fromCursor(Cursor c, String columnName) {
            try {
                return c.getDouble(c.getColumnIndexOrThrow(columnName));
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return null;
        }

        @Override
        public void toContentValues(ContentValues values, String columnName, Double object) {
            try {
                values.put(columnName, object);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static class BooleanAdapter implements TypeAdapter<Boolean> {
        @Override
        public Boolean fromCursor(Cursor c, String columnName) {
            try {
                return c.getInt(c.getColumnIndexOrThrow(columnName)) > 0;
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return false;
        }

        @Override
        public void toContentValues(ContentValues values, String columnName, Boolean object) {
            try {
                values.put(columnName, object);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}