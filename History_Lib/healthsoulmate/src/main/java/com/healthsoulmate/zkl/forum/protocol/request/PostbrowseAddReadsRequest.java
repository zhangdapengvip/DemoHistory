package com.healthsoulmate.zkl.forum.protocol.request;

import com.zero.library.base.retrofit.DefaultRequest;

/**
 * Created by ZeroAries on 2016/3/22.
 * 请求添加浏览历史
 */
public class PostbrowseAddReadsRequest extends DefaultRequest {

    private String pkPosts;

    public String getPkPosts() {
        return pkPosts;
    }

    public void setPkPosts(String pkPosts) {
        this.pkPosts = pkPosts;
    }
}
