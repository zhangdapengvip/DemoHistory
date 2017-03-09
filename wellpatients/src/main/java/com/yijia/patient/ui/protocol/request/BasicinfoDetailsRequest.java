package com.yijia.patient.ui.protocol.request;

import com.zero.library.base.retrofit.DefaultRequest;

/**
 * Created by ZeroAries on 2016/3/22.
 * 基本信息内容
 */
public class BasicinfoDetailsRequest extends DefaultRequest {
    private String pkUser;

    public String getPkUser() {
        return pkUser;
    }

    public void setPkUser(String pkUser) {
        this.pkUser = pkUser;
    }
}
