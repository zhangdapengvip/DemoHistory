package com.healthsoulmate.zkl.forum.protocol.request;

import com.zero.library.base.retrofit.DefaultRequest;

/**
 * Created by ZeroAries on 2016/3/22.
 * 请求关注
 */
public class UserfocusSaveRequest extends DefaultRequest {

    public static final String ADD = "add";
    public static final String DELETE = "delete";

    private String flag;//add 收藏，delete 取消收藏
    private String pkUserfocus;//关注主键   add时不传 delete传
    private String pkUserbefocus;//被关注人主键
    private String pkUser;//关注人主键

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getPkUser() {
        return pkUser;
    }

    public void setPkUser(String pkUser) {
        this.pkUser = pkUser;
    }

    public String getPkUserbefocus() {
        return pkUserbefocus;
    }

    public void setPkUserbefocus(String pkUserbefocus) {
        this.pkUserbefocus = pkUserbefocus;
    }

    public String getPkUserfocus() {
        return pkUserfocus;
    }

    public void setPkUserfocus(String pkUserfocus) {
        this.pkUserfocus = pkUserfocus;
    }
}
