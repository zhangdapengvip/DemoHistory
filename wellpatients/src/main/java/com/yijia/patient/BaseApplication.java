package com.yijia.patient;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.utils.Log;
import com.yijia.patient.ui.activity.WelcomeActivity;
import com.zero.library.base.AppBase;
import com.zero.library.base.manager.PropertyManager;
import com.zero.library.base.utils.UtilsIO;

import cn.jpush.android.api.JPushInterface;

public class BaseApplication extends Application {
    private static BaseApplication mInstance;
    public static Context mContext;
    private static Thread mMainThread;
    private static long mMainThreadId = -1;
    private boolean mEnable;

    @Override
    public void onCreate() {
        super.onCreate();
        mEnable = PropertyManager.logEnable();
        mInstance = this;
        mContext = this;
        mMainThread = Thread.currentThread();
        mMainThreadId = android.os.Process.myTid();
        AppBase.init(mInstance, mMainThread, mMainThreadId);
        initPlantformInfo();
        initJpush();
        //Thread.setDefaultUncaughtExceptionHandler(restartHandler); //程序崩溃检测
    }

    private void initJpush() {
        JPushInterface.init(this);
        JPushInterface.setDebugMode(mEnable);
        JPushInterface.setLatestNotificationNumber(this, 3);
    }

    private void initPlantformInfo() {
        Config.IsToastTip = mEnable;
        Log.LOG = mEnable;
        //微信 appid appsecret
        PlatformConfig.setWeixin("wx00a06a4ebf83fc84", "8c545380dcc99b2351d8198bee133ab9");
        //新浪微博 appkey appsecret
        PlatformConfig.setSinaWeibo("155485386", "cc845cb69d97c76c7f195a9f5f124a3e");
        // QQ和Qzone appid appkey
        PlatformConfig.setQQZone("1105516886", "Gq1QMSQDZQul8I7C");
    }

    // 创建服务用于捕获崩溃异常
    private Thread.UncaughtExceptionHandler restartHandler = (thread, ex) -> {
        MobclickAgent.reportError(mContext, ex);//上传至友盟统计
        UtilsIO.writeLog("Error: ", ex.getMessage(), ex);
        restartApp();//发生崩溃异常时,重启应用
    };

    public void restartApp() {
        Intent intent = new Intent(mInstance, WelcomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mInstance.startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());  //结束进程之前可以把你程序的注销或者退出代码放在这段代码之前
    }
}