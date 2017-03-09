package com.healthsoulmate.zkl.ui.protocol.request;

import com.zero.library.base.retrofit.DefaultRequest;

/**
 * Created by ZeroAries on 2016/3/22.
 * 轮播图
 */
public class InformationHeadRequest extends DefaultRequest {
    private String type;//资讯类型

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
