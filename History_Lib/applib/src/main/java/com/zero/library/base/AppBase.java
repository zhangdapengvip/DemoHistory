package com.zero.library.base;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.umeng.analytics.MobclickAgent;
import com.zero.library.base.constants.DirConstants;
import com.zero.library.base.manager.PropertyManager;

import java.io.File;

public class AppBase {
    private static Application mApplication;
    private static Thread mMainThread;
    private static long mMainThreadId;
    private static boolean mEnable;

    public static void init(Application application, Thread mainThread,
                            long mainThreadId) {
        mApplication = application;
        mMainThread = mainThread;
        mMainThreadId = mainThreadId;
        mEnable = PropertyManager.logEnable();
        initLogger();
        initDifalutDirs();
        MobclickAgent.setDebugMode(mEnable);
    }

    public static String getMetaDate(String key) {
        try {
            ApplicationInfo info = mApplication.getPackageManager()
                    .getApplicationInfo(mApplication.getPackageName(), PackageManager.GET_META_DATA);
            return info.metaData.getString(key);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void initLogger() {
        Logger.init("ZERO_AIRES")
//                .hideThreadInfo()
                .setMethodCount(3)
                .setLogLevel(mEnable ? LogLevel.FULL : LogLevel.NONE)
                .setMethodOffset(2);
    }


    private static void initDifalutDirs() {
        for (String dir : DirConstants.getCreatDirList()) {
            makeDir(dir);
        }
    }

    private static void makeDir(String path) {
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.mkdirs();
            } catch (Exception e) {
                Logger.e(e.getMessage());
            }
        }
    }

    public static Application getApplication() {
        return mApplication;
    }

    public static Thread getMainThread() {
        return mMainThread;
    }

    public static long getMainThreadId() {
        return mMainThreadId;
    }

}
