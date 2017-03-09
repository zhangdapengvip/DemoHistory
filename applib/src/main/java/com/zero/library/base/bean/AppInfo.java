package com.zero.library.base.bean;

import android.graphics.drawable.Drawable;

public class AppInfo {

    public AppInfo() {
    }

    private String mAppName;
    private String mPackageName;
    private String mVersionName;
    private int mVersionCode;
    private Drawable mAppIcon;
    private boolean mIsSysApp;
    private int mPid;
    private String mMemorySize = "0.0KB";
    private String localFilePath;

    public String getAppName() {
        return mAppName;
    }

    public void setAppName(String appName) {
        this.mAppName = appName;
    }

    public String getPackageName() {
        return mPackageName;
    }

    public void setPackageName(String packageName) {
        this.mPackageName = packageName;
    }

    public String getVersionName() {
        return mVersionName;
    }

    public void setVersionName(String versionName) {
        this.mVersionName = versionName;
    }

    public int getVersionCode() {
        return mVersionCode;
    }

    public void setVersionCode(int versionCode) {
        this.mVersionCode = versionCode;
    }

    public Drawable getAppIcon() {
        return mAppIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.mAppIcon = appIcon;
    }

    public boolean isSysApp() {
        return mIsSysApp;
    }

    public void setSysApp(boolean isSysApp) {
        this.mIsSysApp = isSysApp;
    }

    public int getPid() {
        return mPid;
    }

    public void setPid(int pid) {
        this.mPid = pid;
    }

    public String getMemorysize() throws InterruptedException {
        return mMemorySize;
    }

    public void setMemorysize(String memorysize) {
        if (memorysize == null) {
            mMemorySize = "0.0KB";
        } else {
            mMemorySize = memorysize;
        }
    }

    public String getLocalFilePath() {
        return localFilePath;
    }

    public void setLocalFilePath(String localFilePath) {
        this.localFilePath = localFilePath;
    }

}
