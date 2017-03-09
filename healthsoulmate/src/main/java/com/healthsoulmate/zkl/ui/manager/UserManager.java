package com.healthsoulmate.zkl.ui.manager;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import com.healthsoulmate.zkl.forum.activity.ForumPeopleActivity;
import com.healthsoulmate.zkl.ui.activity.LoginActivity;
import com.healthsoulmate.zkl.ui.protocol.ProtocolImp;
import com.healthsoulmate.zkl.ui.protocol.response.LoginResponse;
import com.orhanobut.logger.Logger;
import com.zero.library.base.constants.AppConstants;
import com.zero.library.base.manager.JumpManager;
import com.zero.library.base.retrofit.DefaultResponse;
import com.zero.library.base.retrofit.RetrofitFactory;
import com.zero.library.base.retrofit.RetrofitUtils;
import com.zero.library.base.uibase.AppActivityManager;
import com.zero.library.base.utils.UtilsGson;
import com.zero.library.base.utils.UtilsSharedPreference;

import cn.jpush.android.api.JPushInterface;
import rx.Observable;

public class UserManager {
    public static final String SP_USER_INFO = "sp_user_info";
    public static final String SP_AUTO_LOGIN = "sp_auto_login";

    /**
     * 获取登录信息，检查是否登录，未登录跳转至登录
     *
     * @param activity
     * @return
     */
    public static LoginResponse getLoginInfo(Activity activity) {
        return getLoginInfo(activity, true);
    }

    /**
     * 获取登录信息，检查是否登录，未登录根据条件判断是否跳转至登录
     *
     * @param activity
     * @param jumpToLogin 是否跳转登录
     * @return
     */
    public static LoginResponse getLoginInfo(Activity activity, boolean jumpToLogin) {
        String stringInfo = getStoreLoginInfo();
        if (jumpToLogin && !checkLogin(activity)) return null;
        return UtilsGson.fromJson(stringInfo, LoginResponse.class);
    }

    /**
     * 检查是否登录，未登录跳转至登录
     *
     * @param activity
     * @return
     */
    public static boolean checkLogin(Activity activity) {
        if (!isLogin()) {
            jumpToLogin(activity);
            return false;
        }
        return true;
    }

    /**
     * 检查是否登录
     *
     * @return
     */
    public static boolean isLogin() {
        return !TextUtils.isEmpty(getStoreLoginInfo());
    }

    /**
     * 跳转至登录界面
     *
     * @param activity
     */
    public static void jumpToLogin(Activity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);
        JumpManager.doJumpForward(activity, intent);
    }

    /**
     * 保存用户JSON
     *
     * @param loginInfo
     */
    public static void storeLoginInfo(String loginInfo) {
        UtilsSharedPreference.setStringValue(SP_USER_INFO, loginInfo);
    }

    /**
     * 获取缓存用户JSON
     *
     * @return
     */
    public static String getStoreLoginInfo() {
        String loginInfo = UtilsSharedPreference.getStringValue(SP_USER_INFO);
        return loginInfo;
    }

    /**
     * 清理用户缓存信息
     */
    public static void clearLoginInfo() {
        storeLoginInfo("");
    }

    /**
     * 退出登录
     *
     * @param activity
     */
    public static void logOutApp(Activity activity) {
        logOutCookie(activity);
        AppActivityManager.exitAPP();
    }

    /**
     * 调用销毁cookie接口，清除缓存
     *
     * @param activity
     */
    public static void logOutCookie(Activity activity) {
        Observable<DefaultResponse> ob = RetrofitFactory.getRetorfit(ProtocolImp.class).logOutRequest();
        RetrofitUtils.request(activity, ob);
        JPushInterface.setAlias(activity, "+10086", null);
        UtilsSharedPreference.clear();
    }

    public static void autoLogin(boolean auto) {
        UtilsSharedPreference.setBooleanValue(SP_AUTO_LOGIN, auto);
    }

    public static boolean isAutoLogin() {
        return UtilsSharedPreference.getBooleanValue(SP_AUTO_LOGIN, false);
    }

    /**
     * 跳转至其他人
     *
     * @param activity
     * @param pk
     */
    public static void jumpToOtherPeople(Activity activity, String pk) {
        Intent intent = new Intent(activity, ForumPeopleActivity.class);
        intent.putExtra(AppConstants.EXTRA_STRING, pk);
        JumpManager.doJumpForward(activity, intent);
    }
}
