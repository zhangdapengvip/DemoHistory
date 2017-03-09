package com.zero.library.base.utils;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.v4.util.LruCache;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;

import com.orhanobut.logger.Logger;
import com.zero.library.R;
import com.zero.library.base.AppBase;
import com.zero.library.base.view.AppAlertDialog;
import com.zero.library.base.view.AppEmptyDialog.BtnOnClickListener;
import com.zero.library.base.view.AppPopupWindow;

import java.lang.reflect.Field;

public class UtilsUi {
    public static Context getContext() {
        return AppBase.getApplication();
    }

    public static Thread getMainThread() {
        return AppBase.getMainThread();
    }

    public static long getMainThreadId() {
        return AppBase.getMainThreadId();
    }

    /**
     * 判断当前是否联网
     *
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context
                .CONNECTIVITY_SERVICE);
        if (cm == null) return false;
        NetworkInfo network = cm.getActiveNetworkInfo();
        if (network != null) {
            return network.isAvailable();
        }
        return false;
    }

    /**
     * @return 当前App分配的空间大小
     */
    public static int getAllowSize() {
        return (int) Runtime.getRuntime().maxMemory();
    }

    /**
     * Bitmap缓存,空间大小为App的1/8
     */
    public static final LruCache<String, Bitmap> bitmapCatch = new LruCache<String, Bitmap>(
            getAllowSize() / 8) {
        @Override
        protected int sizeOf(String key, Bitmap value) {
            return value.getRowBytes() * value.getHeight();
        }
    };


    /**
     * Drawable缓存,空间大小为App的1/8
     */
    public static final LruCache<String, Drawable> drawableCatch = new LruCache<String, Drawable>(
            getAllowSize() / 8) {
        @Override
        protected int sizeOf(String key, Drawable value) {
            return value.getIntrinsicWidth() * value.getIntrinsicHeight();
        }
    };

