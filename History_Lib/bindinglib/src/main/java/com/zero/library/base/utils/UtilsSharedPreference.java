package com.zero.library.base.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import static com.zero.library.base.utils.UtilsSharedPreference.setStringValue;

public class UtilsSharedPreference {

    /**
     * 加密Key
     */
    private static final String CRYPTO_KEY = "ZhaoKanGLiAnApP@";
    /**
     * 文件名称
     */
    public static final String APP_SETTING = "app_info";
    public static final String UNCLEAR_SETTING = "unclear_setting";

    public static void setBooleanValue(String key, boolean defaultvalue) {
        SharedPreferences prefs = getSharedPreference(APP_SETTING);
        setBooleanValue(prefs, key, defaultvalue);
    }

    public static void setBooleanValue(SharedPreferences prefs, String key, boolean defaultvalue) {
        checkKey(key);
        prefs.edit().putBoolean(key, defaultvalue).apply();
    }

    public static boolean getBooleanValue(String key, boolean defaultvalue) {
        SharedPreferences prefs = getSharedPreference(APP_SETTING);
        return getBooleanValue(prefs, key, defaultvalue);
    }

    public static boolean getBooleanValue(SharedPreferences prefs, String key, boolean defaultvalue) {
        checkKey(key);
        return prefs.getBoolean(key, defaultvalue);
    }

    public static void setIntValue(String key, int value) {
        SharedPreferences prefs = getSharedPreference(APP_SETTING);
        setIntValue(prefs, key, value);
    }

    public static void setIntValue(SharedPreferences prefs, String key, int value) {
        checkKey(key);
        prefs.edit().putInt(key, value).apply();
    }

    public static int getIntValue(String key) {
        SharedPreferences prefs = getSharedPreference(APP_SETTING);
        return getIntValue(prefs, key);
    }

    public static int getIntValue(SharedPreferences prefs, String key) {
        checkKey(key);
        return prefs.getInt(key, -1);
    }

    public static void setStringValue(String key, String value) {
        SharedPreferences prefs = getSharedPreference(APP_SETTING);
        setStringValue(prefs, key, value);
    }

    public static void setStringValue(SharedPreferences prefs, String key, String value) {
        checkKey(key);
        String encryptStr = UtilsParser.encryptWithBase64NoUrlEncode(value, CRYPTO_KEY.getBytes());
        prefs.edit().putString(key, encryptStr).apply();
    }

    public static String getStringValue(String key) {
        SharedPreferences prefs = getSharedPreference(APP_SETTING);
        return getStringValue(prefs, key);
    }

    public static String getStringValue(SharedPreferences prefs, String key) {
        checkKey(key);
        String storeStr = prefs.getString(key, "");
        if (TextUtils.isEmpty(storeStr)) {
            return storeStr;
        }
        return UtilsParser.decryptWithBase64NoUrlEncode(storeStr.getBytes(), CRYPTO_KEY.getBytes());
    }

    public static void clear() {
        SharedPreferences appPrefs = getSharedPreference(APP_SETTING);
        appPrefs.edit().clear().apply();
    }

    private static void checkKey(String key) {
        if (TextUtils.isEmpty(key)) throw new RuntimeException("Please make sure key not empty!");
    }

    public static SharedPreferences getSharedPreference(String name) {
        return UtilsUi.getContext().getSharedPreferences(name, Context.MODE_PRIVATE);
    }
}