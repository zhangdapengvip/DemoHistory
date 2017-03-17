package com.yijia.zkl.ui.manager;

import android.text.TextUtils;

import com.orhanobut.logger.Logger;
import com.yijia.zkl.ui.protocol.response.LoginResponse;
import com.zero.library.base.utils.UtilsGson;
import com.zero.library.base.utils.UtilsSharedPreference;

public class UserManager {
    public static final String SP_USER_INFO = "sp_user_info";
    public static final String SP_AUTO_LOGIN = "sp_auto_login";

    public static LoginResponse getLoginInfo() {
        String stringInfo = getLoginInfoString();
        if (TextUtils.isEmpty(stringInfo)) {
            return new LoginResponse();
        }
        return UtilsGson.fromJson(stringInfo, LoginResponse.class);
    }

    public static void storeLoginInfo(String loginInfo) {
        UtilsSharedPreference.setStringValue(SP_USER_INFO, loginInfo);
        Logger.d("Store Login Info: " + loginInfo);
    }

    public static String getLoginInfoString() {
        String loginInfo = UtilsSharedPreference.getStringValue(SP_USER_INFO);
        Logger.d("Local Login Info: " + loginInfo);
        return loginInfo;
    }

    public static void autoLogin(boolean auto) {
        UtilsSharedPreference.setBooleanValue(SP_AUTO_LOGIN, auto);
    }

    public static boolean isAutoLogin() {
        return UtilsSharedPreference.getBooleanValue(SP_AUTO_LOGIN, false);
    }
}
