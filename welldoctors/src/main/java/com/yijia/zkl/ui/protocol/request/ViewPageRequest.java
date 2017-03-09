package com.yijia.zkl.ui.protocol.request;

import com.zero.library.base.retrofit.DefaultRequest;

/**
 * Created by ZeroAries on 2016/3/21.
 * 视野
 */
public class ViewPageRequest extends DefaultRequest {
    private String page;//页数 0开始
    private String type;//标签类型 0专业 1 名医 2医学公开课
    private String domain;//领域 0肿瘤科 1心血管科 2其它科
    private String userType = "0";//用户类型  0医生版 1 大众版

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
