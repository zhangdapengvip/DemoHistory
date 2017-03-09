package com.zero.library.base.bean;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

public class SoftwareInfo {
    private PackageManager mPackageManager;
    private ActivityManager mActivityManager;

    public SoftwareInfo(Context context) {
        mPackageManager = context.getPackageManager();
        mActivityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
    }

    /**
     * 软件版本信息
     *
     * @return
     */
    public String[] getVersion() {
        String[] version = {"null", "null", "null", "null"};
        String str1 = "/proc/version";
        String str2;
        String[] arrayOfString;
        try {
            FileReader localFileReader = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(
                    localFileReader, 8192);
            str2 = localBufferedReader.readLine();
            if (null != str2) {
                arrayOfString = str2.split("\\s+");
                version[0] = arrayOfString[2];// KernelVersion
            }
            localBufferedReader.close();
        } catch (IOException e) {
        }
        version[1] = Build.VERSION.RELEASE;// firmware version
        version[2] = Build.MODEL;// model
        version[3] = Build.DISPLAY;// system version
        return version;
    }

    /**
     * 获得软件信息
     */
    public List<ArrayList<AppInfo>> getSoftInfo(Context context) {
        ArrayList<AppInfo> sysAppList = new ArrayList<AppInfo>();
        ArrayList<AppInfo> userAppList = new ArrayList<AppInfo>();
        // 用来存储获取的应用信息数据
        List<PackageInfo> packages = mPackageManager
                .getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
        for (int i = 0; i < packages.size(); i++) {
            PackageInfo packageInfo = packages.get(i);
            AppInfo tmpInfo = new AppInfo();
            tmpInfo.setAppName(packageInfo.applicationInfo.loadLabel(
                    mPackageManager).toString());
            tmpInfo.setPackageName(packageInfo.packageName);
            tmpInfo.setVersionName(packageInfo.versionName);
            tmpInfo.setVersionCode(packageInfo.versionCode);
            tmpInfo.setSysApp(isSysApp(packageInfo.applicationInfo));
            if (tmpInfo.isSysApp()) {
                sysAppList.add(tmpInfo);
            } else {
                userAppList.add(tmpInfo);
            }
        }
        List<ArrayList<AppInfo>> list = new ArrayList<ArrayList<AppInfo>>();
        list.add(sysAppList);
        list.add(userAppList);
        return list;
    }

    /**
     * 判断某个应用是不是系统级应用程序
     */
    public boolean isSysApp(ApplicationInfo applicationInfo) {
        if ((applicationInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
            return true;
        } else if ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
            return true;
        }
        return false;
    }

    /**
     * 获取操作系统的名字和版本号
     */
    public String getOS() {
        Properties props = System.getProperties();
        return props.getProperty("os.name") + " "
                + props.getProperty("os.version");
    }

    /**
     * 获取当前手机运行的服务
     */
    public String getRunningServices() {
        List<RunningServiceInfo> runningServiceInfos = mActivityManager
                .getRunningServices(Integer.MAX_VALUE);
        StringBuffer buffer = new StringBuffer();
        for (RunningServiceInfo runningServiceInfo : runningServiceInfos) {
            ComponentName componentName = runningServiceInfo.service;
            buffer.append(componentName.getPackageName()).append("|");
        }
        buffer.deleteCharAt(buffer.length() - 1);
        return buffer.toString();
    }
}
