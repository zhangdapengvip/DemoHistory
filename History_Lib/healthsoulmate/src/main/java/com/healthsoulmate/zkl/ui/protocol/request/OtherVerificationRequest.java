package com.healthsoulmate.zkl.ui.protocol.request;

import com.zero.library.base.retrofit.DefaultRequest;

/**
 * Created by ZeroAries on 2016/3/22.
 * 请求第三方登录验证
 */
public class OtherVerificationRequest extends DefaultRequest {

    private String uuid;//三方主键
    private String uuidtype;//三方类型 1QQ2微信3微博
    private String password;//手机号
    private String phone;//密码
    private String number;//验证码

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUuidtype() {
        return uuidtype;
    }

    public void setUuidtype(String uuidtype) {
        this.uuidtype = uuidtype;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
