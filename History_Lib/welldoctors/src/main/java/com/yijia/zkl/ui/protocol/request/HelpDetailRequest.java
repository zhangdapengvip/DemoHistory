package com.yijia.zkl.ui.protocol.request;

import com.zero.library.base.retrofit.DefaultRequest;

/**
 * Created by ZeroAries on 2016/3/21.
 * 求助详情
 */
public class HelpDetailRequest extends DefaultRequest {
    private String pkHelp;//求助主键

    public String getPkHelp() {
        return pkHelp;
    }

    public void setPkHelp(String pkHelp) {
        this.pkHelp = pkHelp;
    }
}
