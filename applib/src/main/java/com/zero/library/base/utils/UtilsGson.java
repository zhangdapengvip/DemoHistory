package com.zero.library.base.utils;

import java.lang.reflect.Type;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.orhanobut.logger.Logger;

public class UtilsGson {

    private volatile static Gson gson;

    private UtilsGson() {
    }

    private static synchronized Gson getInstance() {
        if (gson == null) {
            synchronized (UtilsGson.class) {
                if (gson == null) {
                    gson = new GsonBuilder().create();
                }
            }
        }
        return gson;
    }

    /**
     * 将JSON字符串转换为指定类型对象
     *
     * @param json
     * @param type
     * @return
     * @throws JsonSyntaxException
     */
    public static <T> T fromJson(String json, Type type)
            throws JsonSyntaxException {
        T bean = null;
        try {
            bean = getInstance().fromJson(json, type);
        } catch (Exception e) {
            e.printStackTrace();
            Logger.e("转换时发生异常typeOfT:——>" + type.getClass());
            Logger.e("转换时发生异常——>" + json);
        }
        return bean;
    }

    /**
     * 将对象转换为JSON字符串
     *
     * @param obj
     * @return
     */
    public static String toJson(Object obj) {
        return getInstance().toJson(obj);
    }

    /**
     * 将对象转换为JSON对象
     *
     * @param obj
     * @return
     */
    public static JSONObject toJsonObj(Object obj) {
        return UtilsJson.getJSONObject(toJson(obj));
    }
}