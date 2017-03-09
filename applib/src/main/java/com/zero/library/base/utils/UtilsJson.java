package com.zero.library.base.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;

import com.orhanobut.logger.Logger;

public class UtilsJson {

    /**
     * String转换为JSONObject
     *
     * @param jsonStr
     * @return 异常时返回空JSONObject
     */
    public static JSONObject getJSONObject(String jsonStr) {
        if (TextUtils.isEmpty(jsonStr)) {
            return new JSONObject();
        }
        try {
            return new JSONObject(jsonStr);
        } catch (JSONException e) {
            Logger.e("JSONException :" + e.getMessage());
            return new JSONObject();
        }
    }

    /**
     * 获取JSONObject中对应key值的JSONObject
     *
     * @param obj
     * @param name
     * @return 异常时返回空JSONObject
     */
    public static JSONObject getJSONObject(JSONObject obj, String name) {
        JSONObject result = new JSONObject();
        if (obj.has(name)) {
            try {
                result = obj.getJSONObject(name);
            } catch (JSONException e) {
                Logger.e("JSONException :" + e.getMessage());
            }
        }
        return result;
    }

    /**
     * 获取JSONArray中对应位置的JSONObject
     *
     * @param jsonArray
     * @param index
     * @return 异常时返回空JSONObject
     */
    public static JSONObject getJSONObject(JSONArray jsonArray, int index) {
        try {
            return jsonArray.getJSONObject(index);
        } catch (JSONException e) {
            e.printStackTrace();
            return new JSONObject();
        }
    }

    /**
     * 获取JSONObject对应key值的String
     *
     * @param obj
     * @param name
     * @return String
     */
    public static String getString(JSONObject obj, String name) {
        String result = "";
        if (obj.has(name)) {
            try {
                result = obj.getString(name);
            } catch (JSONException e) {
                Logger.e("JSONException :" + e.getMessage());
                result = getIntToString(obj, name);
            }
        }
        return result;
    }

    /**
     * 获取JSONObject对应key值的int转为String
     *
     * @param obj
     * @param name
     * @return int转为String
     */
    public static String getIntToString(JSONObject obj, String name) {
        String result = "";
        if (obj.has(name)) {
            try {
                result = obj.getInt(name) + "";
            } catch (JSONException e) {
                Logger.e("JSONException :" + e.getMessage());
            }
        }
        return result;
    }

    /**
     * 获取JSONObject中对应Key值的JSONArray
     *
     * @param obj
     * @param name
     * @return JSONArray
     */
    public static JSONArray getJSONArray(JSONObject obj, String name) {
        JSONArray result = new JSONArray();
        if (obj.has(name)) {
            try {
                result = obj.getJSONArray(name);
            } catch (JSONException e) {
                Logger.e("JSONException :" + e.getMessage());
            }
        }
        return result;
    }

    /**
     * 判断JSONObject是否为空
     *
     * @param obj
     * @return boolean
     */
    public static boolean isEmpty(JSONObject obj) {
        return null == obj || "{}".equals(obj.toString());
    }

    /**
     * 判断JSONObject是否存在key
     *
     * @param obj
     * @param name
     * @return boolean
     */
    public static boolean has(JSONObject obj, String name) {
        return obj.has(name);
    }

    /**
     * 判断JSONObject中Key对应值是否为空
     *
     * @param obj
     * @param name
     * @return boolean
     */
    public static boolean isEmpty(JSONObject obj, String name) {
        return TextUtils.isEmpty(getString(obj, name));
    }

    /**
     * 向JSONObject添加一条内容
     *
     * @param obj
     * @param name
     * @param value
     */
    public static void put(JSONObject obj, String name, Object value) {
        try {
            obj.put(name, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
