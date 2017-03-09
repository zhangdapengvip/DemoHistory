package com.zero.library.base.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.zero.library.R;
import com.zero.library.base.view.AppEmptyDialog;

import java.text.DecimalFormat;
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
    public static final String DATE_SPLITE = "-";
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

    private static int mYear;
    private static int mMonth;
    private static int mDay;

    public static void showDateChoice(Activity context, TextView textView, long maxDate) {
        showDateChoice(context, textView, 0, maxDate);
    }

    public static void showDateChoice(Activity context, final TextView textView, long minDate, long maxDate) {
        Calendar calendar = Calendar.getInstance();
        View dialogView = View.inflate(context, R.layout.dialog_choice_date, null);
        DatePicker dpDate = (DatePicker) dialogView.findViewById(R.id.dp_date);
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);
        dpDate.init(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        mYear = year;
                        mMonth = monthOfYear;
                        mDay = dayOfMonth;
                    }
                });
        if (maxDate > 0) dpDate.setMaxDate(maxDate);
        if (minDate > 0) dpDate.setMinDate(minDate);
        setPadding(dpDate);
        AppEmptyDialog dateDialog = new AppEmptyDialog(context, 1);
        dateDialog.setBtnOnClickListener(new AppEmptyDialog.BtnOnClickListener() {
            @Override
            public void onClick(Dialog dialog, AppEmptyDialog.BtnView view) {
                DecimalFormat df = new DecimalFormat("00");
                textView.setText(mYear + DATE_SPLITE + df.format(mMonth + 1) + DATE_SPLITE + df.format(mDay));
                dialog.dismiss();
            }
        });
        dateDialog.setView(dialogView);
        dateDialog.show();
    }

    private static int mHour;
    private static int mMinute;

    public static void showTimeChoice(Activity context, final TextView textView) {
        View dialogView = View.inflate(context, R.layout.dialog_choice_time, null);
        TimePicker timePickeer = (TimePicker) dialogView.findViewById(R.id.tp_time);
        timePickeer.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {

            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                mHour = hourOfDay;
                mMinute = minute;
            }
        });
        mHour = timePickeer.getCurrentHour();
        mMinute = timePickeer.getCurrentMinute();
        timePickeer.setIs24HourView(true);
        setPadding(timePickeer);
        // 隐藏时间选择列
        ViewGroup timeGroup = (ViewGroup) timePickeer.getChildAt(0);
        // timeGroup.getChildAt(0).setVisibility(View.GONE);
        timeGroup.getChildAt(1).setVisibility(View.GONE);
        AppEmptyDialog dialog = new AppEmptyDialog(context, 1);
        dialog.setBtnOnClickListener(new AppEmptyDialog.BtnOnClickListener() {
            @Override
            public void onClick(Dialog dialog, AppEmptyDialog.BtnView view) {
                DecimalFormat df = new DecimalFormat("00");
                textView.setText(df.format(mHour) + ":" + df.format(mMinute));
                dialog.dismiss();
            }
        });
        dialog.setView(dialogView);
        dialog.show();
    }

    private static void setPadding(ViewGroup group) {
        for (int index = 0; index < group.getChildCount(); index++) {
            View child = group.getChildAt(index);
            if (child instanceof ViewGroup) {
                setPadding((ViewGroup) child);
            }
            if (child instanceof LinearLayout) {
                child.setPadding(0, 0, 0, 0);
            }
            if (child instanceof NumberPicker) {
                NumberPicker picker = (NumberPicker) child;
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(0, 0, 0, 0);
                picker.setLayoutParams(params);
                picker.setPadding(0, 0, 0, 0);
                setNumberPicker(picker);
            }
        }
    }

    private static void setNumberPicker(ViewGroup numberPicker) {
        for (int index = 0; index < numberPicker.getChildCount(); index++) {
            View child = numberPicker.getChildAt(index);
            if (child instanceof ViewGroup) {
                setNumberPicker((ViewGroup) child);
            }
            if (child instanceof ImageButton) {
                ImageButton picker = (ImageButton) child;
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(0, 0, 0, 0);
                picker.setLayoutParams(params);
                picker.setPadding(0, 0, 0, 0);
            }
            if (child instanceof EditText) {
                EditText picker = (EditText) child;
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                picker.setGravity(Gravity.CENTER);
                params.setMargins(0, 0, 0, 0);
                picker.setLayoutParams(params);
                picker.setPadding(0, 0, 0, 0);
            }
        }
    }
}
