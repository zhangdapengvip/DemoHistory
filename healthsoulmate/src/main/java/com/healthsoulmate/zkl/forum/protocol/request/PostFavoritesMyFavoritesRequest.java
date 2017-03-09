package com.healthsoulmate.zkl.forum.protocol.request;

import com.zero.library.base.retrofit.DefaultRequest;

/**
 * Created by ZeroAries on 2016/3/22.
 * 请求帖子收藏列表
 */
public class PostFavoritesMyFavoritesRequest extends DefaultRequest {

    private String pkUser;//用户主键
    private String page;//分页 0开始 20条一页

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getPkUser() {
        return pkUser;
    }

    public void setPkUser(String pkUser) {
        this.pkUser = pkUser;
    }
}
