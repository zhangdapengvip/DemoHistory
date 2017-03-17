package com.healthsoulmate.zkl.forum.protocol.request;

import com.zero.library.base.retrofit.DefaultRequest;

/**
 * Created by ZeroAries on 2016/3/22.
 * 论坛列表
 */
public class DiscuzsectionPageRequest extends DefaultRequest {

    public static final String IS_COLLECTION = "Y";
    public static final String NO_COLLECTION = "N";

    private String pkUser;//用户主键
    private String pkDiscuzsection;//板块主键
    private String isCollection;//是否收藏   传Y则是   收藏接口   传N则是论坛列表接口


    public String getPkUser() {
        return pkUser;
    }

    public void setPkUser(String pkUser) {
        this.pkUser = pkUser;
    }

    public String getPkDiscuzsection() {
        return pkDiscuzsection;
    }

    public void setPkDiscuzsection(String pkDiscuzsection) {
        this.pkDiscuzsection = pkDiscuzsection;
    }

    public String getIsCollection() {
        return isCollection;
    }

    public void setIsCollection(String isCollection) {
        this.isCollection = isCollection;
    }
}