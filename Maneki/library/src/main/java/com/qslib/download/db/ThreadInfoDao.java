package com.qslib.download.db;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dang
 */
public class ThreadInfoDao extends AbstractDao<ThreadInfo> {
    private static final String TABLE_NAME = ThreadInfo.class.getSimpleName();

    public ThreadInfoDao(Context context) {
        super(context);
    }

    /**
     * create table
     *
     * @param db
     */
    public static void createTable(SQLiteDatabase db) {
        try {
            db.execSQL("create table " + TABLE_NAME + "(_id integer primary key autoincrement, id integer, tag text, uri text, start long, end long, finished long)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * drop table
     *
     * @param db
     */
    public static void dropTable(SQLiteDatabase db) {
        try {
            db.execSQL("drop table if exists " + TABLE_NAME);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param info
     */
    public void insert(ThreadInfo info) {
        try {
            getWritableDatabase().execSQL("insert into " + TABLE_NAME + "(id, tag, uri, start, end, finished) values(?, ?, ?, ?, ?, ?)",
                    new Object[]{info.getId(), info.getTag(), info.getUri(), info.getStart(), info.getEnd(), info.getFinished()});
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param tag
     */
    public void delete(String tag) {
        try {
            getWritableDatabase().execSQL("delete from " + TABLE_NAME + " where tag = ?",
                    new Object[]{tag});
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(String tag, int threadId, long finished) {
        try {
            getWritableDatabase().execSQL("update " + TABLE_NAME + " set finished = ?" + " where tag = ? and id = ? ",
                    new Object[]{finished, tag, threadId});
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param tag
     * @return
     */
    public List<ThreadInfo> getThreadInfos(String tag) {
        List<ThreadInfo> list = new ArrayList<>();

        try {
            Cursor cursor = getReadableDatabase().rawQuery("select * from " + TABLE_NAME + " where tag = ?",
                    new String[]{tag});
            while (cursor.moveToNext()) {
                ThreadInfo info = new ThreadInfo();
                info.setId(cursor.getInt(cursor.getColumnIndex("id")));
                info.setTag(cursor.getString(cursor.getColumnIndex("tag")));
                info.setUri(cursor.getString(cursor.getColumnIndex("uri")));
                info.setEnd(cursor.getLong(cursor.getColumnIndex("end")));
                info.setStart(cursor.getLong(cursor.getColumnIndex("start")));
                info.setFinished(cursor.getLong(cursor.getColumnIndex("finished")));
                list.add(info);
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean exists(String tag, int threadId) {
        try {
            Cursor cursor = getReadableDatabase().rawQuery("select * from " + TABLE_NAME + " where tag = ? and id = ?",
                    new String[]{tag, threadId + ""});
            boolean isExists = cursor.moveToNext();
            cursor.close();
            return isExists;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
