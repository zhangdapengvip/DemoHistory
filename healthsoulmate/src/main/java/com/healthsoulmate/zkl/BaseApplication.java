package com.healthsoulmate.zkl;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.healthsoulmate.zkl.ui.activity.SplashActivity;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.utils.Log;
import com.zero.library.base.AppBase;
import com.zero.library.base.manager.PropertyManager;
import com.zero.library.base.utils.UtilsIO;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by ZeroAries on 2016/4/20.
 * Application
 */
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
        Thread.setDefaultUncaughtExceptionHandler(restartHandler); //程序崩溃检测
    }

    private void initPlantformInfo() {
        Config.IsToastTip = mEnable;
        Log.LOG = mEnable;
        //微信 appid appsecret
        PlatformConfig.setWeixin("wxc24f0a76b70920e0", "9efbfa2a34c2132795f8c30830924a7e");
        //新浪微博 appkey appsecret
        PlatformConfig.setSinaWeibo("3513675374", "b191af09a8e5966d2f5cdcf4bbb28133");
        // QQ和Qzone appid appkey
        PlatformConfig.setQQZone("1105322878", "OfQnsEGifUPxP3zy");
    }

    private void initJpush() {
        JPushInterface.init(this);
        JPushInterface.setDebugMode(mEnable);
        JPushInterface.setLatestNotificationNumber(this, 3);
    }

    // 创建服务用于捕获崩溃异常
    private Thread.UncaughtExceptionHandler restartHandler = (thread, ex) -> {
        UtilsIO.writeLog("Error: ", ex.getMessage(), ex);
        MobclickAgent.reportError(mContext, ex);//上传至友盟统计
        restartApp();//发生崩溃异常时,重启应用
    };

    public void restartApp() {
        Intent intent = new Intent(mInstance, SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mInstance.startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());  //结束进程之前可以把你程序的注销或者退出代码放在这段代码之前
    }
}
