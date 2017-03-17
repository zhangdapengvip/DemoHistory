package com.yijia.patient.ui.protocol.request;

import com.zero.library.base.retrofit.DefaultRequest;

/**
 * Created by ZeroAries on 2016/3/22.
 * 轮播图
 */
public class InformationHeadRequest extends DefaultRequest {
    private String type;//标签 0要闻1会议2教育
    private String domain;//领域（待定）
    private String userType = "1";//用户类型  0医生版 1 大众版

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
