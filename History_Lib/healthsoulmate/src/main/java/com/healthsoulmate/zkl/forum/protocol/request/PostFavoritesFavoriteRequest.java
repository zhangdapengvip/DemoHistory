package com.healthsoulmate.zkl.forum.protocol.request;

import com.zero.library.base.retrofit.DefaultRequest;

/**
 * Created by ZeroAries on 2016/3/22.
 * 请求帖子收藏
 */
public class PostFavoritesFavoriteRequest extends DefaultRequest {
    public static final String ADD = "add";
    public static final String DELETE = "delete";

    private String flag;//add 收藏，delete 取消收藏
    private String pkUser;//用户主键
    private String pkPostfavorites;//收藏主键   add时不传 delete传
    private String pkPosts;//帖子主键

    public String getPkUser() {
        return pkUser;
    }

    public void setPkUser(String pkUser) {
        this.pkUser = pkUser;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getPkPostfavorites() {
        return pkPostfavorites;
    }

    public void setPkPostfavorites(String pkPostfavorites) {
        this.pkPostfavorites = pkPostfavorites;
    }

    public String getPkPosts() {
        return pkPosts;
    }

    public void setPkPosts(String pkPosts) {
        this.pkPosts = pkPosts;
    }
}