    public static int getScreenWidth(Activity context) {
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    public static int getScreenHeight(Activity context) {
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    /**
     * dip转换px
     */
    public static int dip2px(int dip) {
        return (int) (dip * getDensity() + 0.5f);
    }

    /**
     * px转换dip
     */
    public static int px2dip(float px) {
        return (int) (px / getDensity() + 0.5f);
    }

    public static float getDensity() {
        return getContext().getResources().getDisplayMetrics().density;
    }

    /**
     * 根据机制将dp适配成px
     *
     * @param activity
     * @param dip
     * @return
     */
    public static int dipAdaptationPx(Activity activity, int dip) {
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;
        Float dimenFloat = Float.valueOf(dip);
        /**
         * realDip * density / width = 1 * dip / 320;
         */
        int adaptationPx = (int) ((width * dimenFloat) / 320 + 0.5);
        return adaptationPx;
    }

    /**
     * 获取当前版本号
     */
    public static String getVersionName() {
        String appver = null;
        PackageManager packageManager = UtilsUi.getContext().getPackageManager();
        try {
            PackageInfo packInfo = packageManager.getPackageInfo(UtilsUi.getContext().getPackageName(), 0);
            appver = packInfo.versionName;
        } catch (Exception e) {
            Logger.e(e.toString());
        }
        return appver;
    }

    /**
     * 获取当前版编号
     */
    public static int getVersionCode() {
        int appver = -1;
        PackageManager packageManager = UtilsUi.getContext().getPackageManager();
        try {
            PackageInfo packInfo = packageManager.getPackageInfo(UtilsUi.getContext().getPackageName(), 0);
            appver = packInfo.versionCode;
        } catch (Exception e) {
            Logger.e(e.toString());
        }
        return appver;
    }

    /**
     * 获取IMEI号
     */
    public static String getIMEI() {
        Context context = UtilsUi.getContext();
        String imei = null;
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            imei = tm.getDeviceId();
        } catch (Exception e) {
            Logger.e(e.toString());
        }
        return imei;
    }

    /**
     * 获取主线程的handler
     */
    public static Handler getHandler() {
        return new Handler(Looper.getMainLooper());
    }

    /**
     * 延时在主线程执行runnable
     */
    public static boolean postDelayed(Runnable runnable, long delayMillis) {
        return getHandler().postDelayed(runnable, delayMillis);
    }

    /**
     * 在主线程执行runnable
     */
    public static boolean post(Runnable runnable) {
        return getHandler().post(runnable);
    }

    /**
     * 从主线程looper里面移除runnable
     */
    public static void removeCallbacks(Runnable runnable) {
        getHandler().removeCallbacks(runnable);
    }

    public static View inflate(Activity activity, int resId) {
        return View.inflate(activity, resId, null);
    }

    public static View inflate(Context context, int resId) {
        return View.inflate(context, resId, null);
    }

    /**
     * 获取资源
     */
    public static Resources getResources() {
        return getContext().getResources();
    }

    /**
     * 获取资源ID
     */
    public static int getResIdByName(String resName, String resType) {
        return getResources().getIdentifier(resName, resType,
                getContext().getPackageName());
    }

    /**
     * 获取文字
     */
    public static String getString(int resId) {
        return getResources().getString(resId);
    }

    /**
     * 获取文字数组
     */
    public static String[] getStringArray(int resId) {
        return getResources().getStringArray(resId);
    }

    /**
     * 获取dimen
     */
    public static int getDimens(int resId) {
        return getResources().getDimensionPixelSize(resId);
    }

    /**
     * 获取drawable
     */
    public static Drawable getDrawable(int resId) {
        return getResources().getDrawable(resId);
    }

    /**
     * 获取颜色
     */
    public static int getColor(int resId) {
        return getResources().getColor(resId);
    }

    /**
     * 获取颜色选择器
     */
    public static ColorStateList getColorStateList(int resId) {
        return getResources().getColorStateList(resId);
    }

    public static boolean isRunInMainThread() {
        return android.os.Process.myTid() == getMainThreadId();
    }

    public static void runInMainThread(Runnable runnable) {
        if (isRunInMainThread()) {
            runnable.run();
        } else {
            post(runnable);
        }
    }

    /**
     * @param context  当前Context
     * @param title    提示标题
     * @param msg      提示内容
     * @param leftBtn  左侧按钮
     * @param rightBtn 右侧按钮
     * @param listener 事件监听
     * @return 设置好的Dialog
     */
    public static AppAlertDialog getNoticeDialog(final Activity context, final String title,
                                                 final String msg, final String leftBtn,
                                                 final String rightBtn, final BtnOnClickListener listener) {
        AppAlertDialog dialog = new AppAlertDialog(context, 2);
        dialog.setTitle(title);
        dialog.setMsg(msg);
        dialog.setLeftBtnText(leftBtn);
        dialog.setRightBtnText(rightBtn);
        dialog.setBtnOnClickListener(listener);
        return dialog;

    }

    /**
     * 获取单个按钮的Dialog
     *
     * @param context  当前Context
     * @param title    提示标题
     * @param msg      提示内容
     * @param btn      底部按钮
     * @param listener 时间监听
     */
    public static AppAlertDialog getNoticeDialog(final Activity context, final String title,
                                                 final String msg, final String btn,
                                                 final BtnOnClickListener listener) {
        AppAlertDialog dialog = new AppAlertDialog(context, 1);
        dialog.setTitle(title);
        dialog.setMsg(msg);
        dialog.setOneBtnText(btn);
        dialog.setBtnOnClickListener(listener);
        return dialog;

    }

    /**
     * 获得提示Dialog
     *
     * @param context
     * @param msg
     * @param listener
     */
    public static AppAlertDialog getNoticeDialog(final Activity context,
                                                 final String msg, final BtnOnClickListener listener) {
        return getNoticeDialog(context, getString(R.string.notice), msg, getString(R.string.ok), listener);
    }

    /**
     * 获得提示Dialog
     *
     * @param context
     * @param msg
     */
    public static AppAlertDialog getNoticeDialog(Activity context, String msg) {
        return getNoticeDialog(context, msg, null);
    }

    /**
     * 显示下拉ListView
     *
     * @param activity
     * @param parentView 父view
     * @param content    显示内容List
     * @param canCancle  是否可取消
     * @return
     */
    public static PopupWindow showDropListPopuwindow(Activity activity,
                                                     View parentView, View content,
                                                     boolean canCancle) {
        return showDropListPopuwindow(activity, parentView, content,
                parentView.getWidth(), canCancle);
    }

    /**
     * 显示下拉ListView
     *
     * @param activity
     * @param parentView 父view
     * @param content    显示内容List
     * @param width      内容宽度
     * @param canCancle  是否可取消
     * @return
     */
    public static PopupWindow showDropListPopuwindow(Activity activity,
                                                     View parentView, View content, int width,
                                                     boolean canCancle) {
        return showDropListPopuwindow(activity, parentView, content, width, canCancle, 0, 0);
    }

    /**
     * 显示下拉ListView
     *
     * @param activity
     * @param parentView 父view
     * @param content    显示内容List
     * @param width      内容宽度
     * @param canCancle  是否可取消
     * @param xOff       x轴偏移量
     * @param yOff       y轴偏移量
     * @return
     */
    public static PopupWindow showDropListPopuwindow(Activity activity,
                                                     View parentView, View content, int width,
                                                     boolean canCancle, int xOff, int yOff) {
        if (activity.isFinishing()) {
            return null;
        }
        AppPopupWindow popupWindow = new AppPopupWindow(activity, parentView);
        popupWindow.setWidth(width);
        popupWindow.setHeight(LayoutParams.WRAP_CONTENT);
        if (canCancle) {
            popupWindow.setBackgroundDrawable(new BitmapDrawable());
            popupWindow.setOutsideTouchable(true);
        }
        parentView.setFocusableInTouchMode(true);
        popupWindow.setFocusable(true);
        popupWindow.setContentView(content);
        popupWindow.showAsDropDown(parentView, xOff, yOff);
        return popupWindow;
    }

    /**
     * 设置窗口透明度
     *
     * @param activtiy
     * @param bgAlpha
     */
    public static void setWindowBackgroundAlpha(final Activity activtiy,
                                                final float bgAlpha) {
        runInMainThread(new Runnable() {

            @Override
            public void run() {
                Window window = activtiy.getWindow();
                WindowManager.LayoutParams lp = window.getAttributes();
                lp.alpha = bgAlpha;
                window.setAttributes(lp);
                if (1f == bgAlpha) {
                    window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                } else {
                    window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                }
            }
        });
    }

    /**
     * 设置view是否可见
     *
     * @param view 目标view
     * @param show 是否可见
     */
    public static void setVisibility(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    /**
     * 将Activity转化为View
     *
     * @param activtiy
     * @param intent
     * @param id
     * @param state
     * @return
     */
    @SuppressWarnings("deprecation")
    public static View getActivityView(Activity activtiy, Intent intent,
                                       String id, Bundle state) {
        LocalActivityManager manager = new LocalActivityManager(activtiy, true);
        manager.dispatchCreate(state);
        return manager.startActivity(id, intent).getDecorView();
    }

    /**
     * 获取状态栏高度
     *
     * @return
     */
    public static int getStatusBarHeight() {
        Class<?> dimenClass;
        Object dimenInstance;
        Field field;
        int dimenValue, statusBarHeightPx = 38;
        try {
            dimenClass = Class.forName("com.android.internal.R$dimen");
            dimenInstance = dimenClass.newInstance();
            field = dimenClass.getField("status_bar_height");
            dimenValue = Integer.parseInt(field.get(dimenInstance).toString());
            statusBarHeightPx = getResources().getDimensionPixelSize(dimenValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusBarHeightPx;
    }

    /**
     * 显示输入键盘
     *
     * @param activity
     */
    public static void showSoftInput(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 隐藏输入键盘
     *
     * @param activity
     */
    public static void hideSoftInput(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context
                .INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view != null) {
            IBinder token = view.getWindowToken();
            if (token != null) {
                inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
                        InputMethodManager.RESULT_UNCHANGED_SHOWN);
            }
        }
    }

    /**
     * 隐藏输入键盘
     *
     * @param activity
     * @param view     当前焦点View
     */
    public static void hideSoftInput(Activity activity, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context
                .INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }

    /**
     * 切换为全屏
     *
     * @param activity
     */
    public static void setFullScreen(Activity activity) {
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }


    /**
     * 退出全屏
     *
     * @param activity
     */
    public static void quitFullScreen(Activity activity) {
        WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
        attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activity.getWindow().setAttributes(attrs);
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }
}
