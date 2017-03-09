package com.healthsoulmate.zkl.ui.protocol.request;

import com.zero.library.base.retrofit.DefaultRequest;

/**
 * Created by ZeroAries on 2016/3/18.
 * 第三方登录请求
 */
public class OtherLoginRequest extends DefaultRequest {
    private String uuid;//第三方登陆主键
    private String uuidtype;//第三方登陆类型0本平台自注册账号1qq 2 微信 3微博
    private String name;//昵称

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
