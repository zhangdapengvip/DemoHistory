package com.yijia.zkl.ui.protocol.request;

import com.zero.library.base.retrofit.DefaultRequest;

/**
 * Created by ZeroAries on 2016/3/22.
 * 医友提问列表
 */
public class QusetionPageRequest extends DefaultRequest {
    private String page;//页数 0开始
    private String pkView;//视野主键

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getPkView() {
        return pkView;
    }

    public void setPkView(String pkView) {
        this.pkView = pkView;
    }
}
