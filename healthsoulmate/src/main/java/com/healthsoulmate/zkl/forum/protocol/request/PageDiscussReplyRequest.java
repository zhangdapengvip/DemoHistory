package com.healthsoulmate.zkl.forum.protocol.request;

import com.zero.library.base.retrofit.DefaultRequest;

/**
 * Created by ZeroAries on 2016/3/22.
 * 请求跟帖评论
 */
public class PageDiscussReplyRequest extends DefaultRequest {
    private String pkDiscuss;//帖子评论主键
    private String page;//0开始

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getPkDiscuss() {
        return pkDiscuss;
    }

    public void setPkDiscuss(String pkDiscuss) {
        this.pkDiscuss = pkDiscuss;
    }
}
