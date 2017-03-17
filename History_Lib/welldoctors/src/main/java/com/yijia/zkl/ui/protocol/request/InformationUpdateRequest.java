package com.yijia.zkl.ui.protocol.request;

import com.zero.library.base.retrofit.DefaultRequest;

/**
 * Created by ZeroAries on 2016/3/22.
 * 拷贝用
 */
public class InformationUpdateRequest extends DefaultRequest {
    private String pkInformation;

    public String getPkInformation() {
        return pkInformation;
    }

    public void setPkInformation(String pkInformation) {
        this.pkInformation = pkInformation;
    }
}
