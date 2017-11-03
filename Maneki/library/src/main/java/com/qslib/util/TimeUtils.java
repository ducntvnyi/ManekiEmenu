package com.qslib.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by tuyennm on 9/12/2016.
 */
public class TimeUtils {

    private final static long MILISECONDS_PER_SECOND = 1000;
    private final static long MILISECONDS_PER_MINUTE = 60000;
    private final static long MILISECONDS_PER_HOUR = 3600000;
    private final static long MILISECONDS_PER_DAY = 86400000;

    private final static long DAY_PER_WEEK = 7;

    private final static String AGO = " ago";
    private final static String SECONDS = "%d seconds" + AGO;
    private final static String MINUTES = "%d minutes" + AGO;
    private final static String HOURS = "%d hours" + AGO;
    private final static String DAYS = "%d days" + AGO;

    private final static String FORMAT_DATE_DISPLAY = "MMM dd, yyyy";

    public static String formatDuration(long timeInMilis) {
        try {
            Calendar now = Calendar.getInstance();
            long diffTime = now.getTimeInMillis() - timeInMilis;
            if (diffTime > MILISECONDS_PER_DAY) {
                if (diffTime > DAY_PER_WEEK) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(timeInMilis);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMAT_DATE_DISPLAY);
                    return simpleDateFormat.format(calendar.getTime());
                }
                return String.format(DAYS, diffTime / MILISECONDS_PER_DAY);
            }
            if (diffTime > MILISECONDS_PER_HOUR)
                return String.format(HOURS, diffTime / MILISECONDS_PER_HOUR);
            if (diffTime > MILISECONDS_PER_MINUTE)
                return String.format(MINUTES, diffTime / MILISECONDS_PER_MINUTE);
            if (diffTime > MILISECONDS_PER_SECOND)
                return String.format(SECONDS, diffTime / MILISECONDS_PER_SECOND);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
