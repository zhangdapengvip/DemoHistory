package com.yijia.zkl.ui.protocol.request;

import com.zero.library.base.retrofit.DefaultRequest;

/**
 * Created by ZeroAries on 2016/3/22.
 * 版本更新
 */
public class GetVersionRequest extends DefaultRequest {
    private String othertype = "0";//类型  0医生版 4大众版  其他不变

    public String getOthertype() {
        return othertype;
    }

    public void setOthertype(String othertype) {
        this.othertype = othertype;
    }
}
