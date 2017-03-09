package com.yijia.patient.ui.protocol.request;

import com.zero.library.base.retrofit.DefaultRequest;

/**
 * Created by ZeroAries on 2016/3/18.
 * 第三方登录请求
 */
public class OtherLoginRequest extends DefaultRequest {
    private String pk_other;//第三方登陆主键
    private String othertype;//第三方登陆类型0本平台自注册账号1qq 2 微信 3微博
    private String registertype = "1";//0 医生版  1 大众版

    public String getPk_other() {
        return pk_other;
    }

    public void setPk_other(String pk_other) {
        this.pk_other = pk_other;
    }

    public String getOthertype() {
        return othertype;
    }

    public void setOthertype(String othertype) {
        this.othertype = othertype;
    }

    public String getRegistertype() {
        return registertype;
    }

    public void setRegistertype(String registertype) {
        this.registertype = registertype;
    }
}
