package com.zero.library.base.utils;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import com.zero.library.R;
import com.zero.library.base.AppToast;
import com.zero.library.base.constants.DirConstants;
import com.zero.library.base.view.AppAlertDialog;
import com.zero.library.base.view.AppEmptyDialog;

import java.io.File;

/**
 * Created by ZeroAries on 2016/4/18.
 * Apk更新
 */
public class UtilsUpdate {

    public static final String UPDATE_APK = "update.apk";
    public static final String UPDATE_PATH = DirConstants.DEFAULT_APP_DIR + UPDATE_APK;


    /**
     * 更新Apk
     *
     * @param activity 当前Activity
     * @param url      更新url
     * @param version  更新version
     * @return 是否更新
     */
    public static boolean updateApk(Activity activity, String url, String version) {
        return updateApk(activity, url, version, false, null);
    }

    /**
     * 更新Apk
     *
     * @param activity  当前Activity
     * @param url       更新url
     * @param version   更新version
     * @param canCancle 是否可已取消
     * @param listener
     * @return 是否更新
     */
    public static boolean updateApk(Activity activity, String url, String version, boolean canCancle,
                                    DialogInterface.OnCancelListener listener) {
        if (null != activity &&
                !activity.isFinishing() &&
                !TextUtils.isEmpty(version) &&
                isVersionLessThan(version)) {
            showUpdataDialog(activity, url, canCancle, listener);
            return true;
        }
        return false;
    }


    /**
     * 更新提示框
     *
     * @param activity
     * @param url
     * @param listener
     */
    private static void showUpdataDialog(final Activity activity,
                                         final String url, boolean canCancle,
                                         DialogInterface.OnCancelListener listener) {
        AppAlertDialog dialog = new AppAlertDialog(activity, 1);
        dialog.setCanceledOnTouchOutside(canCancle);
        dialog.setCancelable(canCancle);
        if (null != listener)
            dialog.setOnCancelListener(listener);
        dialog.setTitle(R.string.update_title);
        dialog.setMsg(R.string.update_notice);
        dialog.setOneBtnText(R.string.update_now);
        dialog.setBtnOnClickListener(new AppEmptyDialog.BtnOnClickListener() {

            @Override
            public void onClick(Dialog dialog, AppEmptyDialog.BtnView view) {
                downLoadApk(activity, url);
            }
        });
        dialog.show();
    }


    /**
     * 下载更新App
     *
     * @param activity
     * @param url
     */
    private static void downLoadApk(final Activity activity, final String url) {
        final ProgressDialog pd = new ProgressDialog(activity);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setMessage("正在下载更新");
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        new Thread() {
            @Override
            public void run() {
                try {
                    File file = UtilsFile.getFileFromServer(url, pd, new File(UPDATE_PATH));
                    if (pd.isShowing()) {
                        installApk(activity, file);
                    }
                    pd.dismiss();
                } catch (Exception e) {
                    AppToast.show(activity, UtilsUi.getString(R.string.error_service_error));
                }
            }
        }.start();
    }


    /**
     * 安装app
     *
     * @param activity
     * @param file     app路径
     */
    private static void installApk(Activity activity, File file) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        activity.startActivity(intent);
    }

    /**
     * 判断版本是否小于 version
     *
     * @param version
     * @return
     */
    private static boolean isVersionLessThan(String version) {
        return UtilsUi.getVersionName().compareTo(version) < 0;
    }
}
