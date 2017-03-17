package com.zero.library.base.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class UtilsDate {

    public static final String FORMAT_DATE_DETAIL = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_DATE_YEAR = "yyyy-MM-dd";
    public static final String FORMAT_DATE_HOUR = "HH:mm";
    public static final String FORMAT_DATE_YEARMONTHDAY = "yyyy年MM月dd日";
    public static final String FORMAT_DATE_MONTHDAY = "MM月dd日";
    private static Calendar calendar = Calendar.getInstance();

    public static String getCurrentDate(String pattern) {
        return formatDate(new Date(), pattern);
    }

    public static String formatDate(Date date, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern,
                Locale.getDefault());
        return format.format(date);
    }

    public static Date nextDay(Date date, int day) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        cal.add(Calendar.DAY_OF_YEAR, day);
        return cal.getTime();
    }

    public static String getCurrentWeak() {
        return getWeak(new Date());
    }

    public static String getWeak(Date date) {
        calendar.setTime(date);
        String mWeak = calendar.get(Calendar.DAY_OF_WEEK) + "";
        if ("1".equals(mWeak)) {
            mWeak = "星期天";
        } else if ("2".equals(mWeak)) {
            mWeak = "星期一";
        } else if ("3".equals(mWeak)) {
            mWeak = "星期二";
        } else if ("4".equals(mWeak)) {
            mWeak = "星期三";
        } else if ("5".equals(mWeak)) {
            mWeak = "星期四";
        } else if ("6".equals(mWeak)) {
            mWeak = "星期五";
        } else if ("7".equals(mWeak)) {
            mWeak = "星期六";
        }
        return mWeak;
    }

    public static String formatTime(long time, String pattern) {
        SimpleDateFormat mFormat = new SimpleDateFormat(pattern);
        return mFormat.format(time);
    }
}
