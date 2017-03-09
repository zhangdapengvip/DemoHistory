package com.yijia.zkl.ui.protocol.request;

import com.zero.library.base.retrofit.DefaultRequest;

/**
 * Created by ZeroAries on 2016/3/22.
 * 拷贝用
 */
public class GetMessageSendRequest extends DefaultRequest {
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
