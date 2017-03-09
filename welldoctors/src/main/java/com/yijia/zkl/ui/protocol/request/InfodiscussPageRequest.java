package com.yijia.zkl.ui.protocol.request;

import com.zero.library.base.retrofit.DefaultRequest;

/**
 * Created by ZeroAries on 2016/3/22.
 * 资讯详情列表
 */
public class InfodiscussPageRequest extends DefaultRequest {
    private String pkInfomation;//资讯主键
    private String page;//页数 0开始

    public String getPkInfomation() {
        return pkInfomation;
    }

    public void setPkInfomation(String pkInfomation) {
        this.pkInfomation = pkInfomation;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
}
