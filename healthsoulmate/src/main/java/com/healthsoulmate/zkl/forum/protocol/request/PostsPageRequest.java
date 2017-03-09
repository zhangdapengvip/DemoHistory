package com.healthsoulmate.zkl.forum.protocol.request;

import com.zero.library.base.retrofit.DefaultRequest;

/**
 * Created by ZeroAries on 2016/3/22.
 * 请求帖子列表
 */
public class PostsPageRequest extends DefaultRequest {

    private String pkDiscuzsection;//板块主键
    private String page;//分页  0开始

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getPkDiscuzsection() {
        return pkDiscuzsection;
    }

    public void setPkDiscuzsection(String pkDiscuzsection) {
        this.pkDiscuzsection = pkDiscuzsection;
    }
}
