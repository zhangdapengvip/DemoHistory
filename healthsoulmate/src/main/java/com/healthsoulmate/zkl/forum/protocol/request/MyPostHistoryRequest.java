package com.healthsoulmate.zkl.forum.protocol.request;

import com.zero.library.base.retrofit.DefaultRequest;

/**
 * Created by ZeroAries on 2016/3/22.
 * 请求我发布的帖子
 */
public class MyPostHistoryRequest extends DefaultRequest {
    private String pkUser;

    public String getPkUser() {
        return pkUser;
    }

    public void setPkUser(String pkUser) {
        this.pkUser = pkUser;
    }
}
