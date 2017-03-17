package com.healthsoulmate.zkl.ui.protocol.request;

import com.zero.library.base.retrofit.DefaultRequest;

/**
 * Created by ZeroAries on 2016/3/22.
 * 请求阅读数
 */
public class InformationUpdateRequest extends DefaultRequest {

    private String pkInformation;//资讯主键

    public String getPkInformation() {
        return pkInformation;
    }

    public void setPkInformation(String pkInformation) {
        this.pkInformation = pkInformation;
    }
}
