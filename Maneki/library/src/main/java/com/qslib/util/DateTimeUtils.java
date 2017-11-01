package com.qslib.util;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Dang on 10/14/2015.
 */
public class DateTimeUtils {
    /**
     * convert millisecond to calendar
     *
     * @param number
     * @return
     */
    public static Calendar convertTimeStampToDateTime(float number) {
        try {
            if (number <= 0) return null;

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis((long) number);

            return calendar;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    /**
     * convert calendar to string
     *
     * @param calendar
     * @param pattern
     * @return
     */
    public static String convertCalendarToString(Calendar calendar, String pattern) {
        try {
            if (calendar == null) return "";

            @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            return simpleDateFormat.format(calendar.getTime());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return "";
    }
}
