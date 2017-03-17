package com.yijia.zkl.ui.protocol.request;

import com.zero.library.base.retrofit.DefaultRequest;

/**
 * Created by ZeroAries on 2016/3/22.
 * 问题列表
 */
public class QandaPageRequest extends DefaultRequest {
    private String pkOnlineqa;//在线问题主键
    private String page;//页数 0开始

    public String getPkOnlineqa() {
        return pkOnlineqa;
    }

    public void setPkOnlineqa(String pkOnlineqa) {
        this.pkOnlineqa = pkOnlineqa;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
}
