package com.yijia.patient.ui.manager;

import com.yijia.patient.ui.protocol.response.BasicinfoResponse;
import com.zero.library.base.utils.UtilsGson;
import com.zero.library.base.utils.UtilsSharedPreference;

/**
 * Created by ZeroAries on 2016/6/29.
 * 健康信息
 */
public class HealthManager {

    private static final String BASE_HEALTH_INFO = "base_health_info";

    public static void storeBaseHealthInfo(BasicinfoResponse info) {
        String infoJson = UtilsGson.toJson(info);
        UtilsSharedPreference.setStringValue(BASE_HEALTH_INFO, infoJson);
    }

    public static BasicinfoResponse obtainBaseHealthInfo() {
        String loaclStr = UtilsSharedPreference.getStringValue(BASE_HEALTH_INFO);
        BasicinfoResponse info = UtilsGson.fromJson(loaclStr, BasicinfoResponse.class);
        return info;
    }
}
