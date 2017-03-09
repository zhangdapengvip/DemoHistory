package com.yijia.patient.ui.protocol.request;

import com.zero.library.base.retrofit.DefaultRequest;

/**
 * Created by ZeroAries on 2016/3/18.
 * 登录请求
 */
public class LoginRequest extends DefaultRequest {
    private String username;//用户名
    private String password;//密码

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
