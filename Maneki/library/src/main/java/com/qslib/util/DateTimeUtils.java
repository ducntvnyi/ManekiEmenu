package com.qslib.util;

import android.annotation.SuppressLint;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
            return convertDateToString(calendar.getTime(), pattern);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return "";
    }

    public static String convertDateToString(Date date, String pattern) {
        try {
            if (date == null) return "";
            @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            return simpleDateFormat.format(date);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return "";
    }

    public static int getAge(String date) {
        int age = 0;
        try {
            Log.e("Date: " ,"==> getAge:: " + date);
            Date dateOfBirth = new Date(date);
            Calendar today = Calendar.getInstance();
            Calendar birthDate = Calendar.getInstance();
            birthDate.setTime(dateOfBirth);
            if (birthDate.after(today)) {
                throw new IllegalArgumentException("Can't be born in the future");
            }
            age = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);
            Log.e("Date: " ,"==> getAge:: " + age);
//        // If birth date is greater than todays date (after 2 days adjustment of leap year) then decrement age one year
//        if ((birthDate.get(Calendar.DAY_OF_YEAR) - today.get(Calendar.DAY_OF_YEAR) > 3)
//                || (birthDate.get(Calendar.MONTH) > today.get(Calendar.MONTH))) {
//            age--;
//
//            // If birth date and todays date are of same month and birth day of month is greater than todays day of month then decrement age
//        } else if ((birthDate.get(Calendar.MONTH) == today.get(Calendar.MONTH))
//                && (birthDate.get(Calendar.DAY_OF_MONTH) > today.get(Calendar.DAY_OF_MONTH))) {
//            age--;
//        }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return age;
    }
}
