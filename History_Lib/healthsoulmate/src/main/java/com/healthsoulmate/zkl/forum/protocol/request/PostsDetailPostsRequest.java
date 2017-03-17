package com.healthsoulmate.zkl.forum.protocol.request;

import com.zero.library.base.retrofit.DefaultRequest;

/**
 * Created by ZeroAries on 2016/3/22.
 * 请求帖子详情
 */
public class PostsDetailPostsRequest extends DefaultRequest {

    private String pkPosts;
    private String page;

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getPkPosts() {
        return pkPosts;
    }

    public void setPkPosts(String pkPosts) {
        this.pkPosts = pkPosts;
    }
}