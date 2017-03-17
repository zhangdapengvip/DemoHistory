package com.zero.library.base.uibase;

import android.app.Activity;
import android.content.Context;

import java.util.LinkedList;
import java.util.List;

public class AppActivityManager {
    private static List<Activity> mActivities = new LinkedList<Activity>();
    private static List<Activity> mTempActivities = new LinkedList<Activity>();

    public static void addActivity(Activity activity) {
        if (null != activity) {
            mActivities.add(activity);
        }
    }

    public static void removeActivity(Activity activity) {
        if (null != activity) {
            mActivities.remove(activity);
        }
    }

    public static void addTempActivity(Activity activity) {
        if (null != activity) {
            mTempActivities.add(activity);
        }
    }

    public static void removeTempActivity(Activity activity) {
        if (null != activity) {
            mTempActivities.remove(activity);
        }
    }

    public static void exitTemp() {
        for (Activity activity : mTempActivities) {
            activity.finish();
        }
    }

    public static void exitAPP() {
        for (Activity activity : mActivities) {
            activity.finish();
        }
    }

    public static void exitAPP(Context context) {
        for (Activity activity : mActivities) {
            activity.finish();
        }
    }

    public static void exitAPP(Activity act) {
        for (Activity activity : mActivities) {
            if (act != activity) {
                activity.finish();
            }
        }
    }

    public static int getActivitiesNum() {
        return mActivities.size();
    }

}
