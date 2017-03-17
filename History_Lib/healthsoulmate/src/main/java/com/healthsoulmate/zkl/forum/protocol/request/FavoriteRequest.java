package com.healthsoulmate.zkl.forum.protocol.request;

import com.zero.library.base.retrofit.DefaultRequest;

/**
 * Created by ZeroAries on 2016/3/22.
 * 请求收藏板块
 */
public class FavoriteRequest extends DefaultRequest {

    public static final String ADD = "add";
    public static final String DELETE = "delete";

    private String flag;//add 新增，delete 取消
    private String pkSectionfavorites;//主键  注:新增不需要传 取消 传
    private String pkDiscuzsection;//收藏版块
    private String pkUser;//收藏人

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getPkSectionfavorites() {
        return pkSectionfavorites;
    }

    public void setPkSectionfavorites(String pkSectionfavorites) {
        this.pkSectionfavorites = pkSectionfavorites;
    }

    public String getPkDiscuzsection() {
        return pkDiscuzsection;
    }

    public void setPkDiscuzsection(String pkDiscuzsection) {
        this.pkDiscuzsection = pkDiscuzsection;
    }

    public String getPkUser() {
        return pkUser;
    }

    public void setPkUser(String pkUser) {
        this.pkUser = pkUser;
    }
}
