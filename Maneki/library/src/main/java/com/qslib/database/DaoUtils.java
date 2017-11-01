package com.qslib.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

@SuppressWarnings("ALL")
public class DaoUtils {
    private static SQLiteDatabase db = null;

    /**
     * new instance of SQLiteOpenHelper, often used in application
     *
     * @param sqLiteOpenHelper
     */
    public static void newInstance(SQLiteOpenHelper sqLiteOpenHelper) {
        if (db == null) {
            db = sqLiteOpenHelper.getWritableDatabase();
        }
    }

    /**
     * insert a record into database
     *
     * @param tableName
     * @param values
     * @return
     */
    public static long insert(String tableName, ContentValues values) {
        long id = 0;

        try {
            db.beginTransaction();
            id = db.insert(tableName, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }

        return id;
    }

    /**
     * insert multiple records into database
     *
     * @param tableName
     * @param values
     * @return
     */
    public static boolean inserts(String tableName,
                                  List<ContentValues> values) {
        try {
            if (values == null || values.size() <= 0) return false;

            db.beginTransaction();
            for (ContentValues value : values) {
                db.insert(tableName, null, value);
            }
            db.setTransactionSuccessful();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }

        return false;
    }

    /**
     * update record
     *
     * @param tableName
     * @param values
     * @param whereClause
     * @param whereArgs
     * @return
     */
    public static long update(String tableName, ContentValues values,
                              String whereClause, String[] whereArgs) {
        long id = 0;

        try {
            db.beginTransaction();
            id = db.update(tableName, values, whereClause,
                    whereArgs);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }

        return id;
    }

    /**
     * delete records
     *
     * @param tableName
     * @param whereClause
     * @param whereArgs
     * @return
     */
    public static long delete(String tableName, String whereClause,
                              String[] whereArgs) {
        long id = 0;

        try {
            db.beginTransaction();
            id = db.delete(tableName, whereClause, whereArgs);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }

        return id;
    }

    /**
     * query data from database
     *
     * @param table
     * @param columns
     * @param selection
     * @param selectionArgs
     * @param groupBy
     * @param having
     * @param orderBy
     * @return
     */
    public static Cursor query(String table, String[] columns,
                               String selection, String[] selectionArgs, String groupBy,
                               String having, String orderBy) {
        Cursor cursor = null;

        try {
            cursor = db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cursor;
    }

    /**
     * exec query string
     *
     * @param sql
     */
    public static void execSQL(String sql) {
        execSQL(sql, null);
    }

    /**
     * exec query string
     *
     * @param sql
     * @param bindArgs
     */
    public static void execSQL(String sql, String[] bindArgs) {
        try {
            db.beginTransaction();
            db.execSQL(sql, bindArgs);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    /**
     * get data by query string
     *
     * @param sql
     * @return
     */
    public static Cursor rawQuery(String sql) {
        return rawQuery(sql, null);
    }

    /**
     * get data by query string
     *
     * @param sql
     * @param selectionArgs
     * @return
     */
    public static Cursor rawQuery(String sql, String[] selectionArgs) {
        Cursor cursor = null;

        try {
            cursor = db.rawQuery(sql, selectionArgs);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cursor;
    }
}
